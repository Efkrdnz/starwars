
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.efkrdnz.starwarsverse.client.particle.LightningRedParticle;
import net.efkrdnz.starwarsverse.client.particle.LightningParticle;
import net.efkrdnz.starwarsverse.client.particle.LightningBaseParticle;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StarwarsverseModParticles {
	@SubscribeEvent
	public static void registerParticles(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(StarwarsverseModParticleTypes.LIGHTNING.get(), LightningParticle::provider);
		event.registerSpriteSet(StarwarsverseModParticleTypes.LIGHTNING_BASE.get(), LightningBaseParticle::provider);
		event.registerSpriteSet(StarwarsverseModParticleTypes.LIGHTNING_RED.get(), LightningRedParticle::provider);
	}
}
