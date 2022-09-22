package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class EnchantedGoldenAppleCraft
{
	public final Main plugin;
	
	public EnchantedGoldenAppleCraft(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.addRecipe(apple_recipe());
	}
	
	public ShapedRecipe apple_recipe()
	{
		ItemStack item = new ItemStack(Material.MAP);
		NamespacedKey key = new NamespacedKey(this.plugin, "map");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"GGG",
				"GAG",
				"GGG"
		);
		recipe.setIngredient('G', Material.GOLD_BLOCK);
		recipe.setIngredient('A', Material.APPLE);
		return recipe;
	}
}
