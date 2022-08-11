package com.imperzer0.essentials.commands;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.utils.BagUtils;
import com.imperzer0.essentials.utils.CommandUtils;
import com.imperzer0.essentials.utils.Loger;
import com.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bag implements CommandExecutor, TabCompleter
{
	public static final String NAME = "bag";
	public static final String USAGE = "";
	public static final String PERMISSION = "imperzer0-essentials.command.bag";
	public static final String PERMISSION_STEAL = "imperzer0-essentials.command.bag_steal";
	public static final String PERMISSION_CLEAR = "imperzer0-essentials.command.clear.";
	
	public final Main plugin;
	private final Loger loger;
	
	public Bag(@NotNull Loger loger)
	{
		this.loger = loger;
		plugin = loger.plugin;
		Objects.requireNonNull(plugin.getCommand(NAME)).setExecutor(this);
		Bukkit.getServer().getPluginManager().addPermission(
				new Permission(PERMISSION_CLEAR + "all", "Clear all users bags", PermissionDefault.FALSE));
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE, loger)) return false;
		
		if (!(sender instanceof HumanEntity human))
		{
			loger.invalid_entity(sender);
			return false;
		}
		
		if (args.length == 0)
		{
			loger.message(sender, ChatColor.GRAY + "Opening bag...");
			human.openInventory(BagUtils.open_bag(plugin, human.getUniqueId()));
		}
		else if (args.length == 1 && sender.hasPermission(PERMISSION_STEAL))
		{
			OfflinePlayer player = PlayerUtils.Bukkit_getOfflinePlayer(args[0], loger, sender);
			if (player == null) return false;
			loger.message(sender, ChatColor.GRAY + "Opening \"" +
			                      ChatColor.GOLD + player.getName() + ChatColor.GRAY + "\"'s...");
			human.openInventory(BagUtils.open_bag(plugin, player.getUniqueId()));
		}
		else if (args.length == 2 && args[0].equals("clear"))
		{
			if (args[1].equals("ALL"))
			{
				for (OfflinePlayer player : Bukkit.getOfflinePlayers())
				{
					loger.message(sender, ChatColor.GRAY + "Cleaning \"" +
					                      ChatColor.GOLD + player.getName() + ChatColor.GRAY + "\"'s bag...");
					BagUtils.clear_bag(plugin, sender, player.getUniqueId());
				}
			}
			else
			{
				OfflinePlayer player = PlayerUtils.Bukkit_getOfflinePlayer(args[0], loger, sender);
				if (player == null) return false;
				loger.message(sender, ChatColor.GRAY + "Cleaning \"" +
				                      ChatColor.GOLD + player.getName() + ChatColor.GRAY + "\"'s bag...");
				BagUtils.clear_bag(plugin, sender, player.getUniqueId());
			}
		}
		else loger.help(sender, cmd, USAGE);
		
		return false;
	}
	
	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
	                                  @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 0)
		{
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(null));
			list.add("clear");
		}
		else if (args.length == 1)
		{
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]));
			list.add("clear");
		}
		else if (args.length == 2)
		{
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[1]));
			list.add("ALL");
		}
		return list;
	}
}
