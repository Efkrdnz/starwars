package net.efkrdnz.starwarsverse.procedures;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.entity.Entity;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.CameraType;

public class ForceThirdPersonProcedure {
	@OnlyIn(Dist.CLIENT)
	public static void execute(Entity entity) {
		if (entity == null || !(entity instanceof LocalPlayer player)) {
			return;
		}
		Minecraft mc = Minecraft.getInstance();
		// check if player is riding a ship using existing procedure
		if (ReturnRidingAircraftProcedure.execute(player) != null) {
			// force third person back camera
			if (mc.options.getCameraType() != CameraType.THIRD_PERSON_BACK) {
				mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);
			}
		}
	}
}
