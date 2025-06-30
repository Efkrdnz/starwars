package net.efkrdnz.starwarsverse.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.commands.CommandSourceStack;

import net.efkrdnz.starwarsverse.network.StarwarsverseModVariables;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class CommandForceAreaIndicatorToggleProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments, Entity entity) {
		if (entity == null)
			return;
		{
			StarwarsverseModVariables.PlayerVariables _vars = entity.getData(StarwarsverseModVariables.PLAYER_VARIABLES);
			_vars.is_using_force = BoolArgumentType.getBool(arguments, "toggle");
			_vars.syncPlayerVariables(entity);
		}
	}
}
