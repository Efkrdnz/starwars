
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.item.ItemProperties;

import net.efkrdnz.starwarsverse.procedures.LightsaberCountDookuColorReturnProcedure;
import net.efkrdnz.starwarsverse.procedures.LightsaberAnakinColorReturnProcedure;
import net.efkrdnz.starwarsverse.item.TestBlasterItem;
import net.efkrdnz.starwarsverse.item.SteelIngotItem;
import net.efkrdnz.starwarsverse.item.LightsaberCountDookuItem;
import net.efkrdnz.starwarsverse.item.LightsaberAnakinItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalYellowItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalWhiteItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalRedItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalPurpleItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalOrangeItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalGreenItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalBlueItem;
import net.efkrdnz.starwarsverse.item.KyberCrystalBlackItem;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

public class StarwarsverseModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(StarwarsverseMod.MODID);
	public static final DeferredItem<Item> LIGHTSABER_ANAKIN = REGISTRY.register("lightsaber_anakin", LightsaberAnakinItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_RED = REGISTRY.register("kyber_crystal_red", KyberCrystalRedItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_BLUE = REGISTRY.register("kyber_crystal_blue", KyberCrystalBlueItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_GREEN = REGISTRY.register("kyber_crystal_green", KyberCrystalGreenItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_WHITE = REGISTRY.register("kyber_crystal_white", KyberCrystalWhiteItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_YELLOW = REGISTRY.register("kyber_crystal_yellow", KyberCrystalYellowItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_ORANGE = REGISTRY.register("kyber_crystal_orange", KyberCrystalOrangeItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_PURPLE = REGISTRY.register("kyber_crystal_purple", KyberCrystalPurpleItem::new);
	public static final DeferredItem<Item> KYBER_CRYSTAL_BLACK = REGISTRY.register("kyber_crystal_black", KyberCrystalBlackItem::new);
	public static final DeferredItem<Item> KYBER_INFUSER = block(StarwarsverseModBlocks.KYBER_INFUSER);
	public static final DeferredItem<Item> LIGHTSABER_COUNT_DOOKU = REGISTRY.register("lightsaber_count_dooku", LightsaberCountDookuItem::new);
	public static final DeferredItem<Item> STEEL_INGOT = REGISTRY.register("steel_ingot", SteelIngotItem::new);
	public static final DeferredItem<Item> STEEL_CHASING = block(StarwarsverseModBlocks.STEEL_CHASING);
	public static final DeferredItem<Item> AIRCRAFT_MAINFRAME = block(StarwarsverseModBlocks.AIRCRAFT_MAINFRAME);
	public static final DeferredItem<Item> AIRCRAFT_ENGINE = block(StarwarsverseModBlocks.AIRCRAFT_ENGINE);
	public static final DeferredItem<Item> AIRCRAFT_WORKBENCH = block(StarwarsverseModBlocks.AIRCRAFT_WORKBENCH);
	public static final DeferredItem<Item> LIGHTSABER_LIGHT_BLOCK = block(StarwarsverseModBlocks.LIGHTSABER_LIGHT_BLOCK);
	public static final DeferredItem<Item> XWING_AIRCRAFT_SPAWN_EGG = REGISTRY.register("xwing_aircraft_spawn_egg", () -> new DeferredSpawnEggItem(StarwarsverseModEntities.XWING_AIRCRAFT, -1, -1, new Item.Properties()));
	public static final DeferredItem<Item> MULTIPURPOSE_WORKBENCH = block(StarwarsverseModBlocks.MULTIPURPOSE_WORKBENCH);
	public static final DeferredItem<Item> TEST_BLASTER = REGISTRY.register("test_blaster", TestBlasterItem::new);

	// Start of user code block custom items
	// End of user code block custom items
	private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
		return REGISTRY.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
	}

	@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ItemsClientSideHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public static void clientLoad(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ItemProperties.register(LIGHTSABER_ANAKIN.get(), ResourceLocation.parse("starwarsverse:lightsaber_anakin_color"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) LightsaberAnakinColorReturnProcedure.execute(itemStackToRender));
				ItemProperties.register(LIGHTSABER_COUNT_DOOKU.get(), ResourceLocation.parse("starwarsverse:lightsaber_count_dooku_color"),
						(itemStackToRender, clientWorld, entity, itemEntityId) -> (float) LightsaberCountDookuColorReturnProcedure.execute(itemStackToRender));
			});
		}
	}
}
