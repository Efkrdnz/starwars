package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import dev.kosmx.playerAnim.api.AnimUtils;

public class LightsaberSwingWithTimingProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		long currentTime = System.currentTimeMillis();
		// reset pattern if more than 2 seconds have passed since last attack
		if (currentTime - vars.lightsaber_last_attack_time > 2000) {
			vars.lightsaber_attack_pattern = 0;
		}
		// update last attack time
		vars.lightsaber_last_attack_time = currentTime;
		int currentPattern = (int) vars.lightsaber_attack_pattern;
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
