package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipLeftOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;
		// CRITICAL: Use the MCreator style variable setting with sync
		{
			StarwarsverseModVariables.PlayerVariables _vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.ship_l = true;
			_vars.syncPlayerVariables(player);
		}
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			// apply banking through pitch adjustment
			applyBanking(xwing, -1);
			// trigger thruster effects
			triggerThrusterEffects(xwing, "left");
		}
	}

	private static void applyBanking(XwingAircraftEntity xwing, int direction) {
		// simulate banking through slight pitch and yaw adjustments
		float currentPitch = xwing.getXRot();
		float currentYaw = xwing.getYRot();
		// slight nose down and turn when banking
		xwing.setXRot(currentPitch + (direction * 2.0f));
		xwing.setYRot(currentYaw + (direction * 5.0f));
	}

	private static void triggerThrusterEffects(XwingAircraftEntity xwing, String direction) {
		// placeholder for thruster effects
	}
}
