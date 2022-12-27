package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.CommandUtils;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class GetUUID implements CommandExecutor, TabCompleter
{
	public static final String NAME = "uuid";
	public static final String USAGE = "<user>";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public GetUUID()
	{
		CommandUtils.command_initialization(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION, this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length == 1)
			loger.message(sender, "" + ChatColor.DARK_PURPLE + ChatColor.BOLD + ChatColor.ITALIC +
					Objects.requireNonNull(PlayerUtils.Bukkit_getOfflinePlayer(args[0], loger, sender)).getUniqueId());
		else loger.help(sender, cmd, USAGE);

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
									  @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 0)
			list.addAll(PlayerUtils.Bukkit_getAllPlayersNames(null));
		else if (args.length == 1)
			list.addAll(PlayerUtils.Bukkit_getAllPlayersNames(args[0]));
		return list;
	}
}
