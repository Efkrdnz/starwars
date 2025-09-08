package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

public class TempBossOnEntityTickUpdateProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double distance = 0;
		Entity ent = null;
		if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
			ent = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			distance = Math.sqrt(Math.pow(entity.getX() - ent.getX(), 2) + Math.pow(entity.getY() - ent.getY(), 2) + Math.pow(entity.getZ() - ent.getZ(), 2));
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((ent.getX()), (ent.getY() + (entity.getBbHeight() * 2) / 3), (ent.getZ())));
			if (entity instanceof Mob _entity)
				_entity.getNavigation().moveTo((ent.getX()), (ent.getY()), (ent.getZ()), 1);
		}
	}
}
