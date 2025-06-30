package net.efkrdnz.starwarsverse;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

@Mod("starwarsverse")
public class XwingFlightHelper {

	public XwingFlightHelper() {
		NeoForge.EVENT_BUS.register(this);
		if (net.neoforged.fml.loading.FMLEnvironment.dist == Dist.CLIENT) {
			NeoForge.EVENT_BUS.register(ClientEvents.class);
		}
	}

	// server-side flight assistance
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent.Post event) {
		Player player = event.getEntity();
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			handleFlightAssistance(player, xwing);
		}
	}

	private void handleFlightAssistance(Player player, XwingAircraftEntity xwing) {
		Level level = player.level();
		if (level.isClientSide)
			return;

		// auto-collision avoidance
		handleCollisionAvoidance(xwing);

		// flight stabilization
		handleFlightStabilization(xwing);

		// ground proximity warnings
		handleGroundProximity(player, xwing);
	}

	private void handleCollisionAvoidance(XwingAircraftEntity xwing) {
		Vec3 velocity = xwing.getDeltaMovement();
		Vec3 position = xwing.position();
		Level level = xwing.level();

		// check for obstacles ahead
		Vec3 checkPos = position.add(velocity.normalize().scale(3.0));

		if (!level.isEmptyBlock(new net.minecraft.core.BlockPos((int) checkPos.x, (int) checkPos.y, (int) checkPos.z))) {
			// obstacle detected - apply gentle upward force
			Vec3 avoidanceForce = new Vec3(0, 0.1, 0);
			xwing.setDeltaMovement(velocity.add(avoidanceForce));
		}
	}

	private void handleFlightStabilization(XwingAircraftEntity xwing) {
		// prevent excessive spinning
		float currentPitch = xwing.getXRot();

		// limit extreme angles
		if (Math.abs(currentPitch) > 85) {
			xwing.setXRot(Math.signum(currentPitch) * 85);
		}
	}

	private void handleGroundProximity(Player player, XwingAircraftEntity xwing) {
		double groundDistance = xwing.getY() - xwing.level().getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING, xwing.blockPosition()).getY();

		if (groundDistance < 5.0 && xwing.getDeltaMovement().y < -0.5) {
			// ground proximity warning - could trigger warning sound/screen effect
			// apply gentle upward force to prevent crashes
			Vec3 currentVel = xwing.getDeltaMovement();
			xwing.setDeltaMovement(new Vec3(currentVel.x, Math.max(currentVel.y, -0.3), currentVel.z));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class ClientEvents {

		@SubscribeEvent
		public static void onClientPlayerTick(PlayerTickEvent.Post event) {
			if (!(event.getEntity() instanceof LocalPlayer player))
				return;
			if (!(player.getVehicle() instanceof XwingAircraftEntity xwing))
				return;

			handleClientSideEffects(player, xwing);
			handleAdvancedControls(player, xwing);
		}

		private static void handleClientSideEffects(LocalPlayer player, XwingAircraftEntity xwing) {
			Minecraft mc = Minecraft.getInstance();
			double speed = xwing.getDeltaMovement().length();

			// dynamic FOV based on speed
			if (mc.options.getCameraType().isFirstPerson()) {
				float baseFOV = mc.options.fov().get().floatValue();
				float speedFOV = (float) (baseFOV + (speed * 20)); // increase FOV with speed
				speedFOV = Mth.clamp(speedFOV, baseFOV, baseFOV + 40);

				// apply FOV change smoothly
				// note: this would require access to MC's FOV system
			}

			// speed-based screen effects
			if (speed > 2.0) {
				// could trigger speed lines or motion blur effects
				handleSpeedEffects(player, speed);
			}
		}

		private static void handleAdvancedControls(LocalPlayer player, XwingAircraftEntity xwing) {
			// mouse sensitivity adjustment for flight
			Vec3 velocity = xwing.getDeltaMovement();
			double speed = velocity.length();

			// reduce mouse sensitivity at high speeds for better control
			if (speed > 1.5) {
				// this would require custom mouse input handling
				// could be implemented through mixins or key binding modifications
			}

			// inertial dampening simulation
			handleInertialDampening(player, xwing);
		}

		private static void handleInertialDampening(LocalPlayer player, XwingAircraftEntity xwing) {
			// simulate Star Wars inertial dampening
			Vec3 velocity = xwing.getDeltaMovement();

			// reduce G-force effects by limiting sudden velocity changes
			if (velocity.length() > 0.1) {
				Vec3 previousVelocity = getPreviousVelocity(xwing);
				Vec3 acceleration = velocity.subtract(previousVelocity);

				if (acceleration.length() > 0.5) {
					// high G-force - apply dampening
					Vec3 dampenedVel = previousVelocity.add(acceleration.scale(0.7));
					xwing.setDeltaMovement(dampenedVel);
				}
			}

			storePreviousVelocity(xwing, velocity);
		}

		private static void handleSpeedEffects(LocalPlayer player, double speed) {
			// screen shake intensity based on speed
			if (player.getData(StarwarsverseModVariables.PLAYER_VARIABLES) != null) {
				// could set screen shake variables here
				// this integrates with your existing screen shake system
			}

			// engine sound volume based on speed
			// could trigger engine sound procedures with volume = speed / maxSpeed
		}

		// simple velocity storage for inertial dampening
		private static Vec3 previousVelocity = Vec3.ZERO;

		private static Vec3 getPreviousVelocity(XwingAircraftEntity xwing) {
			return previousVelocity;
		}

		private static void storePreviousVelocity(XwingAircraftEntity xwing, Vec3 velocity) {
			previousVelocity = velocity;
		}
	}

	// utility methods for other procedures to use
	public static class FlightUtils {

		public static boolean isPlayerInXwing(Player player) {
			return player.getVehicle() instanceof XwingAircraftEntity;
		}

		public static XwingAircraftEntity getPlayerXwing(Player player) {
			Entity vehicle = player.getVehicle();
			return vehicle instanceof XwingAircraftEntity ? (XwingAircraftEntity) vehicle : null;
		}

		public static double getFlightSpeed(XwingAircraftEntity xwing) {
			return xwing.getDeltaMovement().length();
		}

		public static Vec3 getFlightDirection(XwingAircraftEntity xwing) {
			Vec3 velocity = xwing.getDeltaMovement();
			return velocity.length() > 0.01 ? velocity.normalize() : Vec3.ZERO;
		}

		public static boolean isLanding(XwingAircraftEntity xwing) {
			double groundDistance = xwing.getY() - xwing.level().getHeightmapPos(net.minecraft.world.level.levelgen.Heightmap.Types.MOTION_BLOCKING, xwing.blockPosition()).getY();
			return groundDistance < 3.0 && xwing.getDeltaMovement().y <= 0;
		}

		public static void emergencyStabilize(XwingAircraftEntity xwing) {
			// emergency stabilization - reduce all rotational momentum
			xwing.setXRot(xwing.getXRot() * 0.5f);

			// reduce velocity to manageable levels
			Vec3 vel = xwing.getDeltaMovement();
			if (vel.length() > 1.5) {
				xwing.setDeltaMovement(vel.normalize().scale(1.5));
			}
		}
	}
}
