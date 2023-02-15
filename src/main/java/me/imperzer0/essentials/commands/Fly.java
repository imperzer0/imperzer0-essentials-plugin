package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Fly extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
	public static final String NAME = "fly";
	public static final String USAGE = "[ <user> ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public Fly()
	{
		super(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length > 1)
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}

		Player target = null;

		if (args.length == 1) target = PlayerUtils.Bukkit_getPlayer(args[0]);

		if (target == null)
		{
			if (!(sender instanceof Player player))
			{
				loger.invalid_entity(sender);
				return false;
			}
			target = player;
		}

		boolean allow = !target.getAllowFlight();
		target.setAllowFlight(allow);
		loger.message(sender, ChatColor.WHITE + "Flight " + ChatColor.ITALIC +
				(allow ? ChatColor.GREEN + "allowed" : ChatColor.RED + "not allowed"));

		return true;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
									  @NotNull String[] args)
	{
		if (args.length == 0)
			return PlayerUtils.Bukkit_getAllPlayersIdentifiers(null);
		else if (args.length == 1)
			return PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]);
		else return null;
	}
}
