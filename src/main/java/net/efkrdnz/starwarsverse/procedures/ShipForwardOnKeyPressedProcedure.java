package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipForwardOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;

		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		playerVars.ship_f = true;

		// add boost effect for engine startup
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			// trigger engine startup effects
			triggerEngineEffects(xwing, true);
		}

		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void triggerEngineEffects(XwingAircraftEntity xwing, boolean starting) {
		// placeholder for engine sound/particle effects
	}
}
