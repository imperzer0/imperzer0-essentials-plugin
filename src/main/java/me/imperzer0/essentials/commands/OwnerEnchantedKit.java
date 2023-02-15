package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.constants.OwnerConstants;
import me.imperzer0.essentials.utils.InventoryUtils;
import me.imperzer0.essentials.utils.MaterialUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class OwnerEnchantedKit extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
	public static final String NAME = "owner_kit";
	public static final String USAGE = "[ menu <amount> ] / [ apply ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public OwnerEnchantedKit()
	{
		super(Objects.requireNonNull(me.imperzer0.essentials.Main.getInstance().getCommand(NAME)), PERMISSION);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length == 0)
			if (sender instanceof HumanEntity human)
			{
				human.getInventory().addItem(OwnerConstants.filter_owner_items_for_inventory());
				human.getInventory().setArmorContents(OwnerConstants.filter_owner_items_for_armor());
				OwnerConstants.apply_owner_effects(human);
				loger.message(sender, ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Gave owner kit to \"" +
						ChatColor.GOLD + human.getName() + ChatColor.DARK_AQUA + "\".");
				return true;
			}
			else loger.invalid_entity(sender);
		else if (args.length == 1 && args[0].equals("apply"))
			if (sender instanceof HumanEntity human)
			{
				ItemStack item = human.getInventory().getItemInMainHand();
				item.addUnsafeEnchantments(OwnerConstants.ENCHANTMENTS);

				loger.message(sender, ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Applied owner kit enchantments to \"" +
						ChatColor.GOLD + human.getName() + ChatColor.DARK_AQUA + "\"'s " +
						ChatColor.LIGHT_PURPLE + item.getType().name().toLowerCase() + ChatColor.DARK_AQUA + ".");
				return true;
			}
			else loger.invalid_entity(sender);
		else if (args.length >= 2 && args[0].equals("menu"))
			if (sender instanceof HumanEntity human)
			{
				int amount;

				try
				{
					amount = Integer.parseInt(args[1]);
				}
				catch (NumberFormatException e)
				{
					amount = 1;
				}

				Inventory inventory = Bukkit.createInventory(
						human, 18, ChatColor.GOLD + "" + ChatColor.BOLD + "OwnerKitInventory");

				inventory.addItem(InventoryUtils.multiply_items(OwnerConstants.owner_items, amount));

				for (int i = 2; i < args.length; ++i)
				{
					Material material = Material.matchMaterial(args[i]);
					if (material != null)
					{
						ItemStack item = new ItemStack(material, amount);
						ItemMeta meta = item.getItemMeta();
						assert meta != null;
						meta.setUnbreakable(true);
						item.setItemMeta(meta);
						item.addUnsafeEnchantments(OwnerConstants.ENCHANTMENTS);
						inventory.addItem(item);
					}
				}

				loger.message(sender, ChatColor.BOLD + "" + ChatColor.DARK_AQUA + "Opening inventory for \"" +
						ChatColor.GOLD + human.getName() + ChatColor.DARK_AQUA + "\"...");

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
		if (args.length <= 1)
		{
			list.add("menu");
			list.add("apply");
		}
		else if (args.length >= 3) list.addAll(MaterialUtils.Material_getAllMaterialsNames(args[args.length - 1]));
		return list;
	}
}
