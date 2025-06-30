
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import net.efkrdnz.starwarsverse.potion.ScreenShakeMobEffect;
import net.efkrdnz.starwarsverse.potion.GlientGlowEffectMobEffect;
import net.efkrdnz.starwarsverse.potion.ForceSenseEffectMobEffect;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

public class StarwarsverseModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, StarwarsverseMod.MODID);
	public static final DeferredHolder<MobEffect, MobEffect> SCREEN_SHAKE = REGISTRY.register("screen_shake", () -> new ScreenShakeMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> GLIENT_GLOW_EFFECT = REGISTRY.register("glient_glow_effect", () -> new GlientGlowEffectMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> FORCE_SENSE_EFFECT = REGISTRY.register("force_sense_effect", () -> new ForceSenseEffectMobEffect());
}
