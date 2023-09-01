package net.xenocubium.derver.gen;

import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

public class BiomeGen extends BiomeProvider {
	Biome[] biomes = {
		Biome.ICE_SPIKES,
		Biome.SNOWY_TAIGA,
		Biome.TAIGA,
		Biome.FOREST,
		Biome.PLAINS,
		Biome.JUNGLE,
		Biome.DESERT,
		Biome.BADLANDS,
		Biome.OCEAN,
		Biome.DARK_FOREST,
		Biome.SAVANNA
	};	
	
	WorldGen mainGen;
	
	public BiomeGen(WorldGen mainGen) {
		this.mainGen = mainGen;
	}

	@Override
	public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
		return this.mainGen.getBiome(x, z, y);
	}

	@Override
	public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
		return Arrays.asList(biomes);
	}
}
