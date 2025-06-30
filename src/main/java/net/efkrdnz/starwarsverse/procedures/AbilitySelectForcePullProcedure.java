package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class AbilitySelectForcePullProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double slot = 0;
		String power = "";
		slot = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).gui_slot_index;
		power = "force_pull";
		if (slot == 1) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.bound_power_1 = power;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player)
				_player.closeContainer();
		} else if (slot == 2) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.bound_power_2 = power;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player)
				_player.closeContainer();
		} else if (slot == 3) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.bound_power_3 = power;
				_vars.syncPlayerVariables(entity);
			}
			if (entity instanceof Player _player)
				_player.closeContainer();
		}
	}
}
