package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SpawnerCraft
{
	public final Main plugin;
	
	public SpawnerCraft(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.addRecipe(spawner());
	}
	
	public ShapedRecipe spawner()
	{
		ItemStack item = new ItemStack(Material.SPAWNER);
		NamespacedKey key = new NamespacedKey(this.plugin, "spawnercraft");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"III",
				"I I",
				"III"
		);
		recipe.setIngredient('I', Material.IRON_BLOCK);
		return recipe;
	}
}
