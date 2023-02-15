package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Ench extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
	public static final String NAME = "ench";
	public static final String USAGE = ChatColor.GREEN + "<player>" + ChatColor.BLUE + " [ <level> ] " + ChatColor.YELLOW +
			"[ wipe / copy / paste ] [ list " + ChatColor.GREEN + "<list ...>" + ChatColor.YELLOW + " ]";
	public static final String PERMISSION = "imperzer0-essentials.command." + NAME;


	private Map<Enchantment, Integer> copied = null;


	public Ench()
	{
		super(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION);
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

		if (args.length < 1)
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}

		Player player = PlayerUtils.Bukkit_getPlayer(args[0], loger, sender);
		if (player == null)
		{
			loger.help(sender, cmd, USAGE);
			return false;
		}


		Integer level = -1;

		if (args.length >= 2)
			try
			{
				level = Integer.valueOf(args[1]);
			}
			catch (Exception e)
			{
				level = 1;
			}

		HashMap<Enchantment, Integer> map = new HashMap<>();

		boolean wipe = false, list = false;

		for (int i = 1; i < args.length; i++)
		{
			if (args[i].equalsIgnoreCase("wipe"))
			{
				wipe = true;
			}
			else if (args[i].equalsIgnoreCase("list"))
			{
				int list_index = i + 1;
				list = true;
				for (int j = list_index; j < args.length; j++)
					if (args[j].equalsIgnoreCase("all"))
					{
						for (Enchantment enchantment : Enchantment.values())
							if (!enchantment.equals(Enchantment.VANISHING_CURSE) && !enchantment.equals(Enchantment.BINDING_CURSE))
								map.put(enchantment, level <= 0 ? enchantment.getMaxLevel() : level);
					}
					else
					{
						Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(args[j].toLowerCase()));
						if (enchantment != null)
							map.put(enchantment, level <= 0 ? enchantment.getMaxLevel() : level);
						else
							loger.error(sender, "invalid enchantment : " + ChatColor.UNDERLINE + args[j]);
					}
				break;
			}
			else if (args[i].equalsIgnoreCase("list-all"))
			{
				for (Enchantment e : Enchantment.values())
					loger.message(sender, ChatColor.LIGHT_PURPLE + e.getKey().toString() +
							ChatColor.YELLOW + ChatColor.UNDERLINE + " [1..." + e.getMaxLevel() + "]");
				return true;
			}
			else if (args[i].equalsIgnoreCase("copy"))
			{
				copied = player.getInventory().getItemInMainHand().getEnchantments();
			}
			else if (args[i].equalsIgnoreCase("paste"))
			{
				map = new HashMap<>(copied);
			}
		}

		if (wipe)
		{
			Map<Enchantment, Integer> mp = Objects.requireNonNull(player).getInventory().getItemInMainHand().getEnchantments();
			for (Enchantment v : mp.keySet())
				if (!list && (level <= 0 || Objects.equals(mp.get(v), level)) || map.get(v) != null)
					player.getInventory().getItemInMainHand().removeEnchantment(v);
			loger.message(sender, ChatColor.GREEN + "Enchantments wiped.");
		}
		else
		{
			player.getInventory().getItemInMainHand().addUnsafeEnchantments(map);
			loger.message(sender, ChatColor.GREEN + "Enchantments applied.");
		}
		return true;
	}


	private boolean check_user(@NotNull String[] args)
	{
		boolean typed = false;
		for (String a : args)
			if (PlayerUtils.Bukkit_getPlayer(a) != null)
			{
				typed = true;
				break;
			}
		return typed;
	}

	private void add_user(@NotNull ArrayList<String> list, @NotNull String[] args)
	{
		list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]));
	}


	private boolean check_level(@NotNull String[] args)
	{
		return Integer.getInteger(args[0]) != null || args.length > 1 && Integer.getInteger(args[1]) != null;
	}

	private void add_level(@NotNull ArrayList<String> list, @NotNull String[] args)
	{
		list.add(args[0]);
	}


	private boolean check_wipe(@NotNull String[] args)
	{
		return Arrays.stream(args).toList().contains("wipe");
	}

	private void add_wipe(@NotNull ArrayList<String> list, @NotNull String[] args)
	{
		list.add("wipe");
	}


	private boolean check_copy(@NotNull String[] args)
	{
		return Arrays.stream(args).toList().contains("copy");
	}

	private void add_copy(@NotNull ArrayList<String> list, @NotNull String[] args)
	{
		list.add("copy");
	}


	private boolean check_paste(@NotNull String[] args)
	{
		return Arrays.stream(args).toList().contains("paste");
	}

	private void add_paste(@NotNull ArrayList<String> list, @NotNull String[] args)
	{
		list.add("paste");
	}


	private boolean check_listall(@NotNull String[] args)
	{
		return Arrays.stream(args).toList().contains("list-all");
	}

	private void add_listall(@NotNull ArrayList<String> list, @NotNull String[] args)
	{
		list.add("list-all");
	}


	private boolean check_list(@NotNull String[] args)
	{
		return Arrays.stream(args).toList().contains("list");
	}

	private void add_list(@NotNull ArrayList<String> list, @NotNull String[] args, boolean is_list)
	{
		if (is_list)
		{
			int i = args.length - 1;
			HashSet<String> mentioned_enchantments = new HashSet<>();
			for (int j = 4; j < args.length; ++j)
				mentioned_enchantments.add(args[j].toLowerCase());
			for (Enchantment e : Enchantment.values())
				if (e.toString().contains(args[i].toLowerCase()) && !mentioned_enchantments.contains(args[i]))
					list.add(e.getKey().getKey().toLowerCase());
		}
		else
		{
			list.add("list");
		}
	}

	@Nullable
	@Override
	public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (args.length == 1) // 0 // <player id>
		{
			ArrayList<String> list = new ArrayList<>();

			boolean u = check_user(args);

			if (!u) add_user(list, args);

			return list;
		}
		else if (args.length == 2) // 1 // <level> / wipe / copy / paste / list-all / list / <list...>
		{
			ArrayList<String> list = new ArrayList<>();

			boolean l = check_level(args);
			boolean w = check_wipe(args);
			boolean c = check_copy(args);
			boolean p = check_paste(args);
			boolean ls = check_list(args);
			boolean a = c || p;

			if (!l && !w && !a && !ls) add_level(list, args);

			if (!w && !a && !ls) add_wipe(list, args);
			if (!w && !a && !ls) add_copy(list, args);
			if (!w && !a && !ls) add_paste(list, args);

			if (!a) add_list(list, args, ls);

			return list;
		}
		else if (args.length == 3) // 2 // wipe / copy / paste / list-all / list / <list...>
		{
			ArrayList<String> list = new ArrayList<>();

			boolean w = check_wipe(args);
			boolean c = check_copy(args);
			boolean p = check_paste(args);
			boolean la = check_listall(args);
			boolean ls = check_list(args);
			boolean a = c || p;

			if (!w && !a && !ls && !la) add_wipe(list, args);
			if (!w && !a && !ls && !la) add_copy(list, args);
			if (!w && !a && !ls && !la) add_paste(list, args);
			if (!w && !a && !ls && !la) add_listall(list, args);

			if (!a && !la) add_list(list, args, ls);

			return list;
		}
		else // 3...inf // list / <list...>
		{
			ArrayList<String> list = new ArrayList<>();

			boolean c = check_copy(args);
			boolean p = check_paste(args);
			boolean la = check_listall(args);
			boolean ls = check_list(args);
			boolean a = c || p;

			if (!a && !la) add_list(list, args, ls);

			return list;
		}
	}
}
