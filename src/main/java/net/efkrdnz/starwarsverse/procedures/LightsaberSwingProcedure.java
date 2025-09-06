package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import dev.kosmx.playerAnim.api.AnimUtils;

public class LightsaberSwingProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).lightsaber_attack_pattern == 0) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.lightsaber_attack_pattern = 1;
				_vars.syncPlayerVariables(entity);
			}
			AnimUtils.disableFirstPersonAnim = false;
			if (world.isClientSide()) {
				SetupAnimationsProcedure.setAnimationClientside((Player) entity, "attack1", true);
			}
			if (!world.isClientSide()) {
				if (entity instanceof Player)
					PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("attack1", entity.getId(), true));
			}
		}
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).lightsaber_attack_pattern == 1) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.lightsaber_attack_pattern = 2;
				_vars.syncPlayerVariables(entity);
			}
			AnimUtils.disableFirstPersonAnim = false;
			if (world.isClientSide()) {
				SetupAnimationsProcedure.setAnimationClientside((Player) entity, "attack2", true);
			}
			if (!world.isClientSide()) {
				if (entity instanceof Player)
					PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("attack2", entity.getId(), true));
			}
		}
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).lightsaber_attack_pattern == 2) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.lightsaber_attack_pattern = 3;
				_vars.syncPlayerVariables(entity);
			}
			AnimUtils.disableFirstPersonAnim = false;
			if (world.isClientSide()) {
				SetupAnimationsProcedure.setAnimationClientside((Player) entity, "attack3", true);
			}
			if (!world.isClientSide()) {
				if (entity instanceof Player)
					PacketDistributor.sendToPlayersInDimension((ServerLevel) entity.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("attack3", entity.getId(), true));
			}
		}
	}
}
