package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class CheckShipInvisibilityProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null || !(entity instanceof Player player)) {
			return false;
		}
		return ReturnRidingAircraftProcedure.execute(player) != null;
	}
}
