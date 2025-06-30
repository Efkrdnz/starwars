package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

import javax.annotation.Nullable;

@EventBusSubscriber
public class XwingAircraftTickRotationProcedure {
	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		execute(event, event.getEntity());
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		Entity driver = null;
		double pitch_new = 0;
		double pitch = 0;
		double speed = 0;
		double yaw = 0;
		if (entity instanceof XwingAircraftEntity) {
			if (!((entity.getControllingPassenger()) == (null))) {
				driver = entity.getControllingPassenger();
			}
			if (driver == (null)) {
				return;
			}
			entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((1 * driver.getLookAngle().x), (1 * driver.getLookAngle().y), (1 * driver.getLookAngle().z)));
		}
	}
}
