package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;

public class DoesHaveForceSenseProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(StarwarsverseModMobEffects.FORCE_SENSE_EFFECT)) {
			return true;
		}
		return false;
	}
}
