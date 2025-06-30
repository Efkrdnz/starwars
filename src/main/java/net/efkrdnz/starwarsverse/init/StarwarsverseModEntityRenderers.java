
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.efkrdnz.starwarsverse.client.renderer.XwingAircraftRenderer;
import net.efkrdnz.starwarsverse.client.renderer.PlanetTatooineRenderer;
import net.efkrdnz.starwarsverse.client.renderer.PlanetEarthRenderer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StarwarsverseModEntityRenderers {
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(StarwarsverseModEntities.PLANET_EARTH.get(), PlanetEarthRenderer::new);
		event.registerEntityRenderer(StarwarsverseModEntities.PLANET_TATOOINE.get(), PlanetTatooineRenderer::new);
		event.registerEntityRenderer(StarwarsverseModEntities.XWING_AIRCRAFT.get(), XwingAircraftRenderer::new);
	}
}
