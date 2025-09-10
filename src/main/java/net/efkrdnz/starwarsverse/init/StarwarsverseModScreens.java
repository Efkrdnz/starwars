
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.efkrdnz.starwarsverse.client.gui.MultipurposeWorkbenchGUIScreen;
import net.efkrdnz.starwarsverse.client.gui.MBKyberInfuserGUIScreen;
import net.efkrdnz.starwarsverse.client.gui.KyberInfuserGuiScreen;
import net.efkrdnz.starwarsverse.client.gui.GuiAircraftWorkbenchScreen;
import net.efkrdnz.starwarsverse.client.gui.ForcemenuguiScreen;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StarwarsverseModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(StarwarsverseModMenus.KYBER_INFUSER_GUI.get(), KyberInfuserGuiScreen::new);
		event.register(StarwarsverseModMenus.FORCEMENUGUI.get(), ForcemenuguiScreen::new);
		event.register(StarwarsverseModMenus.GUI_AIRCRAFT_WORKBENCH.get(), GuiAircraftWorkbenchScreen::new);
		event.register(StarwarsverseModMenus.MULTIPURPOSE_WORKBENCH_GUI.get(), MultipurposeWorkbenchGUIScreen::new);
		event.register(StarwarsverseModMenus.MB_KYBER_INFUSER_GUI.get(), MBKyberInfuserGUIScreen::new);
	}
}
