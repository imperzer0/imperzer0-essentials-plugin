package com.imperzer0.essentials.commands;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.utils.CommandUtils;
import com.imperzer0.essentials.utils.GameModeUtils;
import com.imperzer0.essentials.utils.Loger;
import com.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMode implements CommandExecutor, TabCompleter
{
	
	private static final String NAME = "gamemode";
	private static final String USAGE = "<gamemode> [ <user> ]";
	private static final String PERMISSION = "imperzer0-essentials.command.gamemode";
	
	public final Main plugin;
	private final Loger loger;
	
	public GameMode(@NotNull Loger loger)
	{
		this.loger = loger;
		plugin = loger.plugin;
		Objects.requireNonNull(plugin.getCommand(NAME)).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE, loger)) return false;
		
		if (args.length > 2 || args.length == 0)
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}
		
		HumanEntity human;
		if (args.length == 2)
			human = PlayerUtils.Bukkit_getPlayer(args[1], loger, sender);
		else if (sender instanceof HumanEntity)
			human = (HumanEntity)sender;
		else
		{
			loger.invalid_entity(sender);
			return false;
		}
		
		if (human == null) return false;
		
		org.bukkit.GameMode gm;
		try
		{
			gm = org.bukkit.GameMode.valueOf(args[0]);
		}
		catch (IllegalArgumentException e)
		{
			int val;
			try { val = Integer.parseInt(args[0]); }
			catch (NumberFormatException ex)
			{
				loger.error(sender, "Invalid GameMode value: '" + args[0] + "'.");
				return false;
			}
			
			gm = org.bukkit.GameMode.getByValue(val);
		}
		
		if (gm == null)
		{
			loger.error(sender, "Invalid GameMode value: '" + args[0] + "'.");
			return false;
		}
		
		human.setGameMode(gm);
		
		loger.message(sender, ChatColor.GRAY + "Changed \"" + ChatColor.YELLOW + human.getName() +
		                      ChatColor.GRAY + "\"'s GameMode to '" +
		                      ChatColor.LIGHT_PURPLE + human.getGameMode().toString().toLowerCase() + ChatColor.GRAY + "'.");
		
		return true;
	}
	
	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
	                                  @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 0 || args.length == 1)
			list.addAll(GameModeUtils.GameMode_list_all());
		else if (args.length == 2)
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[1]));
		return list;
	}
}
