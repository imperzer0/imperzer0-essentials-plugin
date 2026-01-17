package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.GameModeUtils;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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

import static me.imperzer0.essentials.utils.Loger.loger;

public class Gamemode extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
	public static final String NAME = "gamemode";
	public static final String USAGE = "<gamemode> [ <user> ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public Gamemode()
	{
		super(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length > 2 || args.length == 0)
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}

		HumanEntity human;
		if (args.length == 2)
			human = PlayerUtils.Bukkit_getPlayer(args[1], loger, sender);
		else if (sender instanceof HumanEntity)
			human = (HumanEntity) sender;
		else
		{
			loger.invalid_entity(sender);
			return false;
		}

		if (human == null) return false;

		GameMode gm = GameModeUtils.parse_gamemode(args[0]);

		if (gm == null)
		{
			loger.error(sender, "Invalid GameMode value: " + ChatColor.GRAY + args[0]);
			return false;
		}

		human.setGameMode(gm);

		loger.message(sender, ChatColor.GRAY + "Changed " + ChatColor.YELLOW + human.getName() +
				ChatColor.GRAY + "'s GameMode to " + ChatColor.LIGHT_PURPLE + human.getGameMode().toString().toLowerCase());

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
