package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;

public class ForceSenseUseProcedure {
	public static void execute(Entity target, double charge, double cost, double max_charge) {
		if (target == null)
			return;
		Entity force_user = null;
		double charge_status = 0;
		double force_cost = 0;
		double chargemax = 0;
		force_user = target;
		chargemax = max_charge;
		charge_status = charge / chargemax;
		force_cost = cost;
		if (force_user instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(StarwarsverseModMobEffects.FORCE_SENSE_EFFECT, (int) (charge_status * (100 + 10 * force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level)), 1, false, false));
		{
			StarwarsverseModVariables.PlayerVariables _vars = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.force_power = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power - force_cost;
			_vars.syncPlayerVariables(force_user);
		}
	}
}
