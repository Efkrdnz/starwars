package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class XwingAircraftOnEntityTickUpdateProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity driver = entity.getControllingPassenger();
		// Set animations
		if (entity instanceof XwingAircraftEntity xwing) {
			if (driver == null) {
				xwing.setAnimation("idle_1");
			} else {
				xwing.setAnimation("idle_2");
			}
		}
		if (driver == null) {
			// Simple descent when no pilot
			Vec3 vel = entity.getDeltaMovement();
			entity.setDeltaMovement(new Vec3(vel.x * 0.95, vel.y - 0.02, vel.z * 0.95));
			return;
		}
		if (!(driver instanceof Player player))
			return;
		// Debug output to see if variables are being set
		StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		boolean forward = vars.ship_f;
		boolean backward = vars.ship_b;
		boolean left = vars.ship_l;
		boolean right = vars.ship_r;
		// Print debug info every 20 ticks (1 second)
		if (entity.tickCount % 20 == 0) {
			System.out.println("X-wing Controls Debug: F=" + forward + " B=" + backward + " L=" + left + " R=" + right);
		}
		// Simple direct movement for testing
		Vec3 currentVel = entity.getDeltaMovement();
		Vec3 newVel = currentVel;
		if (forward) {
			Vec3 look = player.getLookAngle();
			newVel = newVel.add(look.scale(0.1));
		}
		if (backward) {
			Vec3 look = player.getLookAngle();
			newVel = newVel.add(look.scale(-0.05));
		}
		if (left) {
			Vec3 right_vec = player.getLookAngle().cross(new Vec3(0, 1, 0));
			newVel = newVel.add(right_vec.scale(-0.07));
		}
		if (right) {
			Vec3 right_vec = player.getLookAngle().cross(new Vec3(0, 1, 0));
			newVel = newVel.add(right_vec.scale(0.07));
		}
		// Apply drag
		newVel = newVel.scale(0.95);
		// Limit speed
		if (newVel.length() > 2.0) {
			newVel = newVel.normalize().scale(2.0);
		}
		entity.setDeltaMovement(newVel);
		// Simple rotation following
		Vec3 lookDirection = player.getLookAngle();
		double targetYaw = Math.toDegrees(Math.atan2(-lookDirection.x, lookDirection.z));
		double targetPitch = Math.toDegrees(Math.asin(-lookDirection.y));
		double currentYaw = entity.getYRot();
		double currentPitch = entity.getXRot();
		double yawDiff = Mth.wrapDegrees(targetYaw - currentYaw);
		double pitchDiff = Mth.wrapDegrees(targetPitch - currentPitch);
		entity.setYRot((float) (currentYaw + yawDiff * 0.2));
		entity.setXRot((float) (currentPitch + pitchDiff * 0.2));
	}
}
