package me.imperzer0.essentials.utils;

import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EnchantmentUtils
{
	public static @NotNull ArrayList<String> Enchantment_getAllEnchantmentsNames(String filter)
	{
		ArrayList<String> names = new ArrayList<>();
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
