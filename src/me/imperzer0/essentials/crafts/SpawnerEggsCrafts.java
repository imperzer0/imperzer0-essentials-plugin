package me.imperzer0.essentials.crafts;

import me.imperzer0.essentials.utils.Pair;
import me.imperzer0.essentials.utils.RecipeUtil;
import me.imperzer0.essentials.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SpawnerEggsCrafts implements Listener
{
	public final NamespacedKey[] keys;
	
	public SpawnerEggsCrafts()
	{
		keys = new NamespacedKey[]{
				new NamespacedKey(Main.plugin, "spideregg"),
				new NamespacedKey(Main.plugin, "cavespideregg"),
				new NamespacedKey(Main.plugin, "skeletonegg"),
				new NamespacedKey(Main.plugin, "zombieegg"),
				new NamespacedKey(Main.plugin, "blazeegg")
		};
		RecipeUtil.add_recipe(spider_egg());
		RecipeUtil.add_recipe(cave_spider_egg());
		RecipeUtil.add_recipe(skeleton_egg());
		RecipeUtil.add_recipe(zombie_egg());
		RecipeUtil.add_recipe(blaze_egg());
	}
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
	}
	
	public Pair<NamespacedKey, Recipe> spider_egg()
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
		return new Pair<>(keys[0], recipe);
	}
	
	public Pair<NamespacedKey, Recipe> cave_spider_egg()
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
		return new Pair<>(keys[1], recipe);
	}
	
	public Pair<NamespacedKey, Recipe> skeleton_egg()
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
		return new Pair<>(keys[2], recipe);
	}
	
	public Pair<NamespacedKey, Recipe> zombie_egg()
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
		return new Pair<>(keys[3], recipe);
	}
	
	public Pair<NamespacedKey, Recipe> blaze_egg()
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
		return new Pair<>(keys[4], recipe);
	}
}
