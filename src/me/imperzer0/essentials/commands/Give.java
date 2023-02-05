package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.CommandUtils;
import me.imperzer0.essentials.utils.MaterialUtils;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Give implements CommandExecutor, TabCompleter
{
	public static final String NAME = "give";
	public static final String USAGE = "[ <user> ] <item> [ <amount> ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public Give()
	{
		CommandUtils.command_initialization(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION, this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length == 1)
		{
			if (!(sender instanceof Player player))
			{
				loger.invalid_entity(sender);
				return false;
			}

			Material material = Material.matchMaterial(args[0]);
			if (material == null)
			{
				loger.error(sender, "Can't match material \"" + args[0] + "\".");
				return false;
			}

			player.getInventory().addItem(new ItemStack(material));

			loger.message(sender, ChatColor.GREEN + "Gave " + ChatColor.LIGHT_PURPLE + material + ChatColor.GREEN +
					" to player \"" + ChatColor.RED + player.getName() + ChatColor.GREEN + "\".");

			return true;
		}
		else if (args.length == 2)
		{
			Player player = PlayerUtils.Bukkit_getPlayer(args[0]);
			int shift = 1;
			int amount = 1;

			if (player == null)
			{
				shift = 0;

				if (!(sender instanceof Player player1))
				{
					loger.invalid_entity(sender);
					return false;
				}

				player = player1;
			}

			Material material = Material.matchMaterial(args[shift]);
			if (material == null)
			{
				loger.error(sender, "Can't match material \"" + args[shift] + "\".");
				return false;
			}

			if (shift == 0)
			{
				String amount_str = args[shift + 1];
				amount = getAmount(sender, args, amount, material, amount_str);
			}

			player.getInventory().addItem(new ItemStack(material, amount));

			loger.message(sender, ChatColor.GREEN + "Gave " + ChatColor.LIGHT_PURPLE + material + ChatColor.GREEN +
					" to player \"" + ChatColor.RED + player.getName() + ChatColor.GREEN + "\".");

			return true;
		}
		else if (args.length == 3)
		{
			Player player = PlayerUtils.Bukkit_getPlayer(args[0], loger, sender);
			int amount = 1;

			if (player == null) return false;

			Material material = Material.matchMaterial(args[1]);
			if (material == null)
			{
				loger.error(sender, "Can't match material \"" + args[1] + "\".");
				return false;
			}

			String amount_str = args[2];
			amount = getAmount(sender, args, amount, material, amount_str);

			player.getInventory().addItem(new ItemStack(material, amount));

			loger.message(sender, ChatColor.GREEN + "Gave " + ChatColor.LIGHT_PURPLE + material +
					ChatColor.GREEN + " to player \"" + ChatColor.RED + player.getName() + ChatColor.GREEN + "\".");

			return true;
		}
		else loger.help(sender, cmd, USAGE);

		return false;
	}

	private int getAmount(@NotNull CommandSender sender, String[] args, int amount, Material material, String amount_str)
	{
		try
		{
			amount = Integer.parseInt(amount_str);
		}
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
					loger.error(sender, "Invalid amount \"" + args[1] + "\".");
				}
			else loger.error(sender, "Invalid amount \"" + args[1] + "\".");
		}
		return amount;
	}

	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
									  @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();

		if (args.length == 1)
		{
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]));
			list.addAll(MaterialUtils.Material_getAllMaterialsNames(args[0]));
		}
		else if (args.length == 2)
		{
			if (Material.matchMaterial(args[0]) == null)
				list.addAll(MaterialUtils.Material_getAllMaterialsNames(args[1]));
			else list.add("stack");

			try
			{
				Integer.parseInt(args[1]);
				list.add(args[1] + "s");
			}
			catch (NumberFormatException e)
			{ /* ignore */ }
		}
		else if (args.length == 3)
		{
			list.add("stack");

			try
			{
				Integer.parseInt(args[2]);
				list.add(args[2] + "s");
			}
			catch (NumberFormatException e)
			{ /* ignore */ }
		}

		return list;
	}
}
