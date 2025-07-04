
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.core.registries.Registries;

import net.efkrdnz.starwarsverse.StarwarsverseMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class StarwarsverseModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StarwarsverseMod.MODID);

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {

			tabData.accept(StarwarsverseModBlocks.KYBER_INFUSER.get().asItem());

		} else if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

			tabData.accept(StarwarsverseModItems.XWING_AIRCRAFT_SPAWN_EGG.get());

		}
	}
}
