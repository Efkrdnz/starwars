package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipBackwardOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;

		StarwarsverseModVariables.PlayerVariables playerVars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		playerVars.ship_b = true;

		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			// apply reverse thruster effects
			triggerReverseThrusterEffects(xwing);
			applySpeedBrakes(xwing);
		}

		player.setData(StarwarsverseModVariables.PLAYER_VARIABLES, playerVars);
	}

	private static void triggerReverseThrusterEffects(XwingAircraftEntity xwing) {
		// placeholder for reverse thruster effects
	}

	private static void applySpeedBrakes(XwingAircraftEntity xwing) {
		Vec3 currentVel = xwing.getDeltaMovement();
		// gradual deceleration with speed brakes
		xwing.setDeltaMovement(currentVel.scale(0.95));
	}
}
