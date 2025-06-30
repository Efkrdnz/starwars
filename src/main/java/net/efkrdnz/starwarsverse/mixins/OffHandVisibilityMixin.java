package net.efkrdnz.starwarsverse.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.model.HumanoidModel;

@Mixin(PlayerRenderer.class)
public class OffHandVisibilityMixin {
	@Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
	private static void forceOffHandVisible(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
		if (hand == InteractionHand.OFF_HAND) {
			ItemStack offHandStack = player.getOffhandItem();
			if (offHandStack.isEmpty()) {
				cir.setReturnValue(HumanoidModel.ArmPose.EMPTY);
			}
		}
	}
}
