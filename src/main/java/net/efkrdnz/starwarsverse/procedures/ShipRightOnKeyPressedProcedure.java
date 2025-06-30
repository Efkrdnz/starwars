package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class ShipRightOnKeyPressedProcedure {
	public static void execute(Entity entity) {
		if (!(entity instanceof Player player))
			return;
		// CRITICAL: Use the MCreator style variable setting with sync
		{
			StarwarsverseModVariables.PlayerVariables _vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.ship_r = true;
			_vars.syncPlayerVariables(player);
		}
		if (player.getVehicle() instanceof XwingAircraftEntity xwing) {
			applyBanking(xwing, 1);
			triggerThrusterEffects(xwing, "right");
		}
	}

	private static void applyBanking(XwingAircraftEntity xwing, int direction) {
		float currentPitch = xwing.getXRot();
		float currentYaw = xwing.getYRot();
		xwing.setXRot(currentPitch + (direction * 2.0f));
		xwing.setYRot(currentYaw + (direction * 5.0f));
	}

	private static void triggerThrusterEffects(XwingAircraftEntity xwing, String direction) {
		// placeholder for thruster effects
	}
}
