package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

@EventBusSubscriber
public class ForceReplenishProcedure {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Post event) {
		execute(event, event.getEntity());
	}

	public static void execute(Event event, Entity entity) {
		if (entity == null || !(entity instanceof Player player))
			return;
		StarwarsverseModVariables.PlayerVariables vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
		// Only check every 20 ticks (1 second)
		if (player.tickCount % 20 != 0)
			return;
		double maxForcePower = 100 + (vars.force_level * 20);
		if (vars.force_power >= maxForcePower)
			return;
		// Base regeneration rate
		double regenRate = 1.0 + (vars.force_level * 0.5);
		// Modify regen based on conditions
		if (player.isCrouching()) {
			regenRate *= 2.0; // 2x regen when meditating (crouching)
		}
		if (player.isSprinting()) {
			regenRate *= 0.5; // Slower regen when sprinting
		}
		// Apply regeneration
		vars.force_power = Math.min(vars.force_power + regenRate, maxForcePower);
		vars.syncPlayerVariables(entity);
	}
}
