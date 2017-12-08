package com.mjr.extraplanets.moons.Deimos.worldgen;

import java.util.List;

import micdoodle8.mods.galacticraft.api.prefab.core.BlockMetaPair;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.ChunkProviderSpace;
import micdoodle8.mods.galacticraft.api.prefab.world.gen.MapGenBaseMeta;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;

import com.google.common.collect.Lists;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.world.MapGenCavePlanet;
import com.mjr.extraplanets.world.MapGenRavinePlanet;

public class ChunkProviderDeimos extends ChunkProviderSpace {
	private final BiomeDecoratorDeimos ceresBiomeDecorator = new BiomeDecoratorDeimos();
	private final MapGenCavePlanet caveGenerator = new MapGenCavePlanet(ExtraPlanets_Blocks.DEIMOS_BLOCKS, 0, 1, 2);
	private final MapGenRavinePlanet ravineGenerator = new MapGenRavinePlanet();

	public ChunkProviderDeimos(World par1World, long seed, boolean mapFeaturesEnabled) {
		super(par1World, seed, mapFeaturesEnabled);
	}

	@Override
	protected BiomeDecoratorSpace getBiomeGenerator() {
		return this.ceresBiomeDecorator;
	}

	@Override
	protected BiomeGenBase[] getBiomesForGeneration() {
		return new BiomeGenBase[] { DeimosBiomes.deimos };
	}

	@Override	
	protected int getSeaLevel() {
		return 93;
	}

	@Override
	protected List<MapGenBaseMeta> getWorldGenerators() {
		List<MapGenBaseMeta> generators = Lists.newArrayList();
		generators.add(this.caveGenerator);
		return generators;
	}

	@Override
	protected BlockMetaPair getGrassBlock() {
		return new BlockMetaPair(ExtraPlanets_Blocks.DEIMOS_BLOCKS, (byte) 0);
	}

	@Override
	protected BlockMetaPair getDirtBlock() {
		return new BlockMetaPair(ExtraPlanets_Blocks.DEIMOS_BLOCKS, (byte) 1);
	}

	@Override
	protected BlockMetaPair getStoneBlock() {
		return new BlockMetaPair(ExtraPlanets_Blocks.DEIMOS_BLOCKS, (byte) 2);
	}

	@Override
	public double getHeightModifier() {
		return 24;
	}

	@Override
	public double getSmallFeatureHeightModifier() {
		return 26;
	}

	@Override
	public double getMountainHeightModifier() {
		return 100;
	}

	@Override
	public double getValleyHeightModifier() {
		return 25;
	}

	@Override
	public int getCraterProbability() {
		return 6000;
	}

	@Override
	public void onChunkProvide(int cX, int cZ, ChunkPrimer primer) {
		this.ravineGenerator.generate(this, this.worldObj, cX, cZ, primer);
	}
	
	@Override
	public void recreateStructures(Chunk chunk, int x, int z) {
	}

	@Override
	public void onPopulate(IChunkProvider provider, int cX, int cZ) {		
	}
}
