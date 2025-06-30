package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipForwardOnKeyReleasedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		playerVars.ship_f = false;
		// afterburner effect for quick taps (you can add timing logic later)
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			// trigger engine shutdown effects
			triggerEngineEffects(xwing, false);
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void triggerEngineEffects(XwingAircraftEntity xwing, boolean starting) {
		// placeholder for engine sound/particle effects
	}
}
