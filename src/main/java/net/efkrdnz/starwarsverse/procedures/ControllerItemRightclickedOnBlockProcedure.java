package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.init.StarwarsverseModBlocks;

public class ControllerItemRightclickedOnBlockProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity) {
		if (direction == null || entity == null)
			return;
		if (direction == Direction.UP) {
			if ((entity.getDirection()) == Direction.NORTH) {
				if (((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.CAVE_AIR)
						&& ((world.getBlockState(BlockPos.containing(x + 1, y + 1, z))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x + 1, y + 1, z))).getBlock() == Blocks.CAVE_AIR)) {
					world.setBlock(BlockPos.containing(x, y + 1, z), StarwarsverseModBlocks.CONTROLLER.get().defaultBlockState(), 3);
					world.setBlock(BlockPos.containing(x + 1, y + 1, z + 0), StarwarsverseModBlocks.CONTROLLER_R.get().defaultBlockState(), 3);
				}
			} else if ((entity.getDirection()) == Direction.SOUTH) {
				if (((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.CAVE_AIR)
						&& ((world.getBlockState(BlockPos.containing(x - 1, y + 1, z))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x - 1, y + 1, z))).getBlock() == Blocks.CAVE_AIR)) {
					world.setBlock(BlockPos.containing(x, y + 1, z), StarwarsverseModBlocks.CONTROLLER.get().defaultBlockState(), 3);
					world.setBlock(BlockPos.containing(x - 1, y + 1, z + 0), StarwarsverseModBlocks.CONTROLLER_R.get().defaultBlockState(), 3);
				}
			} else if ((entity.getDirection()) == Direction.WEST) {
				if (((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.CAVE_AIR)
						&& ((world.getBlockState(BlockPos.containing(x, y + 1, z + 1))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x, y + 1, z + 1))).getBlock() == Blocks.CAVE_AIR)) {
					world.setBlock(BlockPos.containing(x, y + 1, z), StarwarsverseModBlocks.CONTROLLER.get().defaultBlockState(), 3);
					world.setBlock(BlockPos.containing(x + 0, y + 1, z + 1), StarwarsverseModBlocks.CONTROLLER_R.get().defaultBlockState(), 3);
				}
			} else if ((entity.getDirection()) == Direction.EAST) {
				if (((world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x, y + 1, z))).getBlock() == Blocks.CAVE_AIR)
						&& ((world.getBlockState(BlockPos.containing(x, y + 1, z - 1))).getBlock() == Blocks.AIR || (world.getBlockState(BlockPos.containing(x, y + 1, z - 1))).getBlock() == Blocks.CAVE_AIR)) {
					world.setBlock(BlockPos.containing(x, y + 1, z), StarwarsverseModBlocks.CONTROLLER.get().defaultBlockState(), 3);
					world.setBlock(BlockPos.containing(x + 0, y + 1, z - 1), StarwarsverseModBlocks.CONTROLLER_R.get().defaultBlockState(), 3);
				}
			}
		}
	}
}
