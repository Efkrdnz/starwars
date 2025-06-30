package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class XwingCombatManeuversProcedure {
	// barrel roll maneuver (can be triggered by double-tapping turn keys)
	public static void executeBarrelRoll(XwingAircraftEntity xwing, int direction) {
		if (xwing.getControllingPassenger() instanceof Player pilot) {
			// simulate barrel roll through rapid yaw changes
			float rollSpeed = direction * 15.0f;
			xwing.setYRot(xwing.getYRot() + rollSpeed);
			// maintain forward momentum during roll
			Vec3 currentVel = xwing.getDeltaMovement();
			Vec3 forwardDirection = pilot.getLookAngle();
			// slight course correction during roll
			Vec3 rollVel = currentVel.add(forwardDirection.scale(0.1));
			xwing.setDeltaMovement(rollVel);
			// add dramatic effect
			triggerBarrelRollEffects(xwing);
		}
	}

	// immelmann turn (climb and flip)
	public static void executeImmelmann(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// climb phase
		Vec3 climbVel = new Vec3(currentVel.x * 0.7, currentVel.y + 0.3, currentVel.z * 0.7);
		xwing.setDeltaMovement(climbVel);
		// pitch up aggressively
		xwing.setXRot(xwing.getXRot() - 45.0f);
		// start the flip
		xwing.setYRot(xwing.getYRot() + 180.0f);
		triggerImmelmannEffects(xwing);
	}

	// evasive jinking (random small movements)
	public static void executeEvasiveJinking(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// random evasive movements
		double jinkX = (Math.random() - 0.5) * 0.4;
		double jinkY = (Math.random() - 0.5) * 0.2;
		double jinkZ = (Math.random() - 0.5) * 0.4;
		Vec3 jinkVel = currentVel.add(new Vec3(jinkX, jinkY, jinkZ));
		xwing.setDeltaMovement(jinkVel);
		// random pitch and yaw adjustments
		xwing.setXRot(xwing.getXRot() + (float) ((Math.random() - 0.5) * 10));
		xwing.setYRot(xwing.getYRot() + (float) ((Math.random() - 0.5) * 15));
	}

	private static void triggerBarrelRollEffects(XwingAircraftEntity xwing) {
		// add screen shake, particle effects, sounds
		if (xwing.getControllingPassenger() instanceof Player player) {
			// trigger your screen shake procedure with high intensity
		}
	}

	private static void triggerImmelmannEffects(XwingAircraftEntity xwing) {
		// add climb effects and engine strain sounds
	}
}
