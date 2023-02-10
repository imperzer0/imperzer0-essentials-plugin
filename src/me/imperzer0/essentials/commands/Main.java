package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.listeners.BoostMinecartListener;
import me.imperzer0.essentials.utils.CommandUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Main implements CommandExecutor, TabCompleter
{
	public static final String NAME = "essentials";
	public static final String USAGE = "[ reload ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public Main()
	{
		CommandUtils.command_initialization(Objects.requireNonNull(me.imperzer0.essentials.Main.getInstance().getCommand(NAME)), PERMISSION, this);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
		{
			PluginDescriptionFile pdfFile = me.imperzer0.essentials.Main.getInstance().getDescription();
			sender.sendMessage("Reloading " + pdfFile.getName() + " version " + pdfFile.getVersion() + " ...");

			me.imperzer0.essentials.Main.getInstance().reloadConfig();

			BoostMinecartListener.load_config();
			loger.message(sender, "[BoostMinecart] Speed:    " + BoostMinecartListener.get_speed());
			loger.message(sender, "[BoostMinecart] Momentum: " + BoostMinecartListener.get_momentum());
			loger.message(sender, "[BoostMinecart] Carts:    " + BoostMinecartListener.get_carts());

			loger.message(sender, ChatColor.GREEN + "[" + pdfFile.getName() + "] Config reloaded.");
		}
		else
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}

		return true;
	}


	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		list.add("reload");
		return list;
	}
}
