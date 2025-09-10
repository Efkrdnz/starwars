package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;
import net.efkrdnz.starwarsverse.ForcePowers;

import javax.annotation.Nullable;

@EventBusSubscriber
public class ForceChargingProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity());
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		execute(null, world, x, y, z, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String current_used_power = "";
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).is_using_force) {
			if (world.dayTime() % 5 == 0) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_charge")), SoundSource.NEUTRAL, (float) 0.5, 1);
					} else {
						_level.playLocalSound(x, y, z, BuiltInRegistries.SOUND_EVENT.get(ResourceLocation.parse("starwarsverse:force_charge")), SoundSource.NEUTRAL, (float) 0.5, 1, false);
					}
				}
			}
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(StarwarsverseModMobEffects.SCREEN_SHAKE, 5, 8, false, false));
			current_used_power = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).current_charging_power;
			ForcePowers.ForcePowerDefinition power = ForcePowers.getPower(current_used_power);
			if (power == null) {
				return;
			}
			int maxcharge = power.getMaxChargeTime();
			String forcename = power.getName();
			if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).charge_timer < maxcharge) {
				{
					StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.charge_timer = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).charge_timer + 1;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
	}
}
