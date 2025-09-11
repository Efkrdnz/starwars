package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.bus.api.Event;

import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;

public class GuardRingShapeProcedure {
	private static BufferBuilder bufferBuilder = null;
	private static VertexBuffer vertexBuffer = null;
	private static VertexFormat.Mode mode = null;
	private static VertexFormat format = null;

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
		if (GuardRingShapeProcedure.bufferBuilder == null) {
			if (update)
				clear();
			if (GuardRingShapeProcedure.vertexBuffer == null) {
				if (format == DefaultVertexFormat.POSITION_COLOR) {
					GuardRingShapeProcedure.mode = mode;
					GuardRingShapeProcedure.format = format;
					GuardRingShapeProcedure.bufferBuilder = Tesselator.getInstance().begin(mode, DefaultVertexFormat.POSITION_COLOR);
					return true;
				} else if (format == DefaultVertexFormat.POSITION_TEX_COLOR) {
					GuardRingShapeProcedure.mode = mode;
					GuardRingShapeProcedure.format = format;
					GuardRingShapeProcedure.bufferBuilder = Tesselator.getInstance().begin(mode, DefaultVertexFormat.POSITION_TEX_COLOR);
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
			vertexBuffer = new VertexBuffer(VertexBuffer.Usage.DYNAMIC);
			vertexBuffer.bind();
			vertexBuffer.upload(meshData);
			VertexBuffer.unbind();
			bufferBuilder = null;
		}
	}

	private static VertexBuffer shape() {
		return vertexBuffer;
	}

	public static VertexBuffer execute() {
		return execute(null);
	}

	private static VertexBuffer execute(@Nullable Event event) {
		// get current guard value for dynamic ring
		double guardValue = 0;
		double maxGuard = 60;
		if (Minecraft.getInstance().player != null) {
			guardValue = Minecraft.getInstance().player.getData(StarwarsverseModVariables.PLAYER_VARIABLES).guard;
		}
		// calculate fill percentage
		double fillPercentage = guardValue / maxGuard;
		if (begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR, true)) {
			// ring parameters - much larger
			float outerRadius = 0.5f;
			float innerRadius = 0.4f;
			int segments = 64;
			// calculate how many segments to fill based on guard value
			int fillSegments = (int) (segments * fillPercentage);
			// create ring geometry
			for (int i = 0; i < fillSegments; i++) {
				float angle1 = (float) (i * 2.0 * Math.PI / segments - Math.PI / 2); // start from top
				float angle2 = (float) ((i + 1) * 2.0 * Math.PI / segments - Math.PI / 2);
				// calculate color based on guard value
				int color = getGuardColor(guardValue);
				// outer ring vertices
				float x1_outer = (float) (Math.cos(angle1) * outerRadius);
				float y1_outer = (float) (Math.sin(angle1) * outerRadius);
				float x2_outer = (float) (Math.cos(angle2) * outerRadius);
				float y2_outer = (float) (Math.sin(angle2) * outerRadius);
				// inner ring vertices
				float x1_inner = (float) (Math.cos(angle1) * innerRadius);
				float y1_inner = (float) (Math.sin(angle1) * innerRadius);
				float x2_inner = (float) (Math.cos(angle2) * innerRadius);
				float y2_inner = (float) (Math.sin(angle2) * innerRadius);
				// first triangle (outer)
				add(x1_outer, y1_outer, 0, color);
				add(x1_inner, y1_inner, 0, color);
				add(x2_outer, y2_outer, 0, color);
				// second triangle (inner)
				add(x1_inner, y1_inner, 0, color);
				add(x2_inner, y2_inner, 0, color);
				add(x2_outer, y2_outer, 0, color);
			}
			end();
		}
		return shape();
	}

	// color determination based on guard value
	private static int getGuardColor(double guardValue) {
		if (guardValue >= 36) { // 60% of 60 = 36
			return 255 << 24 | 255 << 16 | 0 << 8 | 0; // red - not blockable
		} else if (guardValue >= 18) { // 30% of 60 = 18
			return 255 << 24 | 32 << 16 | 90 << 8 | 171; // blue - normal block
		} else {
			return 255 << 24 | 245 << 16 | 149 << 8 | 5; // gold yellow - parry timeframe
		}
	}
}
