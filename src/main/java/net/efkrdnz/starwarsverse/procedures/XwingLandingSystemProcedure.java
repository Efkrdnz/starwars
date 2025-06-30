package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class XwingLandingSystemProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof XwingAircraftEntity xwing))
			return;
		if (!(xwing.getControllingPassenger() instanceof Player pilot))
			return;
		Level level = xwing.level();
		Vec3 position = xwing.position();
		// check if we're in landing mode (low altitude + low speed)
		double groundDistance = getGroundDistance(xwing, level);
		double speed = xwing.getDeltaMovement().length();
		if (groundDistance < 8.0 && speed < 1.0) {
			handleLandingAssist(xwing, groundDistance);
		}
	}

	private static double getGroundDistance(XwingAircraftEntity xwing, Level level) {
		BlockPos pos = xwing.blockPosition();
		// find the ground below
		for (int y = pos.getY(); y > level.getMinBuildHeight(); y--) {
			BlockPos checkPos = new BlockPos(pos.getX(), y, pos.getZ());
			BlockState state = level.getBlockState(checkPos);
			if (!state.isAir() && state.getBlock() != Blocks.WATER) {
				return xwing.getY() - y;
			}
		}
		return 1000; // no ground found
	}

	private static void handleLandingAssist(XwingAircraftEntity xwing, double groundDistance) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// landing approach assistance
		if (groundDistance > 2.0) {
			// gentle descent
			double descentRate = -0.1 * (groundDistance / 8.0);
			Vec3 assistedVel = new Vec3(currentVel.x * 0.9, // reduce horizontal speed
					Math.max(descentRate, currentVel.y - 0.05), // controlled descent
					currentVel.z * 0.9);
			xwing.setDeltaMovement(assistedVel);
			// auto-level for landing
			autoLevel(xwing);
		} else {
			// final landing phase
			finalLanding(xwing);
		}
	}

	private static void autoLevel(XwingAircraftEntity xwing) {
		float currentPitch = xwing.getXRot();
		// gradually level out
		xwing.setXRot(currentPitch * 0.85f);
	}

	private static void finalLanding(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// very gentle touchdown
		Vec3 landingVel = new Vec3(currentVel.x * 0.8, Math.max(-0.1, currentVel.y), currentVel.z * 0.8);
		xwing.setDeltaMovement(landingVel);
		// complete leveling
		xwing.setXRot(0);
		// deploy landing gear animation
		if (xwing instanceof XwingAircraftEntity) {
			xwing.setAnimation("landing");
		}
	}
}
