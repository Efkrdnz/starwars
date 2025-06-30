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
		if (driver == null) {
			// no pilot - X-wing idles and slowly descends
			if (entity instanceof XwingAircraftEntity) {
				((XwingAircraftEntity) entity).setAnimation("idle_1");
			}
			handleNoPilot(entity);
			return;
		}
		// pilot is present
		if (entity instanceof XwingAircraftEntity) {
			((XwingAircraftEntity) entity).setAnimation("idle_2");
		}
		// get control inputs
		boolean forward = driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_f;
		boolean backward = driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_b;
		boolean left = driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l;
		boolean right = driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r;
		// enhanced flight controls
		handleFlightControls(entity, driver, forward, backward, left, right);
	}

	private static void handleNoPilot(Entity entity) {
		Vec3 currentVel = entity.getDeltaMovement();
		// gentle descent when no pilot
		double drag = 0.85; // air resistance
		double gravityEffect = -0.05; // slow fall
		entity.setDeltaMovement(new Vec3(currentVel.x * drag, Math.max(currentVel.y + gravityEffect, -0.5), // terminal velocity
				currentVel.z * drag));
		// auto-level the ship gradually
		float currentPitch = entity.getXRot();
		if (Math.abs(currentPitch) > 1.0f) {
			entity.setXRot(currentPitch * 0.95f);
		}
	}

	private static void handleFlightControls(Entity entity, Entity driver, boolean forward, boolean backward, boolean left, boolean right) {
		// get current velocity and rotation
		Vec3 currentVel = entity.getDeltaMovement();
		Vec3 lookDirection = driver.getLookAngle();
		// flight parameters
		double maxSpeed = 2.5;
		double acceleration = 0.15;
		double deceleration = 0.92;
		double turnRate = 0.08;
		// calculate desired velocity based on inputs
		Vec3 desiredVel = calculateDesiredVelocity(entity, driver, forward, backward, left, right, lookDirection, maxSpeed);
		// smooth velocity interpolation
		Vec3 newVel = smoothVelocityTransition(currentVel, desiredVel, acceleration, deceleration);
		// apply banking physics when turning
		newVel = applyBankingPhysics(entity, newVel, left, right);
		// apply atmospheric effects
		newVel = applyAtmosphericEffects(entity, newVel);
		// set the new velocity
		entity.setDeltaMovement(newVel);
		// handle ship rotation
		handleShipRotation(entity, driver, left, right, forward, backward, turnRate);
		// add engine effects based on throttle
		addEngineEffects(entity, forward, backward, newVel.length());
	}

	private static Vec3 calculateDesiredVelocity(Entity entity, Entity driver, boolean forward, boolean backward, boolean left, boolean right, Vec3 lookDirection, double maxSpeed) {
		Vec3 desiredVel = Vec3.ZERO;
		// forward/backward movement
		if (forward && !backward) {
			desiredVel = lookDirection.scale(maxSpeed);
		} else if (backward && !forward) {
			desiredVel = lookDirection.scale(-maxSpeed * 0.6); // reverse slower
		}
		// strafe movement using cross product for side vectors
		if (left || right) {
			Vec3 rightVector = lookDirection.cross(new Vec3(0, 1, 0)).normalize();
			Vec3 strafeVel = Vec3.ZERO;
			if (left && !right) {
				strafeVel = rightVector.scale(-maxSpeed * 0.7);
			} else if (right && !left) {
				strafeVel = rightVector.scale(maxSpeed * 0.7);
			}
			// combine forward and strafe movement
			if (forward || backward) {
				desiredVel = desiredVel.add(strafeVel.scale(0.5)); // reduce strafe when moving forward
			} else {
				desiredVel = strafeVel;
			}
		}
		return desiredVel;
	}

	private static Vec3 smoothVelocityTransition(Vec3 currentVel, Vec3 desiredVel, double acceleration, double deceleration) {
		Vec3 velDiff = desiredVel.subtract(currentVel);
		double diffLength = velDiff.length();
		if (diffLength < 0.01) {
			return desiredVel;
		}
		// determine if we're accelerating or decelerating
		double rate = (desiredVel.length() > currentVel.length()) ? acceleration : acceleration * 1.5;
		// apply smooth interpolation
		double maxChange = rate;
		if (diffLength > maxChange) {
			velDiff = velDiff.normalize().scale(maxChange);
		}
		Vec3 newVel = currentVel.add(velDiff);
		// apply deceleration when no input
		if (desiredVel.length() < 0.1) {
			newVel = newVel.scale(deceleration);
		}
		return newVel;
	}

	private static Vec3 applyBankingPhysics(Entity entity, Vec3 velocity, boolean left, boolean right) {
		if (!left && !right)
			return velocity;
		double bankFactor = 0.02; // how much banking affects movement
		double currentSpeed = velocity.length();
		if (currentSpeed < 0.1)
			return velocity;
		// calculate banking effect using velocity modification instead of rotation
		Vec3 bankingForce = Vec3.ZERO;
		if (left && !right) {
			// bank left - creates downward and leftward force
			bankingForce = new Vec3(-bankFactor * currentSpeed, -bankFactor * currentSpeed * 0.3, 0);
		} else if (right && !left) {
			// bank right - creates downward and rightward force  
			bankingForce = new Vec3(bankFactor * currentSpeed, -bankFactor * currentSpeed * 0.3, 0);
		}
		return velocity.add(bankingForce);
	}

	private static Vec3 applyAtmosphericEffects(Entity entity, Vec3 velocity) {
		// get altitude effect
		double altitude = entity.getY();
		double atmosphericDensity = Math.max(0.3, 1.0 - (altitude - 64) / 200.0); // thinner air at high altitude
		// wind resistance increases with speed
		double speed = velocity.length();
		double windResistance = Math.min(0.98, 0.85 + (0.13 * atmosphericDensity));
		// reduce resistance for very slow speeds to prevent getting stuck
		if (speed < 0.1) {
			windResistance = 0.99;
		}
		return velocity.scale(windResistance);
	}

	private static void handleShipRotation(Entity entity, Entity driver, boolean left, boolean right, boolean forward, boolean backward, double turnRate) {
		Vec3 lookDirection = driver.getLookAngle();
		// make the X-wing follow the pilot's look direction with smooth interpolation
		double targetYaw = Math.toDegrees(Math.atan2(-lookDirection.x, lookDirection.z));
		double targetPitch = Math.toDegrees(Math.asin(-lookDirection.y));
		double currentYaw = entity.getYRot();
		double currentPitch = entity.getXRot();
		// smooth rotation interpolation
		double yawDiff = Mth.wrapDegrees(targetYaw - currentYaw);
		double pitchDiff = Mth.wrapDegrees(targetPitch - currentPitch);
		// apply rotation with speed based on input
		double rotationSpeed = (forward || backward) ? 0.3 : 0.15; // faster turning when moving
		entity.setYRot((float) (currentYaw + yawDiff * rotationSpeed));
		entity.setXRot((float) (currentPitch + pitchDiff * rotationSpeed));
		// simulate banking through slight pitch adjustments when turning
		if (left && !right) {
			entity.setXRot(entity.getXRot() - 1.0f); // slight nose down when banking left
		} else if (right && !left) {
			entity.setXRot(entity.getXRot() - 1.0f); // slight nose down when banking right
		}
	}

	private static void addEngineEffects(Entity entity, boolean forward, boolean backward, double currentSpeed) {
		// placeholder for future enhancements
		if (forward && currentSpeed > 1.0) {
			// could add engine trail particles here
			// could add engine sound based on speed
		}
		if (backward) {
			// could add reverse thruster effects
		}
		// screen shake based on speed for immersion
		if (entity.getControllingPassenger() instanceof Player player) {
			if (currentSpeed > 2.0) {
				// trigger screen shake procedure if available
				// this would work with your existing screen shake system
			}
		}
	}
}
