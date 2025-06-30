package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class EnhancedShipForwardProcedure {
	public static void execute(Player player, int pressType, int duration) {
		if (player == null)
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (pressType == 0) { // key pressed
			playerVars.ship_f = true;
			// add boost effect for long presses
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				// trigger engine startup sound/effects
				triggerEngineEffects(xwing, true);
			}
		} else { // key released
			playerVars.ship_f = false;
			// handle afterburner effect for quick taps
			if (duration < 300 && player.getVehicle() instanceof XwingAircraftEntity xwing) {
				applyAfterburner(xwing);
			}
			if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
				triggerEngineEffects(xwing, false);
			}
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void triggerEngineEffects(XwingAircraftEntity xwing, boolean starting) {
		// add engine sound, particles, or other effects here
		// this can trigger your existing procedures for effects
	}

	private static void applyAfterburner(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		Vec3 lookDirection = xwing.getControllingPassenger().getLookAngle();
		// quick burst of speed
		Vec3 burst = lookDirection.scale(0.8);
		xwing.setDeltaMovement(currentVel.add(burst));
		// add screen shake for impact
		if (xwing.getControllingPassenger() instanceof Player player) {
			// trigger your screen shake procedure here
		}
	}
}
