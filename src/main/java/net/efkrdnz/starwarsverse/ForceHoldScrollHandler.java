package net.efkrdnz.starwarsverse;

import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

@EventBusSubscriber(modid = "starwarsverse", value = Dist.CLIENT)
public class ForceHoldScrollHandler {
    
    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        
        StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
        
        // Only handle scroll if player is using telekinesis
        if (!vars.is_using_telekinesis) {
            return;
        }
        
        // Cancel the event to prevent hotbar slot changes
        event.setCanceled(true);
        
        // Get scroll direction
        double scrollDelta = event.getScrollDeltaY();
        
        if (scrollDelta != 0) {
            // Store scroll direction in player variables for server to read
            if (scrollDelta > 0) {
                vars.force_scroll_action = "push"; // Scroll up = push away
                System.out.println("Force scroll action: push");
            } else {
                vars.force_scroll_action = "pull"; // Scroll down = pull closer
                System.out.println("Force scroll action: pull");
            }
            vars.syncPlayerVariables(player);
        }
    }
}