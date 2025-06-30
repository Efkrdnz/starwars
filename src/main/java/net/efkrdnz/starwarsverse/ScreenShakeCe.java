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

import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.procedures.ScreenShakeProcedureProcedure;
import net.efkrdnz.starwarsverse.procedures.ScreenShakeInsensityProcedure;

@Mod("starwarsverse")
public class ScreenShakeCe {
	public ScreenShakeCe() {
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
		@SubscribeEvent
		public static void onCameraShake(ViewportEvent.ComputeCameraAngles event) {
			LocalPlayer player = Minecraft.getInstance().player;
			if (player == null)
				return;
			if (ScreenShakeProcedureProcedure.execute(player)) {
				double shakeIntensity = ScreenShakeInsensityProcedure.execute(player);
				if (Math.random() < 0.5) {
					event.setPitch((float) (event.getPitch() + (Math.random() * shakeIntensity)));
					event.setRoll((float) (event.getRoll() + (Math.random() * shakeIntensity)));
					event.setYaw((float) (event.getYaw() + (Math.random() * shakeIntensity)));
				} else {
					event.setPitch((float) (event.getPitch() - (Math.random() * shakeIntensity)));
					event.setRoll((float) (event.getRoll() - (Math.random() * shakeIntensity)));
					event.setYaw((float) (event.getYaw() - (Math.random() * shakeIntensity)));
				}
			}
		}
	}
}
