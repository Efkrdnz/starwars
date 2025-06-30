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
		// CRITICAL: Use the MCreator style variable setting with sync
		{
			StarwarsverseModVariables.PlayerVariables _vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.ship_b = false;
			_vars.syncPlayerVariables(player);
		}
		// emergency brake for quick taps
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			applyEmergencyBrake(xwing);
		}
	}

	private static void applyEmergencyBrake(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// quick stop
		xwing.setDeltaMovement(currentVel.scale(0.7));
	}
}
