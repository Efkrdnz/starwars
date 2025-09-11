package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.bus.api.Event;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.BufferBuilder;

public class GuardShapeProcedure {
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
		if (GuardShapeProcedure.bufferBuilder == null) {
			if (update)
				clear();
			if (GuardShapeProcedure.vertexBuffer == null) {
				if (format == DefaultVertexFormat.POSITION_COLOR) {
					GuardShapeProcedure.mode = mode;
					GuardShapeProcedure.format = format;
					GuardShapeProcedure.bufferBuilder = Tesselator.getInstance().begin(mode, DefaultVertexFormat.POSITION_COLOR);
					return true;
				} else if (format == DefaultVertexFormat.POSITION_TEX_COLOR) {
					GuardShapeProcedure.mode = mode;
					GuardShapeProcedure.format = format;
					GuardShapeProcedure.bufferBuilder = Tesselator.getInstance().begin(mode, DefaultVertexFormat.POSITION_TEX_COLOR);
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

	private static VertexBuffer shape() {
		return vertexBuffer;
	}

	public static VertexBuffer execute() {
		return execute(null);
	}

	private static VertexBuffer execute(@Nullable Event event) {
		double i = 0;
		double j = 0;
		double k = 0;
		double l = 0;
		if (begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR, false)) {
			for (int index0 = 0; index0 < 90; index0++) {
				for (int index1 = 0; index1 < 45; index1++) {
					k = 255 - (j / 180) * 95;
					l = 255 - ((j + 4) / 180) * 95;
					add((float) (Math.sin(Math.toRadians(i)) * Math.sin(Math.toRadians(j)) * 0.5), (float) (Math.cos(Math.toRadians(j)) * 0.5), (float) (Math.cos(Math.toRadians(i)) * Math.sin(Math.toRadians(j)) * 0.5),
							255 << 24 | (int) k << 16 | (int) k << 8 | (int) k);
					add((float) (Math.sin(Math.toRadians(i)) * Math.sin(Math.toRadians(j + 4)) * 0.5), (float) (Math.cos(Math.toRadians(j + 4)) * 0.5), (float) (Math.cos(Math.toRadians(i)) * Math.sin(Math.toRadians(j + 4)) * 0.5),
							255 << 24 | (int) l << 16 | (int) l << 8 | (int) l);
					add((float) (Math.sin(Math.toRadians(i + 4)) * Math.sin(Math.toRadians(j + 4)) * 0.5), (float) (Math.cos(Math.toRadians(j + 4)) * 0.5), (float) (Math.cos(Math.toRadians(i + 4)) * Math.sin(Math.toRadians(j + 4)) * 0.5),
							255 << 24 | (int) l << 16 | (int) l << 8 | (int) l);
					add((float) (Math.sin(Math.toRadians(i + 4)) * Math.sin(Math.toRadians(j)) * 0.5), (float) (Math.cos(Math.toRadians(j)) * 0.5), (float) (Math.cos(Math.toRadians(i + 4)) * Math.sin(Math.toRadians(j)) * 0.5),
							255 << 24 | (int) k << 16 | (int) k << 8 | (int) k);
					j = j + 4;
				}
				j = 0;
				i = i + 4;
			}
			i = 0;
			end();
		}
		return shape();
	}
}
