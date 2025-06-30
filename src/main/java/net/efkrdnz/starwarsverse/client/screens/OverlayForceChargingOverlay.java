
package net.efkrdnz.starwarsverse.client.screens;

import org.checkerframework.checker.units.qual.h;

import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.client.Minecraft;

import net.efkrdnz.starwarsverse.procedures.ReturnUsingForceNameProcedure;
import net.efkrdnz.starwarsverse.procedures.ReturnUsingForceChargeRateProcedure;
import net.efkrdnz.starwarsverse.procedures.ReturnForceGrabTargetProcedure;
import net.efkrdnz.starwarsverse.procedures.ReturnForceGrabDistanceProcedure;
import net.efkrdnz.starwarsverse.procedures.IsUsingForceProcedure;

@EventBusSubscriber({Dist.CLIENT})
public class OverlayForceChargingOverlay {
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public static void eventHandler(RenderGuiEvent.Pre event) {
		int w = event.getGuiGraphics().guiWidth();
		int h = event.getGuiGraphics().guiHeight();
		Level world = null;
		double x = 0;
		double y = 0;
		double z = 0;
		Player entity = Minecraft.getInstance().player;
		if (entity != null) {
			world = entity.level();
			x = entity.getX();
			y = entity.getY();
			z = entity.getZ();
		}
		if (IsUsingForceProcedure.execute(entity)) {
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					ReturnUsingForceNameProcedure.execute(entity), 6, 26, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					ReturnUsingForceChargeRateProcedure.execute(entity), 6, 35, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					ReturnForceGrabTargetProcedure.execute(world, entity), 6, 44, -1, false);
			event.getGuiGraphics().drawString(Minecraft.getInstance().font,

					ReturnForceGrabDistanceProcedure.execute(entity), 6, 53, -1, false);
		}
	}
}
