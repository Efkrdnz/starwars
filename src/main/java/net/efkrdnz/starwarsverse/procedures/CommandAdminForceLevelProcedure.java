package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class CommandAdminForceLevelProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "target")) {
				{
					StarwarsverseModVariables.PlayerVariables _vars = entityiterator.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.force_level = DoubleArgumentType.getDouble(arguments, "number");
					_vars.syncPlayerVariables(entityiterator);
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}
