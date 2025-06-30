package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class EnhancedShipRightProcedure {
	public static void execute(Player player, int pressType, int duration) {
		if (player == null)
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (pressType == 0) { // key pressed
			playerVars.ship_r = true;
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				applyBanking(xwing, 1);
				triggerThrusterEffects(xwing, "right");
			}
		} else { // key released
			playerVars.ship_r = false;
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				returnToLevel(xwing);
			}
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void applyBanking(XwingAircraftEntity xwing, int direction) {
		float currentPitch = xwing.getXRot();
		float currentYaw = xwing.getYRot();
		xwing.setXRot(currentPitch + (direction * 2.0f));
		xwing.setYRot(currentYaw + (direction * 5.0f));
	}

	private static void returnToLevel(XwingAircraftEntity xwing) {
		float currentPitch = xwing.getXRot();
		if (Math.abs(currentPitch) > 1.0f) {
			xwing.setXRot(currentPitch * 0.9f);
		}
	}

	private static void triggerThrusterEffects(XwingAircraftEntity xwing, String direction) {
		// add thruster particle effects, sounds, etc.
	}
}
