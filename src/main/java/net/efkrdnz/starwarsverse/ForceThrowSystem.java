package net.efkrdnz.starwarsverse;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class ForceThrowSystem {
	/**
	 * Track mouse movement during Force Hold for throwing
	 */
	public static void trackMouseMovement(LevelAccessor world, Entity entity) {
		if (entity == null || !(world instanceof Level level) || level.isClientSide()) {
			return;
		}
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (!vars.is_using_telekinesis) {
			return;
		}
		Vec3 currentLookDirection = entity.getViewVector(1f);
		if (vars.previous_look_direction_x == 0 && vars.previous_look_direction_y == 0 && vars.previous_look_direction_z == 0) {
			vars.previous_look_direction_x = currentLookDirection.x;
			vars.previous_look_direction_y = currentLookDirection.y;
			vars.previous_look_direction_z = currentLookDirection.z;
			vars.syncPlayerVariables(entity);
			return;
		}
		Vec3 previousLookDirection = new Vec3(vars.previous_look_direction_x, vars.previous_look_direction_y, vars.previous_look_direction_z);
		Vec3 lookDelta = currentLookDirection.subtract(previousLookDirection);
		vars.accumulated_throw_velocity_x += lookDelta.x * 15;
		vars.accumulated_throw_velocity_y += lookDelta.y * 15;
		vars.accumulated_throw_velocity_z += lookDelta.z * 15;
		vars.accumulated_throw_velocity_x *= 0.85;
		vars.accumulated_throw_velocity_y *= 0.85;
		vars.accumulated_throw_velocity_z *= 0.85;
		double maxThrowSpeed = 3.0;
		Vec3 throwVelocity = new Vec3(vars.accumulated_throw_velocity_x, vars.accumulated_throw_velocity_y, vars.accumulated_throw_velocity_z);
		if (throwVelocity.length() > maxThrowSpeed) {
			throwVelocity = throwVelocity.normalize().scale(maxThrowSpeed);
			vars.accumulated_throw_velocity_x = throwVelocity.x;
			vars.accumulated_throw_velocity_y = throwVelocity.y;
			vars.accumulated_throw_velocity_z = throwVelocity.z;
		}
		vars.previous_look_direction_x = currentLookDirection.x;
		vars.previous_look_direction_y = currentLookDirection.y;
		vars.previous_look_direction_z = currentLookDirection.z;
		vars.syncPlayerVariables(entity);
	}

	/**
	 * execute throw when Force Hold is released
	 */
	public static void executeThrow(LevelAccessor world, Entity entity) {
		if (entity == null || !(world instanceof Level level) || level.isClientSide()) {
			return;
		}
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		Vec3 throwVelocity = new Vec3(vars.accumulated_throw_velocity_x, vars.accumulated_throw_velocity_y, vars.accumulated_throw_velocity_z);
		double throwSpeed = throwVelocity.length();
		if (throwSpeed < 0.5) {
			gentlyReleaseEntity(world, entity);
			return;
		}
		String targetUUID = vars.telekinesis_target_uuid;
		if (targetUUID == null || targetUUID.isEmpty()) {
			return;
		}
		Entity targetEntity = findTargetEntity(world, targetUUID, entity.position());
		if (targetEntity == null) {
			return;
		}
		applyThrowVelocity(targetEntity, throwVelocity);
		clearThrowTracking(entity);
	}

	/**
	 * release entity without throwing
	 */
	private static void gentlyReleaseEntity(LevelAccessor world, Entity entity) {
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		String targetUUID = vars.telekinesis_target_uuid;
		if (targetUUID != null && !targetUUID.isEmpty()) {
			Entity targetEntity = findTargetEntity(world, targetUUID, entity.position());
			if (targetEntity != null) {
				// Just restore normal physics without adding velocity
				targetEntity.setDeltaMovement(Vec3.ZERO);
				targetEntity.hurtMarked = true;
			}
		}
		clearThrowTracking(entity);
	}

	/**
	 * Apply calculated throw velocity to target entity
	 */
	private static void applyThrowVelocity(Entity targetEntity, Vec3 throwVelocity) {
		// Scale throw velocity based on entity size/weight
		double entityWeight = getEntityWeight(targetEntity);
		Vec3 scaledVelocity = throwVelocity.scale(1.0 / entityWeight);
		if (scaledVelocity.y < 0.2 && scaledVelocity.horizontalDistance() > 0.5) {
			scaledVelocity = new Vec3(scaledVelocity.x, Math.max(scaledVelocity.y, 0.3), scaledVelocity.z);
		}
		targetEntity.setDeltaMovement(scaledVelocity);
		targetEntity.hurtMarked = true;
		targetEntity.fallDistance = 0;
	}

	/**
	 * Calculate entity "weight" for throw scaling
	 */
	private static double getEntityWeight(Entity entity) {
		if (entity instanceof LivingEntity living) {
			double health = living.getMaxHealth();
			double size = living.getBbWidth() * living.getBbHeight();
			if (size < 1.0 && health < 10)
				return 1.0;
			if (size < 3.0 && health < 50)
				return 1.5;
			if (size < 6.0 && health < 100)
				return 2.0;
			return 4.0;
		}
		return 1.5;
	}

	/**
	 * Find target entity by UUID
	 */
	private static Entity findTargetEntity(LevelAccessor world, String targetUUID, Vec3 searchCenter) {
		if (!(world instanceof ServerLevel serverLevel)) {
			return null;
		}
		try {
			Entity entity = serverLevel.getEntity(java.util.UUID.fromString(targetUUID));
			if (entity != null && entity.isAlive()) {
				return entity;
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Clear all throw tracking variables
	 */
	private static void clearThrowTracking(Entity entity) {
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		vars.accumulated_throw_velocity_x = 0;
		vars.accumulated_throw_velocity_y = 0;
		vars.accumulated_throw_velocity_z = 0;
		vars.previous_look_direction_x = 0;
		vars.previous_look_direction_y = 0;
		vars.previous_look_direction_z = 0;
		vars.syncPlayerVariables(entity);
	}
}
