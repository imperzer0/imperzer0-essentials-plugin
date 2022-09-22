package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class DebugStickCraft
{
	public final Main plugin;
	
	public DebugStickCraft(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.addRecipe(debug_stick_recipe());
	}
	
	
	public ShapedRecipe debug_stick_recipe()
	{
		ItemStack item = new ItemStack(Material.DEBUG_STICK);
		NamespacedKey key = new NamespacedKey(this.plugin, "debugstick");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"G",
				"I",
				"I"
		);
		
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('G', Material.GOLD_INGOT);
		return recipe;
	}
}
