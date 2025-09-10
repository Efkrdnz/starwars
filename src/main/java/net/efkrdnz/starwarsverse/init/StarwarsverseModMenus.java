
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.efkrdnz.starwarsverse.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.core.registries.Registries;

import net.efkrdnz.starwarsverse.world.inventory.MultipurposeWorkbenchGUIMenu;
import net.efkrdnz.starwarsverse.world.inventory.MBKyberInfuserGUIMenu;
import net.efkrdnz.starwarsverse.world.inventory.KyberInfuserGuiMenu;
import net.efkrdnz.starwarsverse.world.inventory.GuiAircraftWorkbenchMenu;
import net.efkrdnz.starwarsverse.world.inventory.ForcemenuguiMenu;
import net.efkrdnz.starwarsverse.StarwarsverseMod;

public class StarwarsverseModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, StarwarsverseMod.MODID);
	public static final DeferredHolder<MenuType<?>, MenuType<KyberInfuserGuiMenu>> KYBER_INFUSER_GUI = REGISTRY.register("kyber_infuser_gui", () -> IMenuTypeExtension.create(KyberInfuserGuiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<ForcemenuguiMenu>> FORCEMENUGUI = REGISTRY.register("forcemenugui", () -> IMenuTypeExtension.create(ForcemenuguiMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<GuiAircraftWorkbenchMenu>> GUI_AIRCRAFT_WORKBENCH = REGISTRY.register("gui_aircraft_workbench", () -> IMenuTypeExtension.create(GuiAircraftWorkbenchMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<MultipurposeWorkbenchGUIMenu>> MULTIPURPOSE_WORKBENCH_GUI = REGISTRY.register("multipurpose_workbench_gui", () -> IMenuTypeExtension.create(MultipurposeWorkbenchGUIMenu::new));
	public static final DeferredHolder<MenuType<?>, MenuType<MBKyberInfuserGUIMenu>> MB_KYBER_INFUSER_GUI = REGISTRY.register("mb_kyber_infuser_gui", () -> IMenuTypeExtension.create(MBKyberInfuserGUIMenu::new));
}
