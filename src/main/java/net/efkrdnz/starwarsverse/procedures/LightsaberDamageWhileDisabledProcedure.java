package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.component.DataComponents;

import javax.annotation.Nullable;

@EventBusSubscriber
public class LightsaberDamageWhileDisabledProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingIncomingDamageEvent event) {
		if (event.getEntity() != null && event.getSource().getEntity() != null) {
			execute(event, event.getSource(), event.getSource().getEntity());
		}
	}

	public static void execute(DamageSource damagesource, Entity sourceEntity) {
		execute(null, damagesource, sourceEntity);
	}

	private static void execute(@Nullable Event event, DamageSource damagesource, Entity sourceEntity) {
		if (damagesource == null || sourceEntity == null)
			return;
		// check if the attacker is holding a lightsaber
		if (!(sourceEntity instanceof LivingEntity attacker))
			return;
		ItemStack weaponItem = attacker.getMainHandItem();
		// check if weapon is a lightsaber with correct tag
		if (!weaponItem.is(ItemTags.create(ResourceLocation.parse("minecraft:lightsaber"))))
			return;
		// check if lightsaber is disabled (not enabled)
		boolean isEnabled = weaponItem.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("enabled");
		if (isEnabled)
			return; // lightsaber is enabled, use normal damage
		// check if this is a player attack or entity attack
		if (damagesource.is(DamageTypes.PLAYER_ATTACK) || damagesource.is(DamageTypes.MOB_ATTACK)) {
			if (event instanceof LivingIncomingDamageEvent damageEvent) {
				// set damage to 1 when lightsaber is disabled
				damageEvent.setAmount(1.0f);
			}
		}
	}
}
