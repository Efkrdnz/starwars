package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class EnhancedShipBackwardProcedure {
	public static void execute(Player player, int pressType, int duration) {
		if (player == null)
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (pressType == 0) { // key pressed
			playerVars.ship_b = true;
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				// apply reverse thruster effects
				triggerReverseThrusterEffects(xwing);
				applySpeedBrakes(xwing);
			}
		} else { // key released
			playerVars.ship_b = false;
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				// emergency brake for quick taps
				if (duration < 200) {
					applyEmergencyBrake(xwing);
				}
			}
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void triggerReverseThrusterEffects(XwingAircraftEntity xwing) {
		// add reverse thruster particles and sounds
	}

	private static void applySpeedBrakes(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// gradual deceleration with speed brakes
		xwing.setDeltaMovement(currentVel.scale(0.95));
	}

	private static void applyEmergencyBrake(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// quick stop
		xwing.setDeltaMovement(currentVel.scale(0.3));
		// add dramatic screen shake
		if (xwing.getControllingPassenger() instanceof Player player) {
			// trigger emergency brake screen shake
		}
	}
}
