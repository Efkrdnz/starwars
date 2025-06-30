package net.efkrdnz.starwarsverse.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.world.inventory.KyberInfuserGuiMenu;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class KyberInfuserGuiScreen extends AbstractContainerScreen<KyberInfuserGuiMenu> {
	private final static HashMap<String, Object> guistate = KyberInfuserGuiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;

	public KyberInfuserGuiScreen(KyberInfuserGuiMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof KyberInfuserGuiScreen sc) {

		}
		return textstate;
	}

	@Override
	public void containerTick() {
		super.containerTick();
		PacketDistributor.sendToServer(new KyberInfuserGuiMenu.KyberInfuserGuiSyncMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
		KyberInfuserGuiMenu.KyberInfuserGuiSyncMessage.handleSyncAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
	}

	private static final ResourceLocation texture = ResourceLocation.parse("starwarsverse:textures/screens/kyber_infuser_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.starwarsverse.kyber_infuser_gui.label_lightsaber_must_be"), 14, 53, -12829636, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.starwarsverse.kyber_infuser_gui.label_for_kyber_infusion"), 40, 61, -12829636, false);
	}

	@Override
	public void init() {
		super.init();
	}
}
