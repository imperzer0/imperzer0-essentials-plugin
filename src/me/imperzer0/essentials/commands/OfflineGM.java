package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.CommandUtils;
import me.imperzer0.essentials.utils.GameModeUtils;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static me.imperzer0.essentials.utils.Loger.loger;

public class OfflineGM implements CommandExecutor, TabCompleter
{
	public static final String NAME = "offlinegm";
	public static final String USAGE = "<gamemode> <user_uuid>";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public static Map<UUID, String> gamemodes = new HashMap<>();

	public OfflineGM()
	{
		load_config_vals();
		CommandUtils.command_initialization(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION, this);
	}

	private static void load_config_vals()
	{
		for (OfflinePlayer p : Bukkit.getOfflinePlayers())
		{
			String gm = Main.getInstance().get_offline_gamemodes_config().getString("gamemode." + p.getUniqueId(), null);
			gamemodes.put(p.getUniqueId(), gm);
		}
	}

	public static void save_config_vals()
	{
		for (OfflinePlayer p : Bukkit.getOfflinePlayers())
		{
			String gm = gamemodes.get(p.getUniqueId());
			Main.getInstance().get_offline_gamemodes_config().set("gamemode." + p.getUniqueId(), gm);
		}
		Main.getInstance().saveConfig();
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length != 2)
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}

		Player player = PlayerUtils.Bukkit_getPlayer(args[1]);
		if (player != null)
		{
			GameMode gm = GameModeUtils.parse_gamemode(args[0]);
			if (gm == null)
			{
				loger.error(sender, "Invalid GameMode value: \"" + args[0] + "\".");
				return false;
			}

			player.setGameMode(gm);

			loger.message(sender, ChatColor.GRAY + "Changed \"" + ChatColor.YELLOW + player.getName() +
					ChatColor.GRAY + "\"'s GameMode to \"" +
					ChatColor.LIGHT_PURPLE + player.getGameMode().toString().toLowerCase() + ChatColor.GRAY + "\".");
			return true;
		}

		OfflinePlayer offline_player = PlayerUtils.Bukkit_getOfflinePlayer(args[1], loger, sender);
		if (offline_player != null)
		{
			GameMode gm = GameModeUtils.parse_gamemode(args[0]);
			if (gm == null)
			{
				loger.error(sender, "Invalid GameMode value: \"" + args[0] + "\".");
				return false;
			}

			Main.getInstance().get_offline_gamemodes_config().set("gamemode." + offline_player.getUniqueId(), gm.name());
			Main.getInstance().saveConfig();
			return true;
		}

		loger.error(sender, "Player \"" + args[1] + "\" not found.");
		return false;
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		if (args.length == 0 || args.length == 1)
			list.addAll(GameModeUtils.GameMode_list_all());
		else if (args.length == 2)
			list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[1]));
		return list;
	}
}
