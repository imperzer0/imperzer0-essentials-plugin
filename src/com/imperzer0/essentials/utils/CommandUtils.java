package com.imperzer0.essentials.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandUtils
{
	public static boolean initial_command_assertion(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String[] args,
	                                                @NotNull final String PERMISSION, @NotNull final String USAGE,
	                                                @NotNull Loger loger)
	{
		if (!sender.hasPermission(PERMISSION))
		{
			loger.no_permissions(sender);
			return true;
		}
		
		if (args.length == 1 && args[0].equals("help"))
		{
			loger.help(sender, cmd, USAGE);
			return true;
		}
		
		return false;
	}
}
