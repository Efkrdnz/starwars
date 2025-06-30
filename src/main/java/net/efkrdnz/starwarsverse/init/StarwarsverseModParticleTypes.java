
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleType;

import net.efkrdnz.starwarsverse.StarwarsverseMod;

public class StarwarsverseModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(Registries.PARTICLE_TYPE, StarwarsverseMod.MODID);
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING = REGISTRY.register("lightning", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING_BASE = REGISTRY.register("lightning_base", () -> new SimpleParticleType(true));
	public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LIGHTNING_RED = REGISTRY.register("lightning_red", () -> new SimpleParticleType(true));
}
