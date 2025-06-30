package net.efkrdnz.starwarsverse.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

public class XwingAircraftModel extends GeoModel<XwingAircraftEntity> {
	@Override
	public ResourceLocation getAnimationResource(XwingAircraftEntity entity) {
		return ResourceLocation.parse("starwarsverse:animations/x_wings.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(XwingAircraftEntity entity) {
		return ResourceLocation.parse("starwarsverse:geo/x_wings.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(XwingAircraftEntity entity) {
		return ResourceLocation.parse("starwarsverse:textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(XwingAircraftEntity animatable, long instanceId, AnimationState animationState) {
		GeoBone head = getAnimationProcessor().getBone("ship");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
