package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class MapCraft
{
	public final Main plugin;
	
	public MapCraft(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.addRecipe(map_recipe());
	}
	
	public ShapedRecipe map_recipe()
	{
		ItemStack item = new ItemStack(Material.MAP);
		NamespacedKey key = new NamespacedKey(this.plugin, "map");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"PPP",
				"PPP",
				"PPP"
		);
		recipe.setIngredient('P', Material.PAPER);
		return recipe;
	}
}
