package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.BoostLibrarianUtils;
import me.imperzer0.essentials.utils.CommandUtils;
import me.imperzer0.essentials.utils.EnchantmentUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class BoostLibrarian implements CommandExecutor, TabCompleter
{
	public static final String NAME = "boost_librarian";
	public static final String USAGE = "<enchantment> <level>";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

	public BoostLibrarian()
	{
		CommandUtils.command_initialization(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION, this);
	}


	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (CommandUtils.initial_command_assertion(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length == 2)
		{
			Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(args[0].toLowerCase()));
			if (enchantment == null)
			{
				loger.error(sender, "Invalid enchantment \"" + args[0] + "\"");
				return false;
			}

			int level;
			try
			{
				level = Integer.parseInt(args[1]);
			}
			catch (NumberFormatException e)
			{
				loger.error(sender, "Invalid enchantment level \"" + args[1] + "\"");
				return false;
			}

			BoostLibrarianUtils.set_next_enchantment(enchantment);
			BoostLibrarianUtils.set_next_enchantment_level(level);

			loger.message(sender, ChatColor.GREEN + "Trade scheduled.");
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

		if (args.length == 1)
		{
			list.addAll(EnchantmentUtils.Enchantment_getAllEnchantmentsNames(args[0]));
		}

		return list;
	}
}
