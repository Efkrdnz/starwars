package net.efkrdnz.starwarsverse.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.client.player.LocalPlayer;

import net.efkrdnz.starwarsverse.init.StarwarsverseModItems;

import java.util.function.Consumer;

import com.mojang.blaze3d.vertex.PoseStack;

@Mixin(Item.class)
public abstract class SwingCancelMixin {
	@Inject(method = "initializeClient", at = @At("HEAD"), cancellable = true, remap = false)
	public void onInitializeClient(Consumer<IClientItemExtensions> consumer, CallbackInfo ci) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
				if (itemInHand.getItem() == StarwarsverseModItems.LIGHTSABER_ANAKIN.get()) {
					int i = arm == HumanoidArm.RIGHT ? 1 : -1;
					poseStack.translate(i * 0.56F, -0.52F, -0.72F);
					if (player.getUseItem() == itemInHand) {
						poseStack.translate(0.05F, 0.05F, 0.05F);
					}
					return true;
				}
				return false;
			}
		});
		ci.cancel();
	}
}
