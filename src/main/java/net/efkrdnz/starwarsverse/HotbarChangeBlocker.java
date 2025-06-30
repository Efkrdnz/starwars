package net.efkrdnz.starwarsverse;

import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import javax.annotation.Nullable;

@EventBusSubscriber(modid = "starwarsverse")
public class HotbarChangeBlocker {
    
    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
        if (event.getPlayer() != null) {
            execute(event, event.getPlayer());
        }
    }
    
    private static void execute(@Nullable Event event, Player player) {
        if (player == null) return;
        
        StarwarsverseModVariables.PlayerVariables vars = player.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
        
        // Prevent item pickup during telekinesis to avoid interruptions
        if (vars.is_using_telekinesis) {
            if (event instanceof ICancellableEvent _cancellable) {
                _cancellable.setCanceled(true);
            }
        }
    }
}