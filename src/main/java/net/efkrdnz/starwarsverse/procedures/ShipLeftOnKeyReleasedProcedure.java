package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipLeftOnKeyReleasedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		playerVars.ship_l = false;
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			// return to level flight gradually
			returnToLevel(xwing);
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void returnToLevel(XwingAircraftEntity xwing) {
		// gradually return pitch to level
		float currentPitch = xwing.getXRot();
		if (Math.abs(currentPitch) > 1.0f) {
			xwing.setXRot(currentPitch * 0.9f);
		}
	}
}
