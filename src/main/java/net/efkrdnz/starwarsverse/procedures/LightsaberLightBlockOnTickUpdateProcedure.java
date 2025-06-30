package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public class LightsaberLightBlockOnTickUpdateProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		if (world.isClientSide() || !(world instanceof Level level)) {
			return; // Only run on server side and when we have a Level instance
		}
		BlockPos pos = BlockPos.containing(x, y, z);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity == null) {
			return;
		}
		long placedTime = blockEntity.getPersistentData().getLong("placedTime");
		long currentTime = level.getGameTime();
		// Remove the block if it's been around for more than 10 ticks (0.5 seconds)
		// This gives enough time for the player movement to refresh it
		if (currentTime - placedTime > 10) {
			world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
		}
	}
}
