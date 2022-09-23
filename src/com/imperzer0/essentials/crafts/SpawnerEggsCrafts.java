package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SpawnerEggsCrafts
{
	public final Main plugin;
	
	public SpawnerEggsCrafts(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.addRecipe(spider_egg());
		Bukkit.addRecipe(cave_spider_egg());
		Bukkit.addRecipe(skeleton_egg());
		Bukkit.addRecipe(zombie_egg());
		Bukkit.addRecipe(blaze_egg());
	}
	
	public ShapedRecipe spider_egg()
	{
		ItemStack item = new ItemStack(Material.SPIDER_SPAWN_EGG);
		NamespacedKey key = new NamespacedKey(this.plugin, "spideregg");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"EEE",
				"EPE",
				"EEE"
		);
		recipe.setIngredient('E', Material.EGG);
		recipe.setIngredient('P', Material.STRING);
		return recipe;
	}
	
	public ShapedRecipe cave_spider_egg()
	{
		ItemStack item = new ItemStack(Material.CAVE_SPIDER_SPAWN_EGG);
		NamespacedKey key = new NamespacedKey(this.plugin, "cavespideregg");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"EEE",
				"EPE",
				"EEE"
		);
		recipe.setIngredient('E', Material.EGG);
		recipe.setIngredient('P', Material.SPIDER_EYE);
		return recipe;
	}
	
	public ShapedRecipe skeleton_egg()
	{
		ItemStack item = new ItemStack(Material.SKELETON_SPAWN_EGG);
		NamespacedKey key = new NamespacedKey(this.plugin, "skeletonegg");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"EEE",
				"EPE",
				"EEE"
		);
		recipe.setIngredient('E', Material.EGG);
		recipe.setIngredient('P', Material.BONE);
		return recipe;
	}
	
	public ShapedRecipe zombie_egg()
	{
		ItemStack item = new ItemStack(Material.ZOMBIE_SPAWN_EGG);
		NamespacedKey key = new NamespacedKey(this.plugin, "zombieegg");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"EEE",
				"EPE",
				"EEE"
		);
		recipe.setIngredient('E', Material.EGG);
		recipe.setIngredient('P', Material.ROTTEN_FLESH);
		return recipe;
	}
	
	public ShapedRecipe blaze_egg()
	{
		ItemStack item = new ItemStack(Material.BLAZE_SPAWN_EGG);
		NamespacedKey key = new NamespacedKey(this.plugin, "blazeegg");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"EEE",
				"EPE",
				"EEE"
		);
		recipe.setIngredient('E', Material.EGG);
		recipe.setIngredient('P', Material.BLAZE_ROD);
		return recipe;
	}
}
