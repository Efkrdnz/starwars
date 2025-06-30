
package net.efkrdnz.starwarsverse.client.renderer;

import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.cache.object.BakedGeoModel;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.efkrdnz.starwarsverse.entity.model.PlanetTatooineModel;
import net.efkrdnz.starwarsverse.entity.layer.PlanetTatooineLayer;
import net.efkrdnz.starwarsverse.entity.PlanetTatooineEntity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class PlanetTatooineRenderer extends GeoEntityRenderer<PlanetTatooineEntity> {
	public PlanetTatooineRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new PlanetTatooineModel());
		this.shadowRadius = 0.5f;
		this.addRenderLayer(new PlanetTatooineLayer(this));
	}

	@Override
	public RenderType getRenderType(PlanetTatooineEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void preRender(PoseStack poseStack, PlanetTatooineEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int color) {
		float scale = 1f;
		this.scaleHeight = scale;
		this.scaleWidth = scale;
		super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, color);
	}
}
