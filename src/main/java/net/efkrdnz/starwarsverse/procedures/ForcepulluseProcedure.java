package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import java.util.List;
import java.util.Comparator;

public class ForcepulluseProcedure {
	public static void execute(LevelAccessor world, Entity target, double charge, double cost, double max_charge) {
		if (target == null)
			return;
		final Entity force_user = target;
		final double power_cost = cost;
		final double current_charge = charge;
		final double maxcharge = max_charge;
		if (force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power >= power_cost || !(force_user instanceof Player)) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.force_power = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power - power_cost;
				_vars.syncPlayerVariables(force_user);
			}
			// Calculate charge ratio for scaling effects
			final double charge_ratio = current_charge / maxcharge;
			final double force_level = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level;
			// Max range doubled from original: was 6 + (10 * charge_ratio) + (force_level * 0.4)
			// Now: 12 + (20 * charge_ratio) + (force_level * 0.8)
			final double max_range = 12 + (20 * charge_ratio) + (force_level * 0.8);
			// Detection radius around the player (creates a large search area)
			double detection_radius = max_range + 2;
			// Use player position as center for detection
			final Vec3 _center = new Vec3(force_user.getX(), force_user.getY(), force_user.getZ());
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(detection_radius), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).filter(entity -> {
				// Filter entities to only those in front of the player and within range
				if (entity == force_user || !(entity instanceof LivingEntity))
					return false;
				double distance = entity.distanceTo(force_user);
				if (distance > max_range)
					return false;
				// Check if entity is roughly in front of the player (within 60 degree cone)
				Vec3 toEntity = new Vec3(entity.getX() - force_user.getX(), entity.getY() - force_user.getY(), entity.getZ() - force_user.getZ()).normalize();
				double dotProduct = force_user.getLookAngle().dot(toEntity);
				return dotProduct > 0.5; // 60 degree cone (cos(60°) = 0.5)
			}).toList();
			for (Entity entityiterator : _entfound) {
				// Deal damage
				entityiterator.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("starwarsverse:force_push"))), force_user), (float) (4 * charge_ratio + force_level / 25));
				// Calculate pull direction (from entity TO force user)
				Vec3 pullDirection = new Vec3(force_user.getX() - entityiterator.getX(), force_user.getY() - entityiterator.getY() + 0.5, // Slight upward component
						force_user.getZ() - entityiterator.getZ()).normalize();
				// Calculate pull strength based on charge and force level
				double pull_strength = 1.5 + (2.5 * charge_ratio) + (force_level / 20);
				// Moderate vertical component - keep it functional but not excessive
				double vertical_multiplier = 0.65; // Reduce Y movement to 65% of normal
				double calculated_y = pullDirection.y * pull_strength * vertical_multiplier;
				// Apply the pull movement with moderated vertical velocity
				entityiterator.setDeltaMovement(new Vec3(pullDirection.x * pull_strength, Math.max(0.3, Math.abs(calculated_y) > 1.2 ? Math.signum(calculated_y) * 1.2 : calculated_y), // Cap at ±1.2 but keep direction
						pullDirection.z * pull_strength));
			}
		}
	}
}