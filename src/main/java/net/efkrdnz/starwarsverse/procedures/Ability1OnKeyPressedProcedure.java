package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;

public class Ability1OnKeyPressedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		ForceKeybindWithIndexProcedure.execute(world, entity, 1);
	}
}
