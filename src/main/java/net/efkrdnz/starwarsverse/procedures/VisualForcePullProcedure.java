package net.efkrdnz.starwarsverse.procedures;

import org.joml.Matrix4f;

import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;
import net.efkrdnz.starwarsverse.ForcePowers;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.systems.RenderSystem;

@EventBusSubscriber(value = Dist.CLIENT)
public class VisualForcePullProcedure {
	private static BufferBuilder bufferBuilder = null;
	private static VertexBuffer vertexBuffer = null;
	private static VertexFormat.Mode mode = null;
	private static VertexFormat format = null;
	private static PoseStack poseStack = null;
	private static Matrix4f modelViewMatrix = null;
	private static Matrix4f projectionMatrix = null;
	private static boolean worldCoordinate = true;
	private static Vec3 offset = Vec3.ZERO;
	private static int currentStage = 0;
	private static int targetStage = 0; // NONE: 0, SKY: 1, WORLD: 2

	private static void add(float x, float y, float z, int color) {
		add(x, y, z, 0.0F, 0.0F, color);
	}

	private static void add(float x, float y, float z, float u, float v, int color) {
		if (bufferBuilder == null)
			return;
		if (format == DefaultVertexFormat.POSITION_COLOR) {
			bufferBuilder.addVertex(x, y, z).setColor(color);
		} else if (format == DefaultVertexFormat.POSITION_TEX_COLOR) {
			bufferBuilder.addVertex(x, y, z).setUv(u, v).setColor(color);
		}
	}

	private static boolean begin(VertexFormat.Mode mode, VertexFormat format, boolean update) {
		if (VisualForcePullProcedure.bufferBuilder == null) {
			if (update)
				clear();
			if (VisualForcePullProcedure.vertexBuffer == null) {
				if (format == DefaultVertexFormat.POSITION_COLOR) {
					VisualForcePullProcedure.mode = mode;
					VisualForcePullProcedure.format = format;
					VisualForcePullProcedure.bufferBuilder = Tesselator.getInstance().begin(mode, DefaultVertexFormat.POSITION_COLOR);
					return true;
				} else if (format == DefaultVertexFormat.POSITION_TEX_COLOR) {
					VisualForcePullProcedure.mode = mode;
					VisualForcePullProcedure.format = format;
					VisualForcePullProcedure.bufferBuilder = Tesselator.getInstance().begin(mode, DefaultVertexFormat.POSITION_TEX_COLOR);
					return true;
				}
			}
		}
		return false;
	}

	private static void clear() {
		if (vertexBuffer != null) {
			vertexBuffer.close();
			vertexBuffer = null;
		}
	}

	private static void end() {
		if (bufferBuilder == null)
			return;
		if (vertexBuffer != null)
			vertexBuffer.close();
		MeshData meshData = bufferBuilder.build();
		if (meshData == null) {
			vertexBuffer = null;
			bufferBuilder = null;
		} else {
			vertexBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
			vertexBuffer.bind();
			vertexBuffer.upload(meshData);
			VertexBuffer.unbind();
			bufferBuilder = null;
		}
	}

	private static void offset(double x, double y, double z) {
		offset = new Vec3(x, y, z);
	}

	private static void release() {
		targetStage = 0;
	}

	private static VertexBuffer shape() {
		return vertexBuffer;
	}

	private static void system(boolean worldCoordinate) {
		VisualForcePullProcedure.worldCoordinate = worldCoordinate;
	}

	private static boolean target(int targetStage) {
		if (targetStage == currentStage) {
			VisualForcePullProcedure.targetStage = targetStage;
			return true;
		}
		return false;
	}

	private static void renderShape(VertexBuffer vertexBuffer, double x, double y, double z, float yaw, float pitch, float roll, float xScale, float yScale, float zScale, int color) {
		if (currentStage == 0 || currentStage != targetStage)
			return;
		if (poseStack == null || projectionMatrix == null)
			return;
		if (vertexBuffer == null)
			return;
		float i, j, k;
		if (worldCoordinate) {
			Vec3 pos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
			i = (float) (x - pos.x());
			j = (float) (y - pos.y());
			k = (float) (z - pos.z());
		} else {
			i = (float) x;
			j = (float) y;
			k = (float) z;
		}
		poseStack.pushPose();
		poseStack.mulPose(modelViewMatrix);
		poseStack.translate(i, j, k);
		poseStack.mulPose(com.mojang.math.Axis.YN.rotationDegrees(yaw));
		poseStack.mulPose(com.mojang.math.Axis.XP.rotationDegrees(pitch));
		poseStack.mulPose(com.mojang.math.Axis.ZN.rotationDegrees(roll));
		poseStack.scale(xScale, yScale, zScale);
		poseStack.translate(offset.x(), offset.y(), offset.z());
		RenderSystem.setShaderColor((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, (color >>> 24) / 255.0F);
		vertexBuffer.bind();
		vertexBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, vertexBuffer.getFormat().hasUV(0) ? GameRenderer.getPositionTexColorShader() : GameRenderer.getPositionColorShader());
		VertexBuffer.unbind();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.popPose();
	}

