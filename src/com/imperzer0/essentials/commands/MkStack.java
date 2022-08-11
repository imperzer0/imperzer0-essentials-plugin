package com.imperzer0.essentials.commands;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.utils.CommandUtils;
import com.imperzer0.essentials.utils.Loger;
import com.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MkStack implements CommandExecutor, TabCompleter
{
	
	private static final String NAME = "mkstack";
	private static final String USAGE = "[ <user> ]";
	private static final String PERMISSION = "imperzer0-essentials.command.mkstack";
	
	public final Main plugin;
	private final Loger loger;
	
	public MkStack(@NotNull Loger loger)
	{
		this.loger = loger;
		plugin = loger.plugin;
		Objects.requireNonNull(plugin.getCommand(NAME)).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE, loger)) return false;
		
		HumanEntity human = null;
		
		if (args.length == 0)
			if (sender instanceof HumanEntity) human = (HumanEntity)sender;
			else loger.invalid_entity(sender);
		else if (args.length == 1)
		{
			human = PlayerUtils.Bukkit_getPlayer(args[0]);
		}
		else
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}
		
		if (human == null)
		{
			loger.error(sender, "Invalid player name: '" + args[0] + "'.");
			return false;
		}
		
		ItemStack stack = human.getInventory().getItemInMainHand();
		stack.setAmount(stack.getMaxStackSize());
		
		loger.message(sender, ChatColor.GRAY + "Refilled \"" + ChatColor.YELLOW + human.getName() +
		                      ChatColor.GRAY + "\"'s stack of '" +
		                      ChatColor.LIGHT_PURPLE + stack.getType().name().toLowerCase() + ChatColor.GRAY + "'.");
		
		return true;
	}
	
	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
	                                  @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 0)
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(null));
		else if (args.length == 1)
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]));
		return list;
	}
}
