package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

public class XwingAircraftOnEntityTickUpdateProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity driver = null;
		double pitch_new = 0;
		double pitch = 0;
		double speed = 0;
		double yaw = 0;
		if (!((entity.getFirstPassenger()) == (null))) {
			driver = entity.getFirstPassenger();
		}
		if (driver == (null)) {
			return;
		}
		speed = 2.5;
		pitch_new = driver.getXRot();
		if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_f) {
			if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l && driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = driver.getYRot() * 0.0174533 + Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = driver.getYRot() * 0.0174533 + Math.PI / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = driver.getYRot() * 0.0174533 + (3 * Math.PI) / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else {
				yaw = driver.getYRot() * 0.0174533 + Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_b) {
			if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r && driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = driver.getYRot() * 0.0174533 - Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), ((-1) * speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = driver.getYRot() * 0.0174533 - Math.PI / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), ((-1) * speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = driver.getYRot() * 0.0174533 - (3 * Math.PI) / 4;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), ((-1) * speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			} else {
				yaw = driver.getYRot() * 0.0174533 - Math.PI / 2;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), ((-1) * speed * Math.sin((-1) * pitch)), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
			if (!driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
				yaw = driver.getYRot() * 0.0174533 + 0;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (entity.getDeltaMovement().y()), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		} else if (driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_r) {
			if (!driver.getData(StarwarsverseModVariables.PLAYER_VARIABLES).ship_l) {
				yaw = driver.getYRot() * 0.0174533 + Math.PI;
				pitch = pitch_new * 0.0174533;
				entity.setDeltaMovement(new Vec3((speed * Math.cos(yaw) * Math.cos(pitch)), (entity.getDeltaMovement().y()), (speed * Math.sin(yaw) * Math.cos(pitch))));
			}
		} else {
			entity.setDeltaMovement(new Vec3((entity.getDeltaMovement().x()), (entity.getDeltaMovement().y()), (entity.getDeltaMovement().z())));
		}
	}
}
