package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.CommandUtils;
import me.imperzer0.essentials.utils.DeadInventoryUtils;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class DeadInventory implements CommandExecutor, TabCompleter
{
	public static final String NAME = "dead_inventory";
	public static final String USAGE = ChatColor.GREEN + "[ <player> ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public DeadInventory()
	{
		CommandUtils.command_initialization(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION, this);
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

			player.openInventory(DeadInventoryUtils.open_dead_inventory(player.getUniqueId()));
		}
		else if (args.length == 1)
		{
			if (!(sender instanceof Player player))
			{
				loger.invalid_entity(sender);
				return false;
			}

			OfflinePlayer target = PlayerUtils.Bukkit_getOfflinePlayer(args[0], loger, sender);

			if (target == null) return false;

			player.openInventory(DeadInventoryUtils.open_dead_inventory(target.getUniqueId()));
		}

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 1)
		{
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]));
		}
		return list;
	}
}
