package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.InvseeUtils;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Invsee extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
	public static final String NAME = "invsee";
	public static final String USAGE = "<user_uuid>";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;


	public Invsee()
	{
		super(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION);
		InvseeUtils.load_archive();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (!(sender instanceof Player sender_p))
		{
			loger.invalid_entity(sender);
			return false;
		}

		if (args.length != 1 && args.length != 2)
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}

		{
			Player player = PlayerUtils.Bukkit_getPlayer(args[0]);

			if (player == sender_p)
			{

				loger.error(sender, ChatColor.BOLD + "You can't open your inventory. "
						+ ChatColor.RESET + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + player.getDisplayName()
						+ ChatColor.RESET + ChatColor.BOLD + ChatColor.RED + " is your name!");
				return false;
			}

			if (player != null)
			{
				if (args.length == 1)
				{
					sender_p.openInventory(player.getInventory());
				}
				else
				{
					if (args[1].equalsIgnoreCase("armor"))
					{
						Inventory inventory = InvseeUtils.load_armor(player);
						sender_p.openInventory(inventory);
					}
					else if (args[1].equalsIgnoreCase("inv"))
					{
						sender_p.openInventory(player.getInventory());
					}
					else
					{
						loger.help(sender, cmd, USAGE);
						return false;
					}

					loger.message(sender, ChatColor.GREEN + "" + ChatColor.BOLD + "Opened "
							+ ChatColor.RESET + ChatColor.LIGHT_PURPLE + player.getDisplayName()
							+ ChatColor.RESET + ChatColor.GREEN + ChatColor.BOLD + "'s inventory");
				}

				return true;
			}
		}

		{
			if (args.length != 1)
			{
				loger.help(sender, cmd, USAGE);
				return false;
			}

			OfflinePlayer player = PlayerUtils.Bukkit_getOfflinePlayer(args[0]);
			if (player == null)
			{
				loger.error(sender, "Player \"" + args[0] + "\" not found.");
				return false;
			}


			loger.message(sender, ChatColor.RED + "" + ChatColor.YELLOW + "Player "
					+ ChatColor.RESET + ChatColor.BOLD + ChatColor.LIGHT_PURPLE + args[0]
					+ ChatColor.RESET + ChatColor.YELLOW + " isn't online\n"
					+ ChatColor.GREEN + "Opening archive...");
			try
			{
				sender_p.openInventory(InvseeUtils.get_archived_inventory(player));
				InvseeUtils.add_current_archive_viewer(player, sender_p);
			}
			catch (Exception e)
			{
				loger.error(sender, ChatColor.BOLD + "This inventory does not exist in archive.");
				return false;
			}

			return true;
		}
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length <= 1)
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]));
		else if (args.length == 2)
		{
			list.add("armor");
			list.add("ARMOR");
			list.add("inv");
			list.add("INV");
		}
		return list;
	}
}
