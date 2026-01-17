package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.listeners.BoostMinecartListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Main extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
	public static final String NAME = "essentials";
	public static final String USAGE = "[ reload ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public Main()
	{
		super(Objects.requireNonNull(me.imperzer0.essentials.Main.getInstance().getCommand(NAME)), PERMISSION);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length == 1 && args[0].equalsIgnoreCase("reload"))
		{
			PluginDescriptionFile pdFile = me.imperzer0.essentials.Main.getInstance().getDescription();
			sender.sendMessage("Reloading " + pdFile.getName() + " version " + pdFile.getVersion() + " ...");

			me.imperzer0.essentials.Main.getInstance().reloadConfig();

			BoostMinecartListener.load_config();
			loger.message(sender, "[BoostMinecart] Speed:    " + BoostMinecartListener.get_speed());
			loger.message(sender, "[BoostMinecart] Carts:    " + BoostMinecartListener.get_carts());

			loger.message(sender, ChatColor.GREEN + "[" + pdFile.getName() + "] Config reloaded.");
		}
		else
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}

		return true;
	}


	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		ArrayList<String> list = new ArrayList<>();
		list.add("reload");
		return list;
	}
}
