package com.mjr.extraplanets.planets.Saturn.worldgen;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonConfiguration;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.DungeonStart;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.Piece;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.RoomBoss;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.mjr.extraplanets.blocks.ExtraPlanets_Blocks;
import com.mjr.extraplanets.tile.dungeonSpawners.TileEntityDungeonSpawnerSaturn;

public class RoomBossSaturn extends RoomBoss
{
    public RoomBossSaturn()
    {
    }

    public RoomBossSaturn(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, sizeX, sizeY, sizeZ, entranceDir);
    }

    public RoomBossSaturn(DungeonConfiguration configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, 24, 11, 24, entranceDir);
    }

    @Override
    public boolean addComponentParts(World worldIn, Random random, StructureBoundingBox boundingBox)
    {
        for (int i = 0; i <= this.sizeX; i++)
        {
            for (int j = 0; j <= this.sizeY; j++)
            {
                for (int k = 0; k <= this.sizeZ; k++)
                {
                    if (i == 0 || i == this.sizeX || j == 0 || k == 0 || k == this.sizeZ)
                    {
                        boolean placeBlock = true;
                        if (getDirection().getAxis() == EnumFacing.Axis.Z)
                        {
                            int start = (this.boundingBox.maxX - this.boundingBox.minX) / 2 - 1;
                            int end = (this.boundingBox.maxX - this.boundingBox.minX) / 2 + 1;
                            if (i > start && i <= end && j < 3 && j > 0)
                            {
                                if (getDirection() == EnumFacing.SOUTH && k == 0)
                                {
                                    placeBlock = false;
                                }
                                else if (getDirection() == EnumFacing.NORTH && k == this.sizeZ)
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        else
                        {
                            int start = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 - 1;
                            int end = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 + 1;
                            if (k > start && k <= end && j < 3 && j > 0)
                            {
                                if (getDirection() == EnumFacing.EAST && i == 0)
                                {
                                    placeBlock = false;
                                }
                                else if (getDirection() == EnumFacing.WEST && i == this.sizeX)
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        if (placeBlock)
                        {
                            this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, boundingBox);
                        }
                        else
                        {
                            this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
                        }
                    }
                    else if (j == this.sizeY)
                    {
                        if ((i <= 2 || k <= 2 || i >= this.sizeX - 2 || k >= this.sizeZ - 2) && random.nextInt(4) == 0)
                        {
                            this.setBlockState(worldIn, Blocks.GLOWSTONE.getDefaultState(), i, j, k, boundingBox);
                        }
                        else
                        {
                            this.setBlockState(worldIn, this.configuration.getBrickBlock(), i, j, k, boundingBox);
                        }
                    }
                    else
                    {
                        this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
                    }
                }
            }
        }

        int spawnerX = this.sizeX / 2;
        int spawnerY = 1;
        int spawnerZ = this.sizeZ / 2;
        this.setBlockState(worldIn, ExtraPlanets_Blocks.saturnSpawner.getDefaultState(), spawnerX, spawnerY, spawnerZ, boundingBox);
        BlockPos blockpos = new BlockPos(this.getXWithOffset(spawnerX, spawnerZ), this.getYWithOffset(spawnerY), this.getZWithOffset(spawnerX, spawnerZ));
        TileEntityDungeonSpawnerSaturn spawner = (TileEntityDungeonSpawnerSaturn) worldIn.getTileEntity(blockpos);

        if (spawner == null)
        {
            spawner = new TileEntityDungeonSpawnerSaturn();
            worldIn.setTileEntity(blockpos, spawner);
        }

        spawner.setRoom(new Vector3(this.boundingBox.minX + 1, this.boundingBox.minY + 1, this.boundingBox.minZ + 1), new Vector3(this.sizeX - 1, this.sizeY - 1, this.sizeZ - 1));

        return true;
    }

    @Override
    public Piece getNextPiece(DungeonStart startPiece, Random rand)
    {
        return getCorridor(rand, startPiece, 10, true);
    }
}
