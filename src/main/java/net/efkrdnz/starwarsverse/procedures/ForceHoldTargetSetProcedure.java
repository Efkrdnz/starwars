package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import java.util.List;

public class ForceHoldTargetSetProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null || !(world instanceof Level level)) {
			return;
		}
		// Only execute on server side for multiplayer compatibility
		if (level.isClientSide()) {
			return;
		}
		double maxDistance = 16.0; // Maximum telekinesis range
		// Get player's eye position and look direction
		Vec3 eyePos = entity.getEyePosition(1f);
		Vec3 lookVec = entity.getViewVector(1f);
		Vec3 endPos = eyePos.add(lookVec.scale(maxDistance));
		// Perform raycast to check for block obstructions
		ClipContext clipContext = new ClipContext(eyePos, endPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
		BlockHitResult blockHit = level.clip(clipContext);
		// Adjust max distance if blocked by terrain
		if (blockHit.getType() != HitResult.Type.MISS) {
			maxDistance = Math.min(maxDistance, eyePos.distanceTo(blockHit.getLocation()) - 0.5);
		}
		// Find target entity using a cone-based search
		LivingEntity targetEntity = null;
		double closestDistance = maxDistance;
		// Search for living entities in a reasonable area
		AABB searchArea = AABB.ofSize(eyePos, maxDistance * 2, maxDistance, maxDistance * 2);
		List<LivingEntity> candidates = world.getEntitiesOfClass(LivingEntity.class, searchArea, e -> e != entity && e.isAlive() && !isProtectedEntity(e));
		// Find the best target based on distance and look direction
		for (LivingEntity candidate : candidates) {
			double distance = eyePos.distanceTo(candidate.position());
			if (distance <= maxDistance) {
				// Check if entity is in the player's look direction (cone check)
				Vec3 toEntity = candidate.position().add(0, candidate.getBbHeight() * 0.5, 0).subtract(eyePos).normalize();
				double dotProduct = toEntity.dot(lookVec);
				// 45-degree cone (dot > 0.707) and closer than current target
				if (dotProduct > 0.707 && distance < closestDistance) {
					// Additional raycast to ensure clear line of sight
					ClipContext losCheck = new ClipContext(eyePos, candidate.position().add(0, candidate.getBbHeight() * 0.5, 0), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
					if (level.clip(losCheck).getType() == HitResult.Type.MISS) {
						targetEntity = candidate;
						closestDistance = distance;
					}
				}
			}
		}
		// Set telekinesis target if found
		if (targetEntity != null) {
			StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			vars.is_using_telekinesis = true;
			vars.telekinesis_target_uuid = targetEntity.getStringUUID();
			vars.telekinesis_distance = closestDistance;
			vars.syncPlayerVariables(entity);
			// Stop the target entity's movement immediately
			targetEntity.setDeltaMovement(Vec3.ZERO);
			targetEntity.hurtMarked = true; // Force velocity sync
		}
	}

	/**
	 * Check if an entity should be protected from Force Hold
	 */
	private static boolean isProtectedEntity(Entity entity) {
		// Protect other players in multiplayer
		if (entity instanceof Player) {
			return true;
		}
		// Protect bosses and special entities
		if (entity instanceof LivingEntity living) {
			// Protect entities with high health (likely bosses)
			if (living.getMaxHealth() > 100) {
				return true;
			}
			// Protect entities that are currently being ridden
			if (living.isVehicle()) {
				return true;
			}
		}
		return false;
	}
}
