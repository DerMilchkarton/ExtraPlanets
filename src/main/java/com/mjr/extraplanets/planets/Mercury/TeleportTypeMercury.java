package com.mjr.extraplanets.planets.Mercury;

import java.util.Random;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.entities.player.CapabilityStatsHandler;
import micdoodle8.mods.galacticraft.core.entities.player.IStatsCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleportTypeMercury implements ITeleportType {
	@Override
	public boolean useParachute() {
		return true;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player != null) {
			IStatsCapability stats = player.getCapability(CapabilityStatsHandler.GC_STATS_CAPABILITY, null);
			return new Vector3(stats.getCoordsTeleportedFromX(), 900.0, stats.getCoordsTeleportedFromZ());
		}

		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return new Vector3(entity.posX, 250.0, entity.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		final double x = (rand.nextDouble() * 2 - 1.0D) * 5.0D;
		final double z = (rand.nextDouble() * 2 - 1.0D) * 5.0D;
		return new Vector3(x, 220.0D, z);
	}

	@Override
	public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket) {
	}

	@Override
	public void setupAdventureSpawn(EntityPlayerMP player) {
		// TODO Auto-generated method stub

	}
}
