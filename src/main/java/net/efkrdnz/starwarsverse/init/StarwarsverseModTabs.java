
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import net.efkrdnz.starwarsverse.StarwarsverseMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class StarwarsverseModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, StarwarsverseMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> STARWARS_ITEMS = REGISTRY.register("starwars_items",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.starwarsverse.starwars_items")).icon(() -> new ItemStack(StarwarsverseModItems.KYBER_CRYSTAL_RED.get())).displayItems((parameters, tabData) -> {
				tabData.accept(StarwarsverseModItems.LIGHTSABER_ANAKIN.get());
				tabData.accept(StarwarsverseModItems.LIGHTSABER_COUNT_DOOKU.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_RED.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_BLUE.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_GREEN.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_WHITE.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_YELLOW.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_ORANGE.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_PURPLE.get());
				tabData.accept(StarwarsverseModItems.KYBER_CRYSTAL_BLACK.get());
				tabData.accept(StarwarsverseModBlocks.MULTIPURPOSE_WORKBENCH.get().asItem());
			})

					.build());

	@SubscribeEvent
	public static void buildTabContentsVanilla(BuildCreativeModeTabContentsEvent tabData) {
		if (tabData.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {

			tabData.accept(StarwarsverseModBlocks.KYBER_INFUSER.get().asItem());

		} else if (tabData.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

			tabData.accept(StarwarsverseModItems.XWING_AIRCRAFT_SPAWN_EGG.get());

		}
	}
}
