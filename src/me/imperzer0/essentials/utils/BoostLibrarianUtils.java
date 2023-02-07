package me.imperzer0.essentials.utils;

import org.bukkit.enchantments.Enchantment;

import java.util.Map;

public class BoostLibrarianUtils
{
	private static Enchantment next_enchantment = null;
	private static int next_enchantment_level = 0;

	public static void set_next_enchantment(Enchantment enchantment)
	{
		next_enchantment = enchantment;
	}

	public static void set_next_enchantment_level(int enchantment_level)
	{
		next_enchantment_level = enchantment_level;
	}

	public static Enchantment obtain_next_enchantment()
	{
		Enchantment ench = next_enchantment;
		next_enchantment = null;
		return ench;
	}

	public static int obtain_next_enchantment_level()
	{
		int ench_l = next_enchantment_level;
		next_enchantment_level = 0;
		return ench_l;
	}

	public static boolean is_enchantment_valid()
	{
		return next_enchantment != null && next_enchantment_level > 0;
	}
}
