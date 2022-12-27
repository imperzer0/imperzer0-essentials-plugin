package me.imperzer0.essentials.utils;

import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import static me.imperzer0.essentials.utils.Loger.loger;

public class CommandUtils
{
	public static boolean initial_command_assertion(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String[] args,
													@NotNull final String PERMISSION, @NotNull final String USAGE)
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

	public static void command_initialization(@NotNull PluginCommand command, @NotNull String PERMISSION,
											  @NotNull CommandExecutor executor)
	{
		command.setExecutor(executor);
		Bukkit.getPluginManager().addPermission(new Permission(PERMISSION, command.getDescription(), PermissionDefault.FALSE));
		Main.getInstance().getLogger().info("Loaded command \"" + command.getName() + "\".");
	}
}
