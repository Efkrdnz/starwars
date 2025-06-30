package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;

public class ScreenShakeProcedureProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(StarwarsverseModMobEffects.SCREEN_SHAKE)) {
			return true;
		}
		return false;
	}
}
