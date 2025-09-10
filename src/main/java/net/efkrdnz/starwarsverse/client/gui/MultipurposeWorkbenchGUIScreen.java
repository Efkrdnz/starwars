package net.efkrdnz.starwarsverse.client.gui;

import net.neoforged.neoforge.network.PacketDistributor;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.world.inventory.MultipurposeWorkbenchGUIMenu;
import net.efkrdnz.starwarsverse.network.MultipurposeWorkbenchGUIButtonMessage;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class MultipurposeWorkbenchGUIScreen extends AbstractContainerScreen<MultipurposeWorkbenchGUIMenu> {
	private final static HashMap<String, Object> guistate = MultipurposeWorkbenchGUIMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	ImageButton imagebutton_crystal_assembly;

	public MultipurposeWorkbenchGUIScreen(MultipurposeWorkbenchGUIMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 192;
		this.imageHeight = 164;
	}

	public static HashMap<String, String> getEditBoxAndCheckBoxValues() {
		HashMap<String, String> textstate = new HashMap<>();
		if (Minecraft.getInstance().screen instanceof MultipurposeWorkbenchGUIScreen sc) {

		}
		return textstate;
	}

	private static final ResourceLocation texture = ResourceLocation.parse("starwarsverse:textures/screens/multipurpose_workbench_gui.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
		if (mouseX > leftPos + 13 && mouseX < leftPos + 37 && mouseY > topPos + -24 && mouseY < topPos + 0)
			guiGraphics.renderTooltip(font, Component.translatable("gui.starwarsverse.multipurpose_workbench_gui.tooltip_kyber_crystal_infusion"), mouseX, mouseY);
		if (mouseX > leftPos + 21 && mouseX < leftPos + 45 && mouseY > topPos + -24 && mouseY < topPos + 0)
			guiGraphics.renderTooltip(font, Component.translatable("gui.starwarsverse.multipurpose_workbench_gui.tooltip_kyber_crystal_infusion1"), mouseX, mouseY);
		if (mouseX > leftPos + 13 && mouseX < leftPos + 37 && mouseY > topPos + -29 && mouseY < topPos + -5)
			guiGraphics.renderTooltip(font, Component.translatable("gui.starwarsverse.multipurpose_workbench_gui.tooltip_kyber_crystal_infusion2"), mouseX, mouseY);
		if (mouseX > leftPos + 21 && mouseX < leftPos + 45 && mouseY > topPos + -29 && mouseY < topPos + -5)
			guiGraphics.renderTooltip(font, Component.translatable("gui.starwarsverse.multipurpose_workbench_gui.tooltip_kyber_crystal_infusion3"), mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		guiGraphics.blit(ResourceLocation.parse("starwarsverse:textures/screens/multipurpose_bench_gui.png"), this.leftPos + 0, this.topPos + 0, 0, 0, 192, 164, 192, 164);

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
		imagebutton_crystal_assembly = new ImageButton(this.leftPos + 13, this.topPos + -29, 32, 32,
				new WidgetSprites(ResourceLocation.parse("starwarsverse:textures/screens/crystal_assembly_unexposed.png"), ResourceLocation.parse("starwarsverse:textures/screens/crystal_assembly.png")), e -> {
					if (true) {
						PacketDistributor.sendToServer(new MultipurposeWorkbenchGUIButtonMessage(0, x, y, z, getEditBoxAndCheckBoxValues()));
						MultipurposeWorkbenchGUIButtonMessage.handleButtonAction(entity, 0, x, y, z, getEditBoxAndCheckBoxValues());
					}
				}) {
			@Override
			public void renderWidget(GuiGraphics guiGraphics, int x, int y, float partialTicks) {
				guiGraphics.blit(sprites.get(isActive(), isHoveredOrFocused()), getX(), getY(), 0, 0, width, height, width, height);
			}
		};
		guistate.put("button:imagebutton_crystal_assembly", imagebutton_crystal_assembly);
		this.addRenderableWidget(imagebutton_crystal_assembly);
	}
}
