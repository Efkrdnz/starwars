
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.efkrdnz.starwarsverse.StarwarsverseMod;

public class StarwarsverseModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, StarwarsverseMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTSABER_OPEN = REGISTRY.register("lightsaber_open", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "lightsaber_open")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTSABER_CLOSE = REGISTRY.register("lightsaber_close", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "lightsaber_close")));
	public static final DeferredHolder<SoundEvent, SoundEvent> HIT1 = REGISTRY.register("hit1", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "hit1")));
	public static final DeferredHolder<SoundEvent, SoundEvent> HIT2 = REGISTRY.register("hit2", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "hit2")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTSABER_AMBIENT = REGISTRY.register("lightsaber_ambient", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "lightsaber_ambient")));
	public static final DeferredHolder<SoundEvent, SoundEvent> LIGHTSABER_AMBIENT_LONGER = REGISTRY.register("lightsaber_ambient_longer",
			() -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "lightsaber_ambient_longer")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FORCE_PUSH = REGISTRY.register("force_push", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "force_push")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FORCE_CHARGE = REGISTRY.register("force_charge", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "force_charge")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FORCE_PULL = REGISTRY.register("force_pull", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "force_pull")));
	public static final DeferredHolder<SoundEvent, SoundEvent> FORCE_SENSE = REGISTRY.register("force_sense", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("starwarsverse", "force_sense")));
}
