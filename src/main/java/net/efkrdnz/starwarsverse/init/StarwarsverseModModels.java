
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.efkrdnz.starwarsverse.client.model.Modelblank_entity;
import net.efkrdnz.starwarsverse.client.model.Modelattack;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class StarwarsverseModModels {
	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(Modelattack.LAYER_LOCATION, Modelattack::createBodyLayer);
		event.registerLayerDefinition(Modelblank_entity.LAYER_LOCATION, Modelblank_entity::createBodyLayer);
	}
}
