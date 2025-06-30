package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipForwardOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;

		// CRITICAL: Use the MCreator style variable setting with sync
		{
			StarwarsverseModVariables.PlayerVariables _vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.ship_f = true;
			_vars.syncPlayerVariables(player);
		}

		// add boost effect for engine startup
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			// trigger engine startup effects
			triggerEngineEffects(xwing, true);
		}
	}

	private static void triggerEngineEffects(XwingAircraftEntity xwing, boolean starting) {
		// placeholder for engine sound/particle effects
	}
}
