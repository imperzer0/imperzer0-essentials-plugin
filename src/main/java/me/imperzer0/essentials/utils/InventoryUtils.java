package me.imperzer0.essentials.utils;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class InventoryUtils
{
	public static @NotNull ItemStack[] multiply_items(@NotNull final ItemStack[] inventory, int multiplier)
	{
		ItemStack[] result = new ItemStack[inventory.length];
		for (int i = 0; i < inventory.length; i++)
		{
			result[i] = new ItemStack(inventory[i]);
			result[i].setAmount(result[i].getAmount() * multiplier);
		}
		return result;
	}
}
