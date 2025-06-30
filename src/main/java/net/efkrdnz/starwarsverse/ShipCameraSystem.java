/*
 * The code of this mod element is always locked.
 *
 * You can register new events in this class too.
 *
 * If you want to make a plain independent class, create it using
 * Project Browser -> New... and make sure to make the class
 * outside net.efkrdnz.starwarsverse as this package is managed by MCreator.
 *
 * If you change workspace package, modid or prefix, you will need
 * to manually adapt this file to these changes or remake it.
 *
 * This class will be added in the mod root package.
*/
package net.efkrdnz.starwarsverse;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.player.Player;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;

import net.efkrdnz.starwarsverse.procedures.ReturnRidingAircraftProcedure;

@Mod("starwarsverse")
public class ShipCameraSystem {
	public ShipCameraSystem() {
		if (net.neoforged.fml.loading.FMLEnvironment.dist == Dist.CLIENT) {
			NeoForge.EVENT_BUS.register(ClientEvents.class);
		}
		NeoForge.EVENT_BUS.register(ServerEvents.class);
	}

	public static class ServerEvents {
		@SubscribeEvent
		public static void serverLoad(ServerStartingEvent event) {
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static class ClientEvents {
		private static CameraType previousCameraType = null;
		private static int previousFov = -1;
		private static boolean wasRidingShip = false;
		private static int updateCounter = 0;
		// camera settings - adjust these as needed
		private static final int SHIP_FOV = 110; // max stable fov

		@SubscribeEvent
		public static void onPlayerTick(PlayerTickEvent.Post event) {
			if (!(event.getEntity() instanceof LocalPlayer player)) {
				return;
			}
			// only check every 20 ticks (1 second) to prevent twitching
			updateCounter++;
			if (updateCounter % 20 != 0) {
				return;
			}
			Minecraft mc = Minecraft.getInstance();
			boolean isRidingShip = (ReturnRidingAircraftProcedure.execute(player) != null);
			// entering ship
			if (isRidingShip && !wasRidingShip) {
				previousCameraType = mc.options.getCameraType();
				previousFov = mc.options.fov().get();
				mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
				mc.options.fov().set(SHIP_FOV);
			}
			// exiting ship
			else if (!isRidingShip && wasRidingShip) {
				if (previousCameraType != null) {
					mc.options.setCameraType(previousCameraType);
					previousCameraType = null;
				}
				if (previousFov >= 0) {
					mc.options.fov().set(previousFov);
					previousFov = -1;
				}
			}
			wasRidingShip = isRidingShip;
		}

		@SubscribeEvent
		public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
			if (event.getEntity() instanceof Player player) {
				// make player invisible when riding ship
				if (ReturnRidingAircraftProcedure.execute(player) != null) {
					event.setCanceled(true);
				}
			}
		}
	}
}
