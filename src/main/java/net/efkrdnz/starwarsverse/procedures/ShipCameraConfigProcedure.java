package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;

public class ShipCameraConfigProcedure {
	// camera configuration values
	public static final int SHIP_FOV = 110; // max stable fov without twitching
	public static final int NORMAL_FOV = 70; // normal minecraft fov

	@OnlyIn(Dist.CLIENT)
	public static void applyShipCameraSettings() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null) {
			// set ship camera settings
			mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
			mc.options.fov().set(SHIP_FOV);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void restoreNormalCameraSettings() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null) {
			// restore normal settings
			mc.options.setCameraType(CameraType.FIRST_PERSON);
			mc.options.fov().set(NORMAL_FOV);
		}
	}

	@OnlyIn(Dist.CLIENT)
	public static void setCustomFOV(int fov) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null) {
			// set custom fov (use carefully to avoid twitching)
			mc.options.fov().set(Math.min(fov, 110)); // cap at 110 for stability
		}
	}
}
