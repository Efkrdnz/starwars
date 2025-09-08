package net.efkrdnz.starwarsverse.entity.model;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

import net.efkrdnz.starwarsverse.entity.TempBossEntity;

public class TempBossModel extends GeoModel<TempBossEntity> {
	@Override
	public ResourceLocation getAnimationResource(TempBossEntity entity) {
		return ResourceLocation.parse("starwarsverse:animations/boss.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TempBossEntity entity) {
		return ResourceLocation.parse("starwarsverse:geo/boss.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TempBossEntity entity) {
		return ResourceLocation.parse("starwarsverse:textures/entities/" + entity.getTexture() + ".png");
	}

	@Override
	public void setCustomAnimations(TempBossEntity animatable, long instanceId, AnimationState animationState) {
		GeoBone head = getAnimationProcessor().getBone("head_ext");
		if (head != null) {
			EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}

	}
}
