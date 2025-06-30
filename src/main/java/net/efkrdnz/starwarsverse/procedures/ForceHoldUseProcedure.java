package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.ForceThrowSystem;
import net.efkrdnz.starwarsverse.ForcePowers;

import javax.annotation.Nullable;

import java.util.List;

@EventBusSubscriber
public class ForceHoldUseProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null || !(world instanceof Level level)) {
			return;
		}
		// Only execute on server side for multiplayer compatibility
		if (level.isClientSide()) {
			return;
		}
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		// Only run if player is using telekinesis
		if (!vars.is_using_telekinesis) {
			return;
		}
		// Validate target UUID
		String targetUUID = vars.telekinesis_target_uuid;
		if (targetUUID == null || targetUUID.isEmpty()) {
			stopTelekinesis(world, entity);
			return;
		}
		// Check force power requirements
		ForcePowers.ForcePowerDefinition power = ForcePowers.getPower("force_hold");
		if (power == null) {
			stopTelekinesis(world, entity);
			return;
		}
		// Check if player has enough force power (drain over time)
		if (world.dayTime() % 20 == 0) { // Check every second
			int cost = power.getForceCost();
			if (vars.force_power < cost) {
				stopTelekinesis(world, entity);
				return;
			}
			// Drain force power over time
			vars.force_power -= 1;
			vars.syncPlayerVariables(entity);
		}
		// Play force sound effect periodically
		if (world.dayTime() % 15 == 0) {
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_charge")), SoundSource.NEUTRAL, 0.3f, 1.3f);
			}
		}
		// Calculate target position
		Vec3 playerEyePos = entity.getEyePosition(1f);
		Vec3 lookDirection = entity.getViewVector(1f);
		double distance = vars.telekinesis_distance;
		// Clamp distance to safe limits during runtime
		distance = Math.max(2.0, Math.min(20.0, distance));
		if (distance != vars.telekinesis_distance) {
			vars.telekinesis_distance = distance;
			vars.syncPlayerVariables(entity);
		}
		Vec3 targetPosition = playerEyePos.add(lookDirection.scale(distance));
		// Ensure target position is safe (not in blocks)
		targetPosition = findSafePosition(world, targetPosition);
		// Find and update target entity
		Entity targetEntity = findTargetEntity(world, targetUUID, entity.position());
		if (targetEntity == null) {
			stopTelekinesis(world, entity);
			return;
		}
		// Check if target is still alive and valid
		if (!targetEntity.isAlive() || targetEntity.isRemoved()) {
			stopTelekinesis(world, entity);
			return;
		}
		// Check distance limits (prevent infinite range)
		double currentDistance = entity.position().distanceTo(targetEntity.position());
		if (currentDistance > 25.0) { // Maximum drag distance
			stopTelekinesis(world, entity);
			return;
		}
		// Move entity smoothly to target position
		moveEntityToPosition(targetEntity, targetPosition);
		// Track mouse movement for throwing
		ForceThrowSystem.trackMouseMovement(world, entity);
	}

	/**
	 * Find a safe position for the entity (not inside blocks)
	 */
	private static Vec3 findSafePosition(LevelAccessor world, Vec3 originalPos) {
		BlockPos blockPos = BlockPos.containing(originalPos);
		// Check if original position is safe
		if (isPositionSafe(world, blockPos)) {
			return originalPos;
		}
		// Try positions above the original
		for (int i = 1; i <= 4; i++) {
			BlockPos checkPos = blockPos.above(i);
			if (isPositionSafe(world, checkPos)) {
				return Vec3.atCenterOf(checkPos);
			}
		}
		// Try positions around the original
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = 0; y <= 2; y++) {
					BlockPos checkPos = blockPos.offset(x, y, z);
					if (isPositionSafe(world, checkPos)) {
						return Vec3.atCenterOf(checkPos);
					}
				}
			}
		}
		// Return original position if no safe position found
		return originalPos;
	}

	/**
	 * Check if a position is safe for an entity (air blocks for head and feet)
	 */
	private static boolean isPositionSafe(LevelAccessor world, BlockPos pos) {
		BlockState blockAtFeet = world.getBlockState(pos);
		BlockState blockAtHead = world.getBlockState(pos.above());
		return blockAtFeet.isAir() && blockAtHead.isAir();
	}

	/**
	 * Find target entity by UUID efficiently
	 */
	private static Entity findTargetEntity(LevelAccessor world, String targetUUID, Vec3 searchCenter) {
		if (!(world instanceof ServerLevel serverLevel)) {
			return null;
		}
		try {
			// Try to get entity directly by UUID (most efficient)
			Entity entity = serverLevel.getEntity(java.util.UUID.fromString(targetUUID));
			if (entity != null && entity.isAlive()) {
				return entity;
			}
		} catch (Exception e) {
			// UUID parsing failed or entity not found
		}
		// Fallback: search in area (less efficient but more reliable)
		List<Entity> entities = world.getEntitiesOfClass(Entity.class, new AABB(searchCenter, searchCenter).inflate(30), e -> e.isAlive() && targetUUID.equals(e.getStringUUID()));
		return entities.isEmpty() ? null : entities.get(0);
	}

	/**
	 * Smoothly move entity to target position with proper velocity handling
	 */
	private static void moveEntityToPosition(Entity entity, Vec3 targetPosition) {
		Vec3 currentPos = entity.position();
		Vec3 direction = targetPosition.subtract(currentPos);
		double distance = direction.length();
		if (distance > 0.1) {
			// Calculate smooth movement
			double moveSpeed = Math.min(distance * 0.4, 2.0); // Max 2 blocks per tick
			Vec3 moveVector = direction.normalize().scale(moveSpeed);
			Vec3 newPos = currentPos.add(moveVector);
			// Set entity velocity to zero (prevents physics interference)
			entity.setDeltaMovement(Vec3.ZERO);
			entity.fallDistance = 0;
			entity.hurtMarked = true; // Force velocity sync
			// Teleport entity to new position
			entity.teleportTo(newPos.x, newPos.y, newPos.z);
			// Handle server players specifically for multiplayer
			if (entity instanceof ServerPlayer serverPlayer) {
				serverPlayer.connection.teleport(newPos.x, newPos.y, newPos.z, entity.getYRot(), entity.getXRot());
			}
			// Ensure entity stays still
			entity.setDeltaMovement(Vec3.ZERO);
		} else {
			// Entity is close enough, just keep it still
			entity.setDeltaMovement(Vec3.ZERO);
			entity.hurtMarked = true;
		}
	}

	/**
	 * Stop telekinesis and clean up
	 */
	private static void stopTelekinesis(LevelAccessor world, Entity entity) {
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		// Release the target entity
		String targetUUID = vars.telekinesis_target_uuid;
		if (targetUUID != null && !targetUUID.isEmpty()) {
			Entity targetEntity = findTargetEntity(world, targetUUID, entity.position());
			if (targetEntity != null) {
				// Restore normal physics
				targetEntity.hurtMarked = true;
			}
		}
		// Clear telekinesis variables
		vars.is_using_telekinesis = false;
		vars.telekinesis_target_uuid = "";
		vars.telekinesis_distance = 0;
		vars.syncPlayerVariables(entity);
		// Call the release procedure
		Ability1OnKeyReleasedProcedure.execute(world, entity);
	}
}
