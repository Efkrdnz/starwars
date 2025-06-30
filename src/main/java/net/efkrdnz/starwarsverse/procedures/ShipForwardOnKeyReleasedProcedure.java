package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipForwardOnKeyReleasedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;
		// CRITICAL: Use the MCreator style variable setting with sync
		{
			StarwarsverseModVariables.PlayerVariables _vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.ship_f = false;
			_vars.syncPlayerVariables(player);
		}
		// afterburner effect for quick taps
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			// trigger engine shutdown effects
			triggerEngineEffects(xwing, false);
		}
	}

	private static void triggerEngineEffects(XwingAircraftEntity xwing, boolean starting) {
		// placeholder for engine sound/particle effects
	}
}
