package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.entity.AttackHitboxEntity;

public class AttackHitboxOnEntityTickUpdateProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if ((entity instanceof AttackHitboxEntity _datEntI ? _datEntI.getEntityData().get(AttackHitboxEntity.DATA_life) : 0) < (entity instanceof AttackHitboxEntity _datEntI ? _datEntI.getEntityData().get(AttackHitboxEntity.DATA_maxlife) : 0)) {
			if (entity instanceof AttackHitboxEntity _datEntSetI)
				_datEntSetI.getEntityData().set(AttackHitboxEntity.DATA_life, (int) ((entity instanceof AttackHitboxEntity _datEntI ? _datEntI.getEntityData().get(AttackHitboxEntity.DATA_maxlife) : 0) + 1));
		} else {
			if (!entity.level().isClientSide())
				entity.discard();
		}
	}
}
