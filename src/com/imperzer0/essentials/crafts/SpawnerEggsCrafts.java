package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SpawnerEggsCrafts implements Listener
{
	public final Main plugin;
	public final NamespacedKey[] keys;
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
	}
	
	public SpawnerEggsCrafts(Main plugin)
	{
		this.plugin = plugin;
		keys = new NamespacedKey[]{
				new NamespacedKey(this.plugin, "spideregg"),
				new NamespacedKey(this.plugin, "cavespideregg"),
				new NamespacedKey(this.plugin, "skeletonegg"),
				new NamespacedKey(this.plugin, "zombieegg"),
				new NamespacedKey(this.plugin, "blazeegg")
		};
		Bukkit.addRecipe(spider_egg());
		Bukkit.addRecipe(cave_spider_egg());
		Bukkit.addRecipe(skeleton_egg());
		Bukkit.addRecipe(zombie_egg());
		Bukkit.addRecipe(blaze_egg());
	}
	
	public ShapedRecipe spider_egg()
	{
		ItemStack item = new ItemStack(Material.SPIDER_SPAWN_EGG);
		ShapedRecipe recipe = new ShapedRecipe(keys[0], item);
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
		ShapedRecipe recipe = new ShapedRecipe(keys[1], item);
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
		ShapedRecipe recipe = new ShapedRecipe(keys[2], item);
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
		ShapedRecipe recipe = new ShapedRecipe(keys[3], item);
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
		ShapedRecipe recipe = new ShapedRecipe(keys[4], item);
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
