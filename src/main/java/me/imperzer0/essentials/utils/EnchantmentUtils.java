package me.imperzer0.essentials.utils;

import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class EnchantmentUtils
{
	public static @NotNull LinkedList<String> Enchantment_getAllEnchantmentsNames(String filter)
	{
		LinkedList<String> names = new LinkedList<>();
		for (Enchantment enchantment : Enchantment.values())
		{
			if (filter == null)
			{
				names.add(enchantment.getKey().getKey());
				names.add(enchantment.getKey().getKey().toLowerCase());
			}
			else if (enchantment.getKey().getKey().contains(filter.toLowerCase()))
			{
				names.add(enchantment.getKey().getKey());
				names.add(enchantment.getKey().getKey().toLowerCase());
			}
		}
		return names;
	}
}
