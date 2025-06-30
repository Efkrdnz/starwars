package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class ForceKeybindWithIndexProcedure {
	public static void execute(LevelAccessor world, Entity entity, double buttonIndex) {
		if (entity == null)
			return;
		double index = 0;
		String slot_to_append = "";
		index = buttonIndex;
		if (!entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).is_using_force) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.is_using_force = true;
				_vars.syncPlayerVariables(entity);
			}
			if (index == 1) {
				slot_to_append = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).bound_power_1;
			} else if (index == 2) {
				slot_to_append = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).bound_power_2;
			} else if (index == 3) {
				slot_to_append = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).bound_power_3;
			}
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.current_charging_power = slot_to_append;
				_vars.syncPlayerVariables(entity);
			}
			if ((entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).current_charging_power).equals("force_hold")) {
				ForceHoldTargetSetProcedure.execute(world, entity);
			}
			if ((entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).current_charging_power).equals("force_lightning")) {
				{
					StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.is_using_lightning = true;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}
