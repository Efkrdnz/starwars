
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.BuiltInRegistries;

import net.efkrdnz.starwarsverse.block.entity.LightsaberLightBlockBlockEntity;
import net.efkrdnz.starwarsverse.block.entity.KyberInfuserBlockEntity;
import net.efkrdnz.starwarsverse.block.entity.AircraftWorkbenchBlockEntity;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class StarwarsverseModBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, StarwarsverseMod.MODID);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> KYBER_INFUSER = register("kyber_infuser", StarwarsverseModBlocks.KYBER_INFUSER, KyberInfuserBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> AIRCRAFT_WORKBENCH = register("aircraft_workbench", StarwarsverseModBlocks.AIRCRAFT_WORKBENCH, AircraftWorkbenchBlockEntity::new);
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> LIGHTSABER_LIGHT_BLOCK = register("lightsaber_light_block", StarwarsverseModBlocks.LIGHTSABER_LIGHT_BLOCK, LightsaberLightBlockBlockEntity::new);

	// Start of user code block custom block entities
	// End of user code block custom block entities
	private static DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> register(String registryname, DeferredHolder<Block, Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
		return REGISTRY.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, KYBER_INFUSER.get(), (blockEntity, side) -> ((KyberInfuserBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, AIRCRAFT_WORKBENCH.get(), (blockEntity, side) -> ((AircraftWorkbenchBlockEntity) blockEntity).getItemHandler());
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, LIGHTSABER_LIGHT_BLOCK.get(), (blockEntity, side) -> ((LightsaberLightBlockBlockEntity) blockEntity).getItemHandler());
	}
}
