package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class CommandAdminForceToggleProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "target")) {
				{
					StarwarsverseModVariables.PlayerVariables _vars = entityiterator.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
					_vars.can_use_force = BoolArgumentType.getBool(arguments, "bool");
					_vars.syncPlayerVariables(entityiterator);
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}
