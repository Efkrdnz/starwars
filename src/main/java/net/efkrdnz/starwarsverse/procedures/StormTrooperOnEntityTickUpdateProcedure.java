package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

public class StormTrooperOnEntityTickUpdateProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity trg = null;
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
			trg = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((trg.getX()), (trg.getY() + trg.getBbHeight() * (1 / 2)), (trg.getZ())));
		}
	}
}
