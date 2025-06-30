package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipBackwardOnKeyReleasedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;
		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		playerVars.ship_b = false;
		// emergency brake for quick taps (add timing logic later if needed)
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			applyEmergencyBrake(xwing);
		}
		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void applyEmergencyBrake(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// quick stop
		xwing.setDeltaMovement(currentVel.scale(0.7));
	}
}