	@SubscribeEvent
	public static void renderLevel(RenderLevelStageEvent event) {
		if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
			currentStage = 1;
			poseStack = new PoseStack();
			RenderSystem.depthMask(false);
			renderShapes(event);
			RenderSystem.enableCull();
			RenderSystem.depthMask(true);
			currentStage = 0;
		} else if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
			currentStage = 2;
			poseStack = event.getPoseStack();
			RenderSystem.depthMask(true);
			renderShapes(event);
			RenderSystem.enableCull();
			RenderSystem.depthMask(true);
			currentStage = 0;
		}
	}

	private static void renderShapes(RenderLevelStageEvent event) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		Entity entity = minecraft.gameRenderer.getMainCamera().getEntity();
		if (level != null && entity != null) {
			modelViewMatrix = event.getModelViewMatrix();
			projectionMatrix = event.getProjectionMatrix();
			Vec3 pos = entity.getPosition(event.getPartialTick().getGameTimeDeltaPartialTick(false));
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			execute(event, entity);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableBlend();
			RenderSystem.enableDepthTest();
		}
	}

	public static void execute(Entity entity) {
		execute(null, entity);
	}

	private static void execute(@Nullable Event event, Entity entity) {
		if (entity == null)
			return;
		double i = 0;
		double j = 0;
		double k = 0;
		double l = 0;
		double chargingstatus = 0;
		double pitch = 0;
		double yaw = 0;
		double yaw1 = 0;
		double pitch1 = 0;
		double distance = 0;
		double broadness = 0;
		if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).force_indicator) {
			if (entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).is_using_force) {
				if ((entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).current_charging_power).equals("force_pull")) {
					ForcePowers.ForcePowerDefinition power = ForcePowers.getPower("force_push");
					if (power == null) {
						return;
					}
					int maxcharge = power.getMaxChargeTime();
					String forcename = power.getName();
					chargingstatus = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES).charge_timer / maxcharge;
					if (!entity.isShiftKeyDown()) {
						distance = 4 + 3 * chargingstatus;
						broadness = 2 + 3 * chargingstatus;
						yaw = entity.getYRot() * 0.0174533 + Math.PI / 2 + Math.asin(broadness / distance);
						yaw1 = entity.getYRot() * 0.0174533 + Math.PI / 2 - Math.asin(broadness / distance);
						pitch = entity.getXRot() * 0.0174533;
						pitch1 = entity.getXRot() * 0.0174533;
						if (Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
							if (begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR, (!Minecraft.getInstance().isPaused()))) {
								add(0, (float) 0.2, 0, 255 << 24 | 255 << 16 | 255 << 8 | 255);
								add((float) (distance * Math.cos(yaw) * Math.cos(pitch)), (float) 0.8, (float) (distance * Math.sin(yaw) * Math.cos(pitch)), 255 << 24 | 255 << 16 | 255 << 8 | 255);
								add((float) (distance * Math.cos(yaw1) * Math.cos(pitch1)), (float) 0.8, (float) (distance * Math.sin(yaw1) * Math.cos(pitch1)), 255 << 24 | 255 << 16 | 255 << 8 | 255);
								add(0, (float) 0.2, 0, 255 << 24 | 255 << 16 | 255 << 8 | 255);
								end();
							}
							if (target(2)) {
								renderShape(shape(), (entity.getX()), (entity.getY() + 1.2), (entity.getZ()), 0, 0, 0, 1, 1, 1, 255 << 24 | 255 << 16 | 255 << 8 | 255);
								release();
							}
							clear();
						} else if (Minecraft.getInstance().options.getCameraType() == CameraType.THIRD_PERSON_BACK) {
							if (begin(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.POSITION_COLOR, (!Minecraft.getInstance().isPaused()))) {
								add(0, 0, 0, 255 << 24 | 255 << 16 | 255 << 8 | 255);
								add((float) (distance * Math.cos(yaw) * Math.cos(pitch)), (float) 0.4, (float) (distance * Math.sin(yaw) * Math.cos(pitch)), 255 << 24 | 255 << 16 | 255 << 8 | 255);
								add((float) (distance * Math.cos(yaw1) * Math.cos(pitch1)), (float) 0.4, (float) (distance * Math.sin(yaw1) * Math.cos(pitch1)), 255 << 24 | 255 << 16 | 255 << 8 | 255);
								add(0, 0, 0, 255 << 24 | 255 << 16 | 255 << 8 | 255);
								end();
							}
							if (target(2)) {
								renderShape(shape(), (entity.getX()), (entity.getY() + 0.2), (entity.getZ()), 0, 0, 0, 1, 1, 1, 255 << 24 | 255 << 16 | 255 << 8 | 255);
								release();
							}
						}
					}
				}
			}
		}
	}
}
