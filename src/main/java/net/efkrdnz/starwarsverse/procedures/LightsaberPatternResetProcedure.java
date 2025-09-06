package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class LightsaberPatternResetProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		// reset attack pattern to 0
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		vars.lightsaber_attack_pattern = 0;
		vars.syncPlayerVariables(entity);
	}
}
