package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class IsNotLightsaberProcedure {
	public static boolean execute(ItemStack itemstack) {
		if (!itemstack.is(ItemTags.create(ResourceLocation.parse("lightsaber")))) {
			return true;
		}
		return false;
	}
}
