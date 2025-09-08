package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import dev.kosmx.playerAnim.api.AnimUtils;

public class LightsaberSwingProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		int currentPattern = (int) entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).lightsaber_attack_pattern;
		// cycle through attack patterns
		if (currentPattern == 0) {
			setAttackPattern(entity, 1, "attack1", world);
		} else if (currentPattern == 1) {
			setAttackPattern(entity, 2, "attack2", world);
		} else if (currentPattern == 2) {
			setAttackPattern(entity, 3, "attack3", world);
		} else {
			// reset to first attack after third
			setAttackPattern(entity, 1, "attack1", world);
		}
		if (world instanceof Level _level) {
			if (!world.isClientSide()) {
				_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:lightsaber_swing")), SoundSource.PLAYERS, 0.2f, 1);
			} else {
				_level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:lightsaber_swing")), SoundSource.PLAYERS, 0.1f, 1, false);
			}
		}
	}

	private static void setAttackPattern(Entity entity, int pattern, String animationName, LevelAccessor world) {
		// update attack pattern
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		vars.lightsaber_attack_pattern = pattern;
		vars.syncPlayerVariables(entity);
		// disable first person animation override
		AnimUtils.disableFirstPersonAnim = false;
		// play animation on client side
		if (world.isClientSide()) {
			SetupAnimationsProcedure.setAnimationClientside((Player) entity, animationName, true);
		}
		// sync animation to other players on server side
		if (!world.isClientSide() && entity instanceof Player) {
			PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage(animationName, entity.getId(), true));
		}
	}
}
