package net.efkrdnz.starwarsverse.client.renderer;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import net.efkrdnz.starwarsverse.entity.LaserEntity;
import net.efkrdnz.starwarsverse.client.model.Modellaserbeam;

import com.mojang.math.Axis;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class LaserRenderer extends EntityRenderer<LaserEntity> {
	private static final ResourceLocation texture = ResourceLocation.parse("starwarsverse:textures/entities/laserbeam1.png");
	private final Modellaserbeam model;

	public LaserRenderer(EntityRendererProvider.Context context) {
		super(context);
		model = new Modellaserbeam(context.bakeLayer(Modellaserbeam.LAYER_LOCATION));
	}

	@Override
	public void render(LaserEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		// render with emissive render type for glow effect
		VertexConsumer vb = bufferIn.getBuffer(RenderType.eyes(this.getTextureLocation(entityIn)));
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entityIn.yRotO, entityIn.getYRot()) - 90));
		poseStack.mulPose(Axis.ZP.rotationDegrees(90 + Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot())));
		// use full bright lighting to make it glow - same value as particles use
		model.renderToBuffer(poseStack, vb, 15728880, OverlayTexture.NO_OVERLAY);
		poseStack.popPose();
		super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(LaserEntity entity) {
		return texture;
	}
}
