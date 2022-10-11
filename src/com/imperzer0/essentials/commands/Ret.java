package com.imperzer0.essentials.commands;

import com.imperzer0.essentials.listeners.RetListener;
import com.imperzer0.essentials.utils.CommandUtils;
import com.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static com.imperzer0.essentials.Main.plugin;
import static com.imperzer0.essentials.utils.Loger.loger;

public class Ret implements CommandExecutor, TabCompleter
{
	public static final String NAME = "ret";
	public static final String USAGE = "";
	public static final String PERMISSION = "imperzer0-essentials.command.ret";
	
	public Ret()
	{
		CommandUtils.command_initialization(Objects.requireNonNull(plugin.getCommand(NAME)), PERMISSION, this);
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE)) return false;
		
		if (args.length == 0)
		{
			if (!(sender instanceof Player player))
			{
				loger.invalid_entity(sender);
				return false;
			}
			
			Location location = RetListener.get_death_location(player.getUniqueId());
			if (location != null)
			{
				player.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
				loger.message(sender, ChatColor.GRAY + "Teleported to " +
				                      ChatColor.ITALIC + ChatColor.LIGHT_PURPLE + location);
				return true;
			}
			else
			{
				loger.error(sender, ChatColor.ITALIC + "Die first!!!");
				return false;
			}
		}
		else
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}
	}
	
	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
	                                  @NotNull String[] args)
	{
		if (args.length == 0)
			return PlayerUtils.Bukkit_getAllPlayersIdentifiers(null);
		else if (args.length == 1)
			return PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]);
		else return null;
	}
}
