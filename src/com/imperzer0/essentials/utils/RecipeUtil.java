package com.imperzer0.essentials.utils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import static com.imperzer0.essentials.Main.plugin;

public class RecipeUtil
{
	public static void add_recipe(@NotNull Pair<NamespacedKey, Recipe> recipe)
	{
		plugin.getLogger().info("Loaded recipe '" + recipe.getKey().getKey() + "'.");
		Bukkit.addRecipe(recipe.getValue());
	}
}
