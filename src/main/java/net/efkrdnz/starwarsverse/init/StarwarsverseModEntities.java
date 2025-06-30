
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.core.registries.Registries;

import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;
import net.efkrdnz.starwarsverse.entity.PlanetTatooineEntity;
import net.efkrdnz.starwarsverse.entity.PlanetEarthEntity;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class StarwarsverseModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, StarwarsverseMod.MODID);
	public static final DeferredHolder<EntityType<?>, EntityType<PlanetEarthEntity>> PLANET_EARTH = register("planet_earth",
			EntityType.Builder.<PlanetEarthEntity>of(PlanetEarthEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final DeferredHolder<EntityType<?>, EntityType<PlanetTatooineEntity>> PLANET_TATOOINE = register("planet_tatooine",
			EntityType.Builder.<PlanetTatooineEntity>of(PlanetTatooineEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).fireImmune().sized(0.6f, 1.8f));
	public static final DeferredHolder<EntityType<?>, EntityType<XwingAircraftEntity>> XWING_AIRCRAFT = register("xwing_aircraft",
			EntityType.Builder.<XwingAircraftEntity>of(XwingAircraftEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3)

					.sized(3.2f, 3.2f));

	// Start of user code block custom entities
	// End of user code block custom entities
	private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(RegisterSpawnPlacementsEvent event) {
		PlanetEarthEntity.init(event);
		PlanetTatooineEntity.init(event);
		XwingAircraftEntity.init(event);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(PLANET_EARTH.get(), PlanetEarthEntity.createAttributes().build());
		event.put(PLANET_TATOOINE.get(), PlanetTatooineEntity.createAttributes().build());
		event.put(XWING_AIRCRAFT.get(), XwingAircraftEntity.createAttributes().build());
	}
}
