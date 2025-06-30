package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class XwingAircraftOnEntityTickUpdateProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity driver = null;
		double pitch_new = 0;
		double pitch = 0;
		double yaw = 0;
		double speed = 0;
		if (!((entity.getControllingPassenger()) == (null))) {
			if (entity instanceof XwingAircraftEntity) {
				((XwingAircraftEntity) entity).setAnimation("idle_2");
			}
			driver = entity.getControllingPassenger();
		}
		if (driver == (null)) {
			if (entity instanceof XwingAircraftEntity) {
				((XwingAircraftEntity) entity).setAnimation("idle_1");
			}
			return;
		}
		entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((driver.getLookAngle().x), (driver.getLookAngle().y), (driver.getLookAngle().z)));
		speed = 5;
		if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_f) {
			if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l && driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = entity.getYRot() * 0.0174533 + Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = entity.getYRot() * 0.0174533 + Math.PI / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = entity.getYRot() * 0.0174533 + (3 * Math.PI) / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else {
				yaw = entity.getYRot() * 0.0174533 + Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_b) {
			if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r && driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = entity.getYRot() * 0.0174533 - Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = entity.getYRot() * 0.0174533 - Math.PI / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = entity.getYRot() * 0.0174533 - (3 * Math.PI) / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else {
				yaw = entity.getYRot() * 0.0174533 - Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
			if (!driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = entity.getYRot() * 0.0174533 + Math.PI;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (entity.getDeltaMovement().y()), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
			if (!driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = entity.getYRot() * 0.0174533;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (entity.getDeltaMovement().y()), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		}
	}
}
