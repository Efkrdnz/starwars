package net.efkrdnz.starwarsverse;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class ForcePushPullHandler {
	/**
	 * Execute force push on the held entity
	 */
	public static void executePush(LevelAccessor world, Entity player) {
		System.out.println("ForcePushPullHandler.executePush called");
		if (player == null || !(world instanceof Level level) || level.isClientSide()) {
			System.out.println("Push failed: invalid world or client side");
			return;
		}
		StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (!vars.is_using_telekinesis) {
			System.out.println("Push failed: not using telekinesis");
			return;
		}
		Entity targetEntity = findTargetEntity(world, vars.telekinesis_target_uuid);
		if (targetEntity == null) {
			System.out.println("Push failed: no target entity found");
			return;
		}
		System.out.println("Pushing entity: " + targetEntity.getName().getString());
		// Calculate push direction (away from player)
		Vec3 playerPos = player.position().add(0, player.getBbHeight() * 0.5, 0);
		Vec3 targetPos = targetEntity.position().add(0, targetEntity.getBbHeight() * 0.5, 0);
		Vec3 pushDirection = targetPos.subtract(playerPos).normalize();
		// Apply push force
		double pushStrength = calculatePushStrength(targetEntity);
		Vec3 pushVelocity = pushDirection.scale(pushStrength);
		// Add some upward component to make it feel more dramatic
		pushVelocity = pushVelocity.add(0, 0.2, 0);
		System.out.println("Push velocity: " + pushVelocity);
		// Apply the velocity
		targetEntity.setDeltaMovement(targetEntity.getDeltaMovement().add(pushVelocity));
		targetEntity.hurtMarked = true;
		targetEntity.fallDistance = 0; // Prevent fall damage
		// Play push sound effect
		playForceSound(level, targetEntity.position(), 1.2f);
		System.out.println("Push executed successfully");
		// Schedule telekinesis resumption after 10 ticks (0.5 seconds)
		vars.telekinesis_resume_timer = 10;
		vars.syncPlayerVariables(player);
	}

	/**
	 * Execute force pull on the held entity
	 */
	public static void executePull(LevelAccessor world, Entity player) {
		System.out.println("ForcePushPullHandler.executePull called");
		if (player == null || !(world instanceof Level level) || level.isClientSide()) {
			System.out.println("Pull failed: invalid world or client side");
			return;
		}
		StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		if (!vars.is_using_telekinesis) {
			System.out.println("Pull failed: not using telekinesis");
			return;
		}
		Entity targetEntity = findTargetEntity(world, vars.telekinesis_target_uuid);
		if (targetEntity == null) {
			System.out.println("Pull failed: no target entity found");
			return;
		}
		System.out.println("Pulling entity: " + targetEntity.getName().getString());
		// Calculate pull direction (toward player)
		Vec3 playerPos = player.position().add(0, player.getBbHeight() * 0.5, 0);
		Vec3 targetPos = targetEntity.position().add(0, targetEntity.getBbHeight() * 0.5, 0);
		Vec3 pullDirection = playerPos.subtract(targetPos).normalize();
		// Apply pull force
		double pullStrength = calculatePullStrength(targetEntity);
		Vec3 pullVelocity = pullDirection.scale(pullStrength);
		System.out.println("Pull velocity: " + pullVelocity);
		// Apply the velocity
		targetEntity.setDeltaMovement(targetEntity.getDeltaMovement().add(pullVelocity));
		targetEntity.hurtMarked = true;
		targetEntity.fallDistance = 0; // Prevent fall damage
		// Play pull sound effect
		playForceSound(level, targetEntity.position(), 0.8f);
		System.out.println("Pull executed successfully");
		// Schedule telekinesis resumption after 10 ticks (0.5 seconds)
		vars.telekinesis_resume_timer = 10;
		vars.syncPlayerVariables(player);
	}

	/**
	 * Calculate push strength based on entity properties
	 */
	private static double calculatePushStrength(Entity entity) {
		if (entity instanceof LivingEntity living) {
			double health = living.getMaxHealth();
			double size = living.getBbWidth() * living.getBbHeight();
			// Smaller/lighter entities get pushed further
			if (size < 1.0 && health < 10)
				return 1.5; // Small entities
			if (size < 3.0 && health < 50)
				return 1.0; // Medium entities
			if (size < 6.0 && health < 100)
				return 0.7; // Large entities
			return 0.4; // Very large entities
		}
		return 1.0; // Default strength
	}

	/**
	 * Calculate pull strength based on entity properties
	 */
	private static double calculatePullStrength(Entity entity) {
		// Pull is generally stronger than push for control
		return calculatePushStrength(entity) * 1.3;
	}

	/**
	 * Find target entity by UUID
	 */
	private static Entity findTargetEntity(LevelAccessor world, String targetUUID) {
		if (!(world instanceof ServerLevel serverLevel) || targetUUID == null || targetUUID.isEmpty()) {
			return null;
		}
		try {
			Entity entity = serverLevel.getEntity(java.util.UUID.fromString(targetUUID));
			if (entity != null && entity.isAlive()) {
				return entity;
			}
		} catch (Exception e) {
			// UUID parsing failed
		}
		return null;
	}

	/**
	 * Play force sound effect
	 */
	private static void playForceSound(Level level, Vec3 position, float pitch) {
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.playSound(null, BlockPos.containing(position), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_charge")), SoundSource.NEUTRAL, 0.8f, pitch);
		}
	}
}
