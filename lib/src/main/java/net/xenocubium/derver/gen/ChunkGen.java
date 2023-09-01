package net.xenocubium.derver.gen;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

public class ChunkGen extends ChunkGenerator {
	WorldGen mainGen;
	
	public ChunkGen(WorldGen mainGen) {
		this.mainGen = mainGen;
	}
	
	public Material outMaterial(int x, int y, int z, int min, int max) {
		int pos = mainGen.getPosInt(x, z, y);
		
		if (y == min) return Material.BEDROCK;
		
		if (y < pos) return Material.STONE;
		if (y < 64) return Material.WATER;
			
		return Material.AIR;
	}
	
	public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkGenerator.ChunkData chunkData) {
		int min = chunkData.getMinHeight();
		int max = chunkData.getMaxHeight();
		
		mainGen.min = min;
		mainGen.max = max;
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				
				for (int y = min; y < max; y++) {
					int x2 = x + chunkX * 16;
					int z2 = z + chunkZ * 16;
					
					Material mat = outMaterial(x2,y,z2, min, max);

					chunkData.setBlock(x, y, z, mat);
				}
			}
		}
	}
	
	public boolean shouldGenerateNoise() {
		return false;
	}
	
	public boolean shouldGenerateSurface() {
		return true;
	}
	
	public boolean shouldGenerateCaves(WorldInfo worldInfo, Random random, int chunkX, int chunkZ) {
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int y = 64;
				int x2 = x + chunkX * 16;
				int z2 = z + chunkZ * 16;
					
				Material mat = outMaterial(x2,y,z2, -10000, 10000);

				if (mat == Material.WATER) return false;
			}
		}
		return true;
	}
	
	public boolean shouldGenerateDecorations() {
		return true;
	}
	
	public boolean shouldGenerateMobs() {
		return true;
	}
	
	public boolean shouldGenerateStructures() {
		return false;
	}
}
