package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class ShipRightOnKeyReleasedProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.ship_r = false;
			_vars.syncPlayerVariables(entity);
		}
	}
}
