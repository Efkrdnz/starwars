package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.util.Mth;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class XwingAircraftOnEntityTickUpdateProcedure {

	// Tunables (you can move these into a config later)
	private static final double MAX_SPEED = 2.5;      // max cruise speed
	private static final double BOOST_SPEED = 3.5;    // optional: when you add a boost flag
	private static final double ACCEL = 0.25;         // how quickly we approach target speed (0..1 lerp)
	private static final double STRAFE_SCALE = 0.60;  // how strong left/right is vs forward
	private static final double VERTICAL_LIFT = 0.10; // small lift to counter gravity while moving forward
	private static final double DRAG = 0.05;          // passive drag each tick when no input

	public static void execute(Entity entity) {
		if (entity == null) return;

		// Server-side authoritative physics only
		if (entity.level().isClientSide) return;

		Entity driver = entity.getFirstPassenger();
		if (!(driver instanceof LivingEntity)) return;

		// --- Input flags (kept exactly as your variables) ---
		var vars = driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		final boolean forward = vars.ship_f;
		final boolean backward = vars.ship_b;
		final boolean left = vars.ship_r;
		final boolean right = vars.ship_l;

		// --- Speed target (simple: forward/back decide sign). You can add boost/brake later. ---
		double baseMax = MAX_SPEED;
		double forwardInput = (forward ? 1.0 : 0.0) - (backward ? 1.0 : 0.0);
		double strafeInput  = (right ? 1.0 : 0.0)  - (left ? 1.0 : 0.0);

		// No inputs? Apply drag and bail.
		if (forwardInput == 0.0 && strafeInput == 0.0) {
			Vec3 v = entity.getDeltaMovement();
			// light drag to help the ship slowly come to rest
			entity.setDeltaMovement(v.scale(1.0 - DRAG));
			return;
		}

		// --- Build direction from driver orientation ---
		// Forward already includes yaw + pitch
		Vec3 forwardDir = driver.getLookAngle().normalize();

		// Compute "right" vector from forward & world-up.
		// Note: using up × forward gives a right-handed basis for Minecraft coords.
		Vec3 up = new Vec3(0, 1, 0);
		Vec3 rightDir = up.cross(forwardDir).normalize();

		// Combine forward & strafe
		Vec3 desiredDir = forwardDir.scale(forwardInput).add(rightDir.scale(strafeInput * STRAFE_SCALE));

		// Normalize if we have both axes pressed so diagonal isn’t faster
		if (desiredDir.lengthSqr() > 1.0e-6) {
			desiredDir = desiredDir.normalize();
		} else {
			// Extremely small vector—apply drag and exit
			entity.setDeltaMovement(entity.getDeltaMovement().scale(1.0 - DRAG));
			return;
		}

		// --- Vertical handling ---
		// forwardDir already has pitch baked in; to make flight feel better, add a tiny lift when moving forward
		double lift = (forwardInput > 0 ? VERTICAL_LIFT : 0.0);

		// Optional: clamp how steep pitch can drive vertical speed (prevents crazy stalls)
		// This keeps the "nose up/down" effect but less extreme.
		double pitchRad = ((LivingEntity) driver).getXRot() * Mth.DEG_TO_RAD;
		double verticalAssist = Mth.clamp(Math.sin(-pitchRad), -0.75, 0.75);

		// Final desired velocity
		double targetSpeed = baseMax;
		Vec3 targetVel = new Vec3(
				desiredDir.x * targetSpeed,
				desiredDir.y * targetSpeed + verticalAssist * 0.15 + lift,
				desiredDir.z * targetSpeed
		);

		// --- Smooth towards target (acceleration) ---
		Vec3 current = entity.getDeltaMovement();
		Vec3 newVel = lerpVec(current, targetVel, ACCEL);

		entity.setDeltaMovement(newVel);
	}

	private static Vec3 lerpVec(Vec3 a, Vec3 b, double t) {
		// Clamp t just in case
		t = Mth.clamp(t, 0.0, 1.0);
		return new Vec3(
				Mth.lerp(t, a.x, b.x),
				Mth.lerp(t, a.y, b.y),
				Mth.lerp(t, a.z, b.z)
		);
	}
}
