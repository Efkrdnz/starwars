package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;
import net.efkrdnz.starwarsverse.ForceThrowSystem;

public class Ability1OnKeyReleasedProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.is_using_force = false;
			_vars.syncPlayerVariables(entity);
		}
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).is_using_telekinesis) {
			ForceThrowSystem.executeThrow(world, entity);
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(StarwarsverseModMobEffects.SCREEN_SHAKE, 5, (int) entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).charge_timer, false, false));
		ForceUseProcedure.execute(world, entity, entity, entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).charge_timer, entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).current_charging_power);
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.current_charging_power = "";
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.charge_timer = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.telekinesis_distance = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.telekinesis_target_uuid = "";
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.force_scroll_action = "";
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.is_using_telekinesis = false;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.is_using_lightning = false;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.accumulated_throw_velocity_x = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.accumulated_throw_velocity_y = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.accumulated_throw_velocity_z = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.previous_look_direction_x = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.previous_look_direction_y = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.previous_look_direction_z = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.last_mouse_pitch = 0;
			_vars.syncPlayerVariables(entity);
		}
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.telekinesis_resume_timer = 0;
			_vars.syncPlayerVariables(entity);
		}
	}
}
