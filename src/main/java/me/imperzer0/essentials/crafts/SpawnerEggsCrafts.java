package me.imperzer0.essentials.crafts;

import me.imperzer0.essentials.utils.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SpawnerEggsCrafts extends Craft
{
	public SpawnerEggsCrafts()
	{
		super("spideregg",
				"cavespideregg",
				"skeletonegg",
				"zombieegg",
				"blazeegg");
	}

	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
	}

	@Override
	protected Set<Pair<NamespacedKey, Recipe>> get_recipes()
	{
		Set<Pair<NamespacedKey, Recipe>> recipes = new HashSet<>();
		recipes.add(spider_egg());
		recipes.add(cave_spider_egg());
		recipes.add(skeleton_egg());
		recipes.add(zombie_egg());
		recipes.add(blaze_egg());
		return recipes;
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
