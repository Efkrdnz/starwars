package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class ReturnForceGrabDistanceProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).is_using_telekinesis) {
			return "" + entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).telekinesis_distance;
		}
		return "None";
	}
}
