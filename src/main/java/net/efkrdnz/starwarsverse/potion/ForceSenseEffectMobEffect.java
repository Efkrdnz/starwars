
package net.efkrdnz.starwarsverse.potion;

import net.neoforged.neoforge.common.EffectCure;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;

import java.util.Set;

public class ForceSenseEffectMobEffect extends MobEffect {
	public ForceSenseEffectMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -6710785);
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
	}
}
