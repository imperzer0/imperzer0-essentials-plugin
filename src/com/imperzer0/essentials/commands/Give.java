package com.imperzer0.essentials.commands;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.utils.Loger;
import com.imperzer0.essentials.utils.MaterialUtils;
import com.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Give implements CommandExecutor, TabCompleter
{
	public static final String NAME = "give";
	public static final String USAGE = "<user> <item> [ <amount> <enchantments> ]";
	public static final String PERMISSION = "imperzer0-essentials.command.give";
	public final Main plugin;
	private final Loger loger;
	
	public Give(@NotNull Loger loger)
	{
		this.plugin = loger.plugin;
		this.loger = loger;
		Objects.requireNonNull(plugin.getCommand(NAME)).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
	{
		if (!sender.hasPermission(PERMISSION))
		{
			loger.no_permissions(sender);
			return false;
		}
		
		if (args.length == 1)
		{
			if (!(sender instanceof InventoryHolder holder))
			{
				loger.invalid_entity(sender);
				return false;
			}
			
			Material material = Material.matchMaterial(args[0]);
			if (material == null)
			{
				loger.error(sender, "Can't match material '" + args[0] + "'");
				return false;
			}
			
			holder.getInventory().addItem(new ItemStack(material));
		}
		else if (args.length == 2)
		{
			InventoryHolder holder = PlayerUtils.Bukkit_getPlayer(args[0]);
			int shift = 1;
			int amount = 1;
			
			if (holder == null)
			{
				shift = 0;
				
				if (!(sender instanceof InventoryHolder invholder))
				{
					loger.invalid_entity(sender);
					return false;
				}
				
				holder = invholder;
			}
			
			Material material = Material.matchMaterial(args[shift]);
			if (material == null)
			{
				loger.error(sender, "Can't match material '" + args[shift] + "'");
				return false;
			}
			
			if (shift == 0)
			{
				String amount_str = args[shift + 1];
				amount = getAmount(sender, args, amount, material, amount_str);
			}
			
			holder.getInventory().addItem(new ItemStack(material, amount));
			
			return true;
		}
		else if (args.length == 3)
		{
			InventoryHolder holder = PlayerUtils.Bukkit_getPlayer(args[0], loger, sender);
			int amount = 1;
			
			if (holder == null) return false;
			
			Material material = Material.matchMaterial(args[1]);
			if (material == null)
			{
				loger.error(sender, "Can't match material '" + args[1] + "'");
				return false;
			}
			
			String amount_str = args[2];
			amount = getAmount(sender, args, amount, material, amount_str);
			
			holder.getInventory().addItem(new ItemStack(material, amount));
			return true;
		}
		else loger.help(sender, cmd, USAGE);
		
		return false;
	}
	
	private int getAmount(@NotNull CommandSender sender, String[] args, int amount, Material material, String amount_str)
	{
		try { amount = Integer.parseInt(amount_str); }
		catch (NumberFormatException e)
		{
			if (Objects.equals(amount_str, "stack"))
				amount = material.getMaxStackSize();
			else if (amount_str.endsWith("s"))
				try
				{
					amount = Integer.parseInt(amount_str.substring(0, amount_str.length() - "s".length()));
					amount *= material.getMaxStackSize();
				}
				catch (NumberFormatException ex)
				{
					loger.error(sender, "Invalid amount '" + args[1] + "'");
				}
			else loger.error(sender, "Invalid amount '" + args[1] + "'");
		}
		return amount;
	}
	
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
	                                  String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		
		if (args.length == 1)
		{
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]));
			list.addAll(MaterialUtils.Material_getAllMaterialsNames(args[0]));
		}
		else if (args.length == 2)
		{
			if (Material.getMaterial(args[0]) == null) list.addAll(MaterialUtils.Material_getAllMaterialsNames(args[0]));
			else list.add("stack");
			
			try
			{
				Integer.parseInt(args[1]);
				list.add(args[1] + "s");
			}
			catch (NumberFormatException e) { /* ignore */ }
		}
		else if (args.length == 3)
		{
			list.add("stack");
			
			try
			{
				Integer.parseInt(args[1]);
				list.add(args[1] + "s");
			}
			catch (NumberFormatException e) { /* ignore */ }
		}
		
		return list;
	}
}
