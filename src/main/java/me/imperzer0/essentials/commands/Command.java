package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import static me.imperzer0.essentials.utils.Loger.loger;

public abstract class Command implements CommandExecutor
{
	protected Command(@NotNull PluginCommand command, @NotNull String PERMISSION)
	{
		command.setExecutor(this);
		Bukkit.getPluginManager().addPermission(new Permission(PERMISSION, command.getDescription(), PermissionDefault.FALSE));
		Main.getInstance().getLogger().info("Loaded \"" + command.getName() + "\" command handler [" + getClass().getName() + "].");
	}

	protected boolean init_command(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String[] args,
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
}
