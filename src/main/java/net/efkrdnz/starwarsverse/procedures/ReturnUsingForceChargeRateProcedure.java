package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.ForcePowers;

public class ReturnUsingForceChargeRateProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		String current_used_power = "";
		current_used_power = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).current_charging_power;
		ForcePowers.ForcePowerDefinition power = ForcePowers.getPower(current_used_power);
		if (power == null) {
			return "none";
		}
		int maxcharge = power.getMaxChargeTime();
		return "[" + entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).charge_timer + "/" + maxcharge + "]";
	}
}
