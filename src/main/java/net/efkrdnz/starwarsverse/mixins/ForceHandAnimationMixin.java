
package net.efkrdnz.starwarsverse.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.player.AbstractClientPlayer;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

@Mixin(ItemInHandRenderer.class)
public class ForceHandAnimationMixin {
	@Inject(method = "getItem", at = @At("RETURN"), cancellable = true)
	private void makeForceHandVisible(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<ItemStack> cir) {
		// Only affect the off-hand
		if (hand == InteractionHand.OFF_HAND) {
			StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			// If using force powers and off-hand is empty, return an invisible barrier block
			if ((vars.is_using_telekinesis) && cir.getReturnValue().isEmpty()) {
				// Create an invisible item stack (barrier block is invisible when held)
				ItemStack invisibleItem = new ItemStack(Items.BARRIER);
				cir.setReturnValue(invisibleItem);
			}
		}
	}
}
