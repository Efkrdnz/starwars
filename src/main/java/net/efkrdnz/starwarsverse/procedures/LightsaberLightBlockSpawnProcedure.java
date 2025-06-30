package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.init.StarwarsverseModBlocks;

import javax.annotation.Nullable;

@EventBusSubscriber
public class LightsaberLightBlockSpawnProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity().level(), event.getEntity());
	}

	public static void execute(LevelAccessor world, Entity entity) {
		execute(null, world, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, Entity entity) {
		if (entity == null || world.isClientSide())
			return;
		BlockPos lightPos = BlockPos.containing(entity.getX(), entity.getY() + 1.6, entity.getZ());
		BlockState currentBlock = world.getBlockState(lightPos);
		boolean hasActiveLightsaber = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("enabled")
				|| (entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("enabled");
		if (hasActiveLightsaber) {
			// Only place light block if there isn't already one there
			if (currentBlock.getBlock() == Blocks.AIR) {
				world.setBlock(lightPos, StarwarsverseModBlocks.LIGHTSABER_LIGHT_BLOCK.get().defaultBlockState(), 3);
				// Set initial timestamp when placing the block
				BlockEntity blockEntity = world.getBlockEntity(lightPos);
				if (blockEntity != null && world instanceof Level level) {
					blockEntity.getPersistentData().putLong("placedTime", level.getGameTime());
					blockEntity.getPersistentData().putString("ownerUUID", entity.getUUID().toString());
				}
			} else if (currentBlock.getBlock() == StarwarsverseModBlocks.LIGHTSABER_LIGHT_BLOCK.get()) {
				// Refresh the timestamp of existing light block if it belongs to this player
				BlockEntity blockEntity = world.getBlockEntity(lightPos);
				if (blockEntity != null && world instanceof Level level) {
					String ownerUUID = blockEntity.getPersistentData().getString("ownerUUID");
					if (ownerUUID.equals(entity.getUUID().toString())) {
						blockEntity.getPersistentData().putLong("placedTime", level.getGameTime());
					}
				}
			}
		} else {
			// Remove light block if lightsaber is deactivated and this player owns it
			if (currentBlock.getBlock() == StarwarsverseModBlocks.LIGHTSABER_LIGHT_BLOCK.get()) {
				BlockEntity blockEntity = world.getBlockEntity(lightPos);
				if (blockEntity != null) {
					String ownerUUID = blockEntity.getPersistentData().getString("ownerUUID");
					if (ownerUUID.equals(entity.getUUID().toString())) {
						world.setBlock(lightPos, Blocks.AIR.defaultBlockState(), 3);
					}
				}
			}
		}
	}
}
