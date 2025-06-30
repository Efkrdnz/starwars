package net.efkrdnz.starwarsverse.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.world.inventory.ForcemenuguiMenu;
import net.efkrdnz.starwarsverse.network.ForcemenuguiButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class ForcemenuguiScreen extends AbstractContainerScreen<ForcemenuguiMenu> {
	private final static HashMap<String, Object> guistate = ForcemenuguiMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_1;
	Button button_2;
	Button button_3;
	Button button_force_push;
	Button button_force_hold;
	Button button_force_pull;
	Button button_force_push1;
	Button button_force_sens;

	public ForcemenuguiScreen(ForcemenuguiMenu container, Inventory inventory, Component text) {
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
		if (Minecraft.getInstance().screen instanceof ForcemenuguiScreen sc) {

		}
		return textstate;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("starwarsverse:textures/screens/forcemenugui.png");

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
	}

	@Override
	public void init() {
		super.init();
		button_1 = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_1"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 12, this.topPos + 134, 30, 20).build();
		guistate.put("button:button_1", button_1);
		this.addRenderableWidget(button_1);
		button_2 = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_2"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(1, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 1, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 72, this.topPos + 134, 30, 20).build();
		guistate.put("button:button_2", button_2);
		this.addRenderableWidget(button_2);
		button_3 = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_3"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(2, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 2, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 132, this.topPos + 134, 30, 20).build();
		guistate.put("button:button_3", button_3);
		this.addRenderableWidget(button_3);
		button_force_push = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_force_push"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(3, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 3, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 8, 77, 20).build();
		guistate.put("button:button_force_push", button_force_push);
		this.addRenderableWidget(button_force_push);
		button_force_hold = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_force_hold"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(4, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 4, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 32, 77, 20).build();
		guistate.put("button:button_force_hold", button_force_hold);
		this.addRenderableWidget(button_force_hold);
		button_force_pull = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_force_pull"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(5, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 5, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 90, this.topPos + 8, 77, 20).build();
		guistate.put("button:button_force_pull", button_force_pull);
		this.addRenderableWidget(button_force_pull);
		button_force_push1 = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_force_push1"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(6, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 6, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 90, this.topPos + 32, 77, 20).build();
		guistate.put("button:button_force_push1", button_force_push1);
		this.addRenderableWidget(button_force_push1);
		button_force_sens = Button.builder(Component.translatable("gui.starwarsverse.forcemenugui.button_force_sens"), e -> {
			if (true) {
				PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(7, x, y, z, getEditBoxAndCheckBoxValues()));
				ForcemenuguiButtonMessage.handleButtonAction(entity, 7, x, y, z, getEditBoxAndCheckBoxValues());
			}
		}).bounds(this.leftPos + 10, this.topPos + 56, 77, 20).build();
		guistate.put("button:button_force_sens", button_force_sens);
		this.addRenderableWidget(button_force_sens);
	}
}
