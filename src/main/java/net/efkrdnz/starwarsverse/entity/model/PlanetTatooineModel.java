package net.efkrdnz.starwarsverse.entity.model;

import software.bernie.geckolib.model.GeoModel;

import net.minecraft.resources.ResourceLocation;

import net.efkrdnz.starwarsverse.entity.PlanetTatooineEntity;

public class PlanetTatooineModel extends GeoModel<PlanetTatooineEntity> {
	@Override
	public ResourceLocation getAnimationResource(PlanetTatooineEntity entity) {
		return ResourceLocation.parse("starwarsverse:animations/planet_earth.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(PlanetTatooineEntity entity) {
		return ResourceLocation.parse("starwarsverse:geo/planet_earth.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PlanetTatooineEntity entity) {
		return ResourceLocation.parse("starwarsverse:textures/entities/" + entity.getTexture() + ".png");
	}

}
