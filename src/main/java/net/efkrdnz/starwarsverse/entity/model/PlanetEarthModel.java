package net.efkrdnz.starwarsverse.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.efkrdnz.starwarsverse.entity.PlanetEarthEntity;

public class PlanetEarthModel extends GeoModel<PlanetEarthEntity> {
	@Override
	public ResourceLocation getAnimationResource(PlanetEarthEntity entity) {
		return ResourceLocation.parse("starwarsverse:animations/planet_earth.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PlanetEarthEntity entity) {
		return ResourceLocation.parse("starwarsverse:geo/planet_earth.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PlanetEarthEntity entity) {
		return ResourceLocation.parse("starwarsverse:textures/entities/" + entity.getTexture() + ".png");
	}

}
