
package net.efkrdnz.starwarsverse.world.inventory;

import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.IItemHandler;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import net.efkrdnz.starwarsverse.procedures.ForcemenuguiThisGUIIsClosedProcedure;
import net.efkrdnz.starwarsverse.network.ForcemenuguiButtonMessage;
import net.efkrdnz.starwarsverse.init.StarwarsverseModMenus;
import net.efkrdnz.starwarsverse.client.gui.ForcemenuguiScreen;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

public class ForcemenuguiMenu extends AbstractContainerMenu implements Supplier<Map<Integer, Slot>> {
	public final static HashMap<String, Object> guistate = new HashMap<>();
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private IItemHandler internal;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private Supplier<Boolean> boundItemMatcher = null;
	private Entity boundEntity = null;
	private BlockEntity boundBlockEntity = null;

	public ForcemenuguiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		super(StarwarsverseModMenus.FORCEMENUGUI.get(), id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.internal = new ItemStackHandler(0);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			access = ContainerLevelAccess.create(world, pos);
		}
		ForcemenuguiThisGUIIsClosedProcedure.execute(entity);
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.bound) {
			if (this.boundItemMatcher != null)
				return this.boundItemMatcher.get();
			else if (this.boundBlockEntity != null)
				return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
			else if (this.boundEntity != null)
				return this.boundEntity.isAlive();
		}
		return true;
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		return ItemStack.EMPTY;
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		removeAction();
	}

	private void removeAction() {
		if (this.world != null && this.world.isClientSide()) {
			PacketDistributor.sendToServer(new ForcemenuguiButtonMessage(-2, x, y, z, ForcemenuguiScreen.getEditBoxAndCheckBoxValues()));
			ForcemenuguiButtonMessage.handleButtonAction(entity, -2, x, y, z, ForcemenuguiScreen.getEditBoxAndCheckBoxValues());
		}
	}

	public Map<Integer, Slot> get() {
		return customSlots;
	}
}
