package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;

public class ScreenShakeInsensityProcedure {
	public static double execute(Entity entity) {
		if (entity == null)
			return 0;
		return (entity instanceof LivingEntity _livEnt && _livEnt.hasEffect(StarwarsverseModMobEffects.SCREEN_SHAKE) ? _livEnt.getEffect(StarwarsverseModMobEffects.SCREEN_SHAKE).getAmplifier() : 0) / 10;
	}
}
