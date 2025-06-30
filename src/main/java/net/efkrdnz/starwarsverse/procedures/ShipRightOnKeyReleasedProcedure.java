package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipRightOnKeyReleasedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		playerVars.ship_r = false;
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			returnToLevel(xwing);
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void returnToLevel(XwingAircraftEntity xwing) {
		float currentPitch = xwing.getXRot();
		if (Math.abs(currentPitch) > 1.0f) {
			xwing.setXRot(currentPitch * 0.9f);
		}
	}
}
