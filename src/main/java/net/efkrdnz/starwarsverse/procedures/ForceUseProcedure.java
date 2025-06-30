package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.ForcePowers;

import dev.kosmx.playerAnim.api.AnimUtils;

public class ForceUseProcedure {
	public static void execute(LevelAccessor world, Entity entity, Entity force_entity, double force_charging, String force_name) {
		if (entity == null || force_entity == null || force_name == null)
			return;
		String force_id = "";
		Entity force_user = null;
		double force_charge = 0;
		force_user = force_entity;
		force_id = force_name;
		force_charge = force_charging;
		{
			StarwarsverseModVariables.PlayerVariables _vars = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.is_using_force = false;
			_vars.syncPlayerVariables(force_user);
		}
		ForcePowers.ForcePowerDefinition power = ForcePowers.getPower(force_id);
		if (power == null) {
			return;
		}
		int cost = power.getForceCost();
		int requiredLevel = power.getMinLevel();
		String name = power.getName();
		int maxcharge = power.getMaxChargeTime();
		if ((force_id).equals("force_push")) {
			if (force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power >= cost && force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level >= requiredLevel) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(force_user.getX(), force_user.getY(), force_user.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_push")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound((force_user.getX()), (force_user.getY()), (force_user.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_push")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				{
					StarwarsverseModVariables.PlayerVariables _vars = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.is_force_pushing = true;
					_vars.syncPlayerVariables(force_user);
				}
				if (!force_user.isShiftKeyDown()) {
					ForcepushuseProcedure.execute(world, force_user, force_charge, cost, maxcharge);
					{
						Entity _ent = force_user;
						_ent.setYRot(force_user.getYRot());
						_ent.setXRot(force_user.getXRot());
						_ent.setYBodyRot(_ent.getYRot());
						_ent.setYHeadRot(_ent.getYRot());
						_ent.yRotO = _ent.getYRot();
						_ent.xRotO = _ent.getXRot();
						if (_ent instanceof LivingEntity _entity) {
							_entity.yBodyRotO = _entity.getYRot();
							_entity.yHeadRotO = _entity.getYRot();
						}
					}
					AnimUtils.disableFirstPersonAnim = false;
					if (world.isClientSide()) {
						SetupAnimationsProcedure.setAnimationClientside((Player) force_user, "force_push", false);
					}
					if (!world.isClientSide()) {
						if (force_user instanceof Player)
							PacketDistributor.sendToPlayersInDimension((ServerLevel) force_user.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("force_push", force_user.getId(), false));
					}
				} else {
					ForcePushAreaUseProcedure.execute(world, entity, force_user, force_charge, cost, maxcharge);
				}
			}
		} else if ((force_id).equals("force_pull")) {
			if (force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power >= cost && force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level >= requiredLevel) {
				{
					StarwarsverseModVariables.PlayerVariables _vars = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.is_force_pulling = true;
					_vars.syncPlayerVariables(force_user);
				}
				{
					StarwarsverseModVariables.PlayerVariables _vars = force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.force_pull_animation_progress = 0;
					_vars.syncPlayerVariables(force_user);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(force_user.getX(), force_user.getY(), force_user.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_pull")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound((force_user.getX()), (force_user.getY()), (force_user.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_pull")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				if (!force_user.isShiftKeyDown()) {
					ForcepulluseProcedure.execute(world, force_user, force_charge, cost, maxcharge);
					{
						Entity _ent = force_user;
						_ent.setYRot(force_user.getYRot());
						_ent.setXRot(force_user.getXRot());
						_ent.setYBodyRot(_ent.getYRot());
						_ent.setYHeadRot(_ent.getYRot());
						_ent.yRotO = _ent.getYRot();
						_ent.xRotO = _ent.getXRot();
						if (_ent instanceof LivingEntity _entity) {
							_entity.yBodyRotO = _entity.getYRot();
							_entity.yHeadRotO = _entity.getYRot();
						}
					}
					AnimUtils.disableFirstPersonAnim = false;
					if (world.isClientSide()) {
						SetupAnimationsProcedure.setAnimationClientside((Player) force_user, "force_pull", false);
					}
					if (!world.isClientSide()) {
						if (force_user instanceof Player)
							PacketDistributor.sendToPlayersInDimension((ServerLevel) force_user.level(), new SetupAnimationsProcedure.StarwarsverseModAnimationMessage("force_pull", force_user.getId(), false));
					}
				} else {
					ForcepulluseSneakProcedure.execute(world, force_user, force_charge, cost, maxcharge);
				}
			}
		} else if ((force_id).equals("force_sense")) {
			if (force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_power >= cost && force_user.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_level >= requiredLevel) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(force_user.getX(), force_user.getY(), force_user.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_sense")), SoundSource.NEUTRAL, 1, 1);
					} else {
						_level.playLocalSound((force_user.getX()), (force_user.getY()), (force_user.getZ()), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_sense")), SoundSource.NEUTRAL, 1, 1, false);
					}
				}
				ForceSenseUseProcedure.execute(force_user, force_charge, cost, maxcharge);
			}
		}
	}
}
