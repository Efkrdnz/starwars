package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.init.StarwarsverseModMobEffects;

public class ClientGlowReturnProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;

		// Check if entity has the ClientGlowEffect
		if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(StarwarsverseModMobEffects.GLIENT_GLOW_EFFECT)) {

			// Get the current client player (viewer)
			Player viewer = Minecraft.getInstance().player;
			if (viewer == null)
				return false;

			// Check if this viewer is the one who marked the entity
			if (entity.getPersistentData().hasUUID("marked_by")) {
				return entity.getPersistentData().getUUID("marked_by").equals(viewer.getUUID());
			}

			// If no "marked_by" data, default to true (for backward compatibility)
			return true;
		}

		return false;
	}
}
