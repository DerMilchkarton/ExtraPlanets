package com.mjr.extraplanets.planets.Uranus.worldgen;

import micdoodle8.mods.galacticraft.api.prefab.world.gen.BiomeDecoratorSpace;
import micdoodle8.mods.galacticraft.core.world.gen.WorldGenMinableMeta;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

import com.mjr.extraplanets.Config;
import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.blocks.fluid.ExtraPlanets_Fluids;
import com.mjr.extraplanets.world.features.WorldGenCustomIceSpike;
import com.mjr.extraplanets.world.features.WorldGenCustomLake;
import com.mjr.extraplanets.world.features.WorldGenIgloo;

public class BiomeDecoratorUranus extends BiomeDecoratorSpace {

	private WorldGenerator iceGen;
	private WorldGenerator crystalGen;
	private WorldGenerator denseIceGen;
	private WorldGenerator whiteGemGen;

	private World currentWorld;

	private int iceSpikesPerChunk = 5;
	private int LakesPerChunk = 5;

	private boolean isDecorating = false;

	public BiomeDecoratorUranus() {
		this.iceGen = new WorldGenMinableMeta(Blocks.ICE, 18, 0, true, ExtraPlanets_Blocks.URANUS_BLOCKS, 2);
		this.crystalGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.URANUS_BLOCKS, 4, 3, true, ExtraPlanets_Blocks.URANUS_BLOCKS, 2);
		this.denseIceGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.DENSE_ICE, 8, 0, true, ExtraPlanets_Blocks.URANUS_BLOCKS, 0);
		this.whiteGemGen = new WorldGenMinableMeta(ExtraPlanets_Blocks.URANUS_BLOCKS, 4, 6, true, ExtraPlanets_Blocks.URANUS_BLOCKS, 2);

		// WorldGenMinableMeta(Block OreBlock, int numberOfBlocks, int OreMeta,
		// boolean usingMetaData, Block StoneBlock, int StoneMeta);
	}

	@Override
	protected void setCurrentWorld(World world) {
		this.currentWorld = world;
	}

	@Override
	protected World getCurrentWorld() {
		return this.currentWorld;
	}

	@Override
	protected void decorate() {
		if (isDecorating)
			return;
		isDecorating = true;
		this.generateOre(8, this.iceGen, 50, 120);
		this.generateOre(20, this.crystalGen, 0, 32);
		this.generateOre(20, this.denseIceGen, 0, 256);
		this.generateOre(5, this.whiteGemGen, 0, 10);

		// generateOre(int amountPerChunk, WorldGenerator worldGenerator, int
		// minY, int maxY);

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.currentWorld, this.rand, new BlockPos(this.chunkX, 0, this.chunkZ)));

		if (Config.GENERATE_URANUS_ICE_SPIKES) {
			for (int i = 0; i < this.iceSpikesPerChunk; i++) {
				if (this.rand.nextInt(20) == 0) {
					int x = this.chunkX + 6;
					int z = this.chunkZ + 6;
					int y = this.currentWorld.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
					new WorldGenCustomIceSpike().generate(this.currentWorld, this.rand, new BlockPos(x, y, z), ExtraPlanets_Blocks.URANUS_BLOCKS);
				}
			}
		}
		for (int i = 0; i < this.LakesPerChunk; i++) {
			if (this.rand.nextInt(10) == 0) {
				int x = this.chunkX + 8;
				// int y = this.rand.nextInt(16) + 16;
				int z = this.chunkZ + 8;
				int y = this.currentWorld.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY() - 2;
				new WorldGenCustomLake(ExtraPlanets_Fluids.FROZEN_WATER).generate(this.currentWorld, this.rand, new BlockPos(x, y, z), ExtraPlanets_Blocks.URANUS_BLOCKS);
			}
		}
		if (Config.GENERATE_URANUS_IGLOOS) {
			if (this.rand.nextInt(100) == 1) {
				int x = this.chunkX + 8;
				int z = this.chunkZ + 8;
				int y = this.currentWorld.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z)).getY();
				new WorldGenIgloo().generate(this.currentWorld, this.rand, new BlockPos(x, y, z));
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.currentWorld, this.rand, new BlockPos(this.chunkX, 0, this.chunkZ)));
		isDecorating = false;
	}
}