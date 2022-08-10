package com.imperzer0.essentials.commands;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.constants.OwnerConstants;
import com.imperzer0.essentials.utils.InventoryUtils;
import com.imperzer0.essentials.utils.Loger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OwnerEnchantedKit implements CommandExecutor, TabCompleter
{
	
	private static final String NAME = "owner_kit";
	private static final String USAGE = "[ menu <amount> ]";
	private static final String PERMISSION = "imperzer0-essentials.command.owner_kit";
	
	public final Main plugin;
	private final Loger loger;
	
	public OwnerEnchantedKit(@NotNull Loger loger)
	{
		this.loger = loger;
		plugin = loger.plugin;
		Objects.requireNonNull(plugin.getCommand(NAME)).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (!sender.hasPermission(PERMISSION))
		{
			loger.no_permissions(sender);
			return false;
		}
		
		if (args.length == 0)
			if (sender instanceof HumanEntity human)
			{
				human.getInventory().addItem(OwnerConstants.filter_owner_items_for_inventory());
				human.getInventory().setArmorContents(OwnerConstants.filter_owner_items_for_armor());
				OwnerConstants.apply_owner_effects(human);
				return true;
			}
			else loger.invalid_entity(sender);
		else if (args.length == 2 && args[0].equals("menu"))
			if (sender instanceof HumanEntity human)
			{
				int amount;
				
				try { amount = Integer.parseInt(args[1]); }
				catch (NumberFormatException e) { amount = 1; }
				
				Inventory inventory = Bukkit.createInventory(
						human, 18, ChatColor.GOLD + "" + ChatColor.BOLD + "OwnerKitInventory");
				
				inventory.addItem(InventoryUtils.multiply_items(OwnerConstants.owner_items, amount));
				
				human.openInventory(inventory);
				
				return true;
			}
			else loger.invalid_entity(sender);
		else loger.help(sender, cmd, USAGE);
		
		return false;
	}
	
	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
	                                  @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length <= 1) list.add("menu");
		return list;
	}
}
