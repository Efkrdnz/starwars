package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.item.ItemStack;

import net.efkrdnz.starwarsverse.init.StarwarsverseModItems;

public class IsNotKyberProcedure {
	public static boolean execute(ItemStack itemstack) {
		if (!(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_RED.get()) && !(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_BLUE.get()) && !(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_GREEN.get())
				&& !(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_WHITE.get()) && !(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_YELLOW.get()) && !(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_ORANGE.get())
				&& !(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_PURPLE.get()) && !(itemstack.getItem() == StarwarsverseModItems.KYBER_CRYSTAL_BLACK.get())) {
			return true;
		}
		return false;
	}
}
