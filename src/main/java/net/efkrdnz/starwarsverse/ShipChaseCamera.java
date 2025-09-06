package net.efkrdnz.starwarsverse.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import net.efkrdnz.starwarsverse.procedures.ReturnRidingAircraftProcedure;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = "starwarsverse")
public class ShipChaseCamera {

    private static CameraType prevCamera = null;
    private static boolean wasRiding = false;

    // FOV tuning (render-time)
    private static final double SHIP_FOV = 130.0;   // make this obviously wider to verify
    private static final double FOV_SMOOTH = 0.35;
    private static double smoothedFov = -1;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        boolean ridingShip = ReturnRidingAircraftProcedure.execute(mc.player) != null;

        if (ridingShip && !wasRiding) {
            prevCamera = mc.options.getCameraType();
            // Force third person immediately when mounting
            mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            smoothedFov = SHIP_FOV; // seed smoothing
        } else if (!ridingShip && wasRiding) {
            // Restore previous camera when dismounting
            if (prevCamera != null) {
                mc.options.setCameraType(prevCamera);
                prevCamera = null;
            }
            smoothedFov = -1;
        }

        wasRiding = ridingShip;
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {
        Minecraft mc = Minecraft.getInstance();
        Player p = mc.player;
        if (p == null) return;

        // Apply while riding regardless of camera type (works for third person too)
        if (ReturnRidingAircraftProcedure.execute(p) == null) return;

        double current = (smoothedFov < 0) ? event.getFOV() : smoothedFov;
        double next = Mth.lerp(FOV_SMOOTH, current, SHIP_FOV);
        smoothedFov = next;
        event.setFOV(next);
    }

    // 2) Hide player model while riding the ship
    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        // Hide ONLY the local player while riding (other players still render normally)
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (player == mc.player && ReturnRidingAircraftProcedure.execute(player) != null) {
            event.setCanceled(true);
        }
    }
}
