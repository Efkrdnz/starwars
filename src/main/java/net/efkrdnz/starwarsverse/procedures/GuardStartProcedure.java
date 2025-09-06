package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber
public class GuardStartProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		if (entity.isShiftKeyDown()) {
			if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard < 60) {
				{
					StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.guard = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard + 1;
					_vars.syncPlayerVariables(entity);
				}
			}
		} else {
			if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard > 0) {
				{
					StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.guard = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard - 2;
					_vars.syncPlayerVariables(entity);
				}
			}
		}
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard < 0) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.guard = 0;
				_vars.syncPlayerVariables(entity);
			}
		}
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard > 60) {
			{
				StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
				_vars.guard = 60;
				_vars.syncPlayerVariables(entity);
			}
		}
	}
}
