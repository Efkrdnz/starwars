package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class ReturnForcePowerProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return "[" + new java.text.DecimalFormat("##").format(entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power) + "/"
				+ new java.text.DecimalFormat("##").format(entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).max_force_power) + "]";
	}
}
