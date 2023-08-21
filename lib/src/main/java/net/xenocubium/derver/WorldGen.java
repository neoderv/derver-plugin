package net.xenocubium.derver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.bukkit.block.Biome;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.PerlinNoiseGenerator;

public class WorldGen {
	
	enum Factor {
		HEIGHT,
		MOUNTAIN,
		TEMPERATURE
	}
	
	public int min = -64;
	public int mid = 64;
	public int max = 320;
	public double midFr = 0;
	
	NoiseGenerator[] generators = new NoiseGenerator[24];
	double[][] data = {
		// seed, scale, weight, power, type
		{76923,80/1.0,200},
		{74633,300/1.3,300},
		{72362,800/1.6,400},
		{75221,2000/1.9,500},
		{74840,4000/2.2,600},
		{72494,8000/2.5,700},
		
		{65424,80,300},
		{84624,40,300},
		{23424,20,300},
		{12324,10,300},
		
		{89563,100,15},
		{83163,100,15},
		{86553,800,70},
		{89853,800,70},
		{32542,3000,200},
		{39942,3000,200},
		{55321,8000,500},
		{35321,8000,500},
		
		{76983,80/1.0,200*3.5},
		{74653,300/1.3,300*3.5},
		{32362,800/1.6,400*3.5},
		{25221,2000/1.9,500*3.5},
		{94840,4000/2.2,600*3.5},
		{77494,8000/2.5,700*3.5},
	};	
	Factor[] types = {
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
		
		Factor.MOUNTAIN,
		Factor.MOUNTAIN,
		Factor.MOUNTAIN,
		Factor.MOUNTAIN,
		
		Factor.TEMPERATURE,
		Factor.TEMPERATURE,
		Factor.TEMPERATURE,
		Factor.TEMPERATURE,
		Factor.TEMPERATURE,
		Factor.TEMPERATURE,
		Factor.TEMPERATURE,
		Factor.TEMPERATURE,
		
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
		Factor.HEIGHT,
	};
	
	Biome[] biomes = {
		Biome.ICE_SPIKES,
		Biome.SNOWY_TAIGA,
		Biome.TAIGA,
		Biome.DARK_FOREST,
		Biome.FOREST,
		Biome.PLAINS,
		Biome.JUNGLE,
		Biome.SAVANNA,
		Biome.DESERT,
		Biome.BADLANDS
	};
	
	public WorldGen() {
		for (int i = 0; i < data.length; i++) {
			double[] entry = this.data[i];
			int seed = (int) entry[0];
			this.generators[i] = new PerlinNoiseGenerator(seed);
		}
		
		this.midFr = ((double)(mid-min))/((double)(max - min));
		
		this.midFr = Math.log(this.midFr) / Math.log(0.5);
	}
	
	public double getPos(int x, int z, int y, Factor filter) {
		double val = 0.0;
		double weight = 0;
		for (int i = 0; i < data.length; i++) {
			double[] entry = this.data[i];
			NoiseGenerator generator = this.generators[i];
			double scale = entry[1];
			double inWeight = entry[2];
			Factor type = this.types[i];
			
			double inVal = 0.0;
			
			if (type != filter && (filter != Factor.HEIGHT || type != Factor.MOUNTAIN)) {
				continue;
			}
			
			if (type == Factor.MOUNTAIN) {
				inVal = generator.noise(x / scale, z / scale, y / scale * 2);
			} else {
				inVal = generator.noise(x / scale, z / scale);
			}
			inVal /= 2.0;
			inVal += 0.5;
			
			if (type == Factor.MOUNTAIN) {
				val /= weight;
				inVal = (val > 0.5) ? (inVal * ((val - 0.5) / 0.5)) : 0;
				inVal *= 5;
				val *= weight;
			} else if (type == Factor.TEMPERATURE) {
				int delta = biomes.length - 1;
				inVal = Math.log(inVal/(1- inVal));
				inVal *= 7;
				inVal += (delta + 1) / 2;
			}
			
			inVal *= inWeight;
			
			weight += inWeight;
			
			val += inVal;
		}
		val /= weight;
		
		if (filter == Factor.HEIGHT) {
			val = Math.pow(val,midFr * 0.98);
		}
		
		return val;
	}
	
	public int getPosInt(int x, int z, int y) {
		int delta = max - min;
		
		int pos = (int) (this.getPos(x,z,y, Factor.HEIGHT) * delta + min);
		
		pos = Math.min(pos, max);
		pos = Math.max(pos, min);
		
		return pos;
	}
	
	public Biome getBiome(int x, int z, int y) {
		int delta = biomes.length - 1;
		
		double inPos = this.getPos(x,z,y, Factor.TEMPERATURE);

		int pos = (int) (inPos);
		int height = this.getPosInt(x,z,y);
		
		pos = Math.min(pos, delta);
		pos = Math.max(pos, 0);
		
		if (height < mid) {
			return Biome.OCEAN;
		}
		
		return biomes[pos];
	}
	
	public void renderImage() {
		int factor = 16;
		int scale = 1024;
		
		BufferedImage bi = new BufferedImage(scale*2,scale*2,BufferedImage.TYPE_INT_RGB);
		
		for (int x = 0; x < scale*2; x++) {
			for (int y = 0; y < scale*2; y++) {
				int x2 = (x-scale) * factor;
				int y2 = (y-scale) * factor;
				
				Biome pos = this.getBiome(x2, y2, 0);
				
				int color = 0x000000;
				
				switch (pos) {
				case ICE_SPIKES:
					color = 0xB4DCDC;
					break;
				case SNOWY_TAIGA:
					color = 0x31554A;
					break;
				case TAIGA:
					color = 0x0B6659;
					break;
				case FOREST:
					color = 0x056621;
					break;
				case PLAINS:
					color = 0x8DB360;
					break;
				case JUNGLE:
					color = 0x537B09;
					break;
				case DESERT:
					color = 0xFA9418;
					break;
				case BADLANDS:
					color = 0xD94515;
					break;
				case OCEAN:
					color = 0x000070;
					break;
				case SAVANNA:
					color = 0xBDB25F;
					break;
				case DARK_FOREST:
					color = 0x40511A;
					break;
				default:
					break;
				}
				
				bi.setRGB(x, y, color);
			}
		}
		
	    File outputfile = new File("map.png");
	    try {
			ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
