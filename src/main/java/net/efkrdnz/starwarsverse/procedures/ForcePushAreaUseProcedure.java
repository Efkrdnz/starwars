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

public class ForcePushAreaUseProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity target, double charge, double cost, double max_charge) {
		if (entity == null || target == null)
			return;
		Entity force_user = null;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		double power_cost = 0;
		double maxcharge = 0;
		double current_charge = 0;
		force_user = target;
		power_cost = cost;
		current_charge = charge;
		maxcharge = max_charge;
		if (force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power >= power_cost || !(force_user instanceof Player)) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.force_power = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power - power_cost;
				_vars.syncPlayerVariables(force_user);
			}
			dx = force_user.getLookAngle().x;
			dy = force_user.getLookAngle().y;
			dz = force_user.getLookAngle().z;
			dx = 1.5 * force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level;
			{
				final Vec3 _center = new Vec3((force_user.getX()), (force_user.getY()), (force_user.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((20 * (current_charge / maxcharge)) / 2d), e -> true).stream()
						.sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (!(entityiterator == force_user) && entityiterator instanceof LivingEntity) {
						if (!entityiterator.getPersistentData().getBoolean("blocking")) {
							entityiterator.hurt(new DamageSource(world.holderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse("starwarsverse:force_push"))), force_user),
									(float) (3 * (current_charge / maxcharge) + force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level / 5));
							entityiterator.setDeltaMovement(new Vec3(
									((entityiterator.getX() - force_user.getX()) * ((5 * (current_charge / maxcharge) * (force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level / 5))
											/ Math.sqrt(Math.pow(entityiterator.getX() - entity.getX(), 2) + Math.pow(entityiterator.getY() - entity.getY(), 2) + Math.pow(entityiterator.getZ() - entity.getZ(), 2)))),
									0.5, ((entityiterator.getZ() - force_user.getZ()) * ((5 * (current_charge / maxcharge) * (force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level / 5))
											/ Math.sqrt(Math.pow(entityiterator.getX() - entity.getX(), 2) + Math.pow(entityiterator.getY() - entity.getY(), 2) + Math.pow(entityiterator.getZ() - entity.getZ(), 2))))));
						}
					}
				}
			}
		}
	}
}
