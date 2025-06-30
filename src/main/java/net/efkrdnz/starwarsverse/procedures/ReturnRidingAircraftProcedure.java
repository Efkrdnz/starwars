package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class ReturnRidingAircraftProcedure {
	public static Entity execute(Entity entity) {
		if (entity == null)
			return null;
		if (!((entity.getVehicle()) == null)) {
			if ((entity.getVehicle()).getType().is(TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.parse("sw_aircraft")))) {
				return entity.getVehicle();
			}
		}
		return null;
	}
}
