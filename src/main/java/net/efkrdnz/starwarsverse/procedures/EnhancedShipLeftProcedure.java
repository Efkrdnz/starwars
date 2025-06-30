package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class EnhancedShipLeftProcedure {
	public static void execute(Player player, int pressType, int duration) {
		if (player == null)
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (pressType == 0) { // key pressed
			playerVars.ship_l = true;
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				// apply immediate banking through pitch adjustment
				applyBanking(xwing, -1);
				// trigger maneuvering thruster effects
				triggerThrusterEffects(xwing, "left");
			}
		} else { // key released
			playerVars.ship_l = false;
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				// return to level flight gradually
				returnToLevel(xwing);
			}
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void applyBanking(XwingAircraftEntity xwing, int direction) {
		// simulate banking through slight pitch and yaw adjustments
		float currentPitch = xwing.getXRot();
		float currentYaw = xwing.getYRot();
		// slight nose down and turn when banking
		xwing.setXRot(currentPitch + (direction * 2.0f));
		xwing.setYRot(currentYaw + (direction * 5.0f));
	}

	private static void returnToLevel(XwingAircraftEntity xwing) {
		// gradually return pitch to level
		float currentPitch = xwing.getXRot();
		if (Math.abs(currentPitch) > 1.0f) {
			xwing.setXRot(currentPitch * 0.9f);
		}
	}

	private static void triggerThrusterEffects(XwingAircraftEntity xwing, String direction) {
		// add thruster particle effects, sounds, etc.
	}
}
