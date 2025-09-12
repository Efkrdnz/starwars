package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;

public class LaserProjectileHitsLivingEntityProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity immediatesourceentity, Entity sourceentity) {
		if (entity == null || immediatesourceentity == null || sourceentity == null)
			return;
		entity.invulnerableTime = 0;
		if (!immediatesourceentity.level().isClientSide())
			immediatesourceentity.discard();
		entity.hurt(new DamageSource(world.holderOrThrow(DamageTypes.MOB_PROJECTILE), immediatesourceentity, sourceentity), 4);
	}
}
