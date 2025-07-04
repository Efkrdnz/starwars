
package net.efkrdnz.starwarsverse.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.efkrdnz.starwarsverse.entity.model.XwingAircraftModel;
import net.efkrdnz.starwarsverse.entity.XwingAircraftEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class XwingAircraftRenderer extends GeoEntityRenderer<XwingAircraftEntity> {
	public XwingAircraftRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new XwingAircraftModel());
		this.shadowRadius = 0.7f;
	}

	@Override
	public RenderType getRenderType(XwingAircraftEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, XwingAircraftEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
	}

	@Override
	protected float getDeathMaxRotation(XwingAircraftEntity entityLivingBaseIn) {
		return 0.0F;
	}
}
