package net.efkrdnz.starwarsverse.mixins;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

@Mixin(ItemInHandRenderer.class)
public class ForceOffhandRenderMixin {
    
    @Inject(method = "shouldRenderOffhand", at = @At("RETURN"), cancellable = true)
    private void forceRenderOffhandDuringForcePowers(AbstractClientPlayer player, CallbackInfoReturnable<Boolean> cir) {
        StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
        
        // Force render off-hand when using force powers
        if (vars.is_using_telekinesis) {
            cir.setReturnValue(true);
        }
    }
}