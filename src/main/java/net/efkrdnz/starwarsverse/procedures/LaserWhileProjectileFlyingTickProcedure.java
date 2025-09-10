package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;

public class LaserWhileProjectileFlyingTickProcedure {
	public static void execute(Entity immediatesourceentity) {
		if (immediatesourceentity == null)
			return;
		immediatesourceentity.setNoGravity(true);
	}
}
