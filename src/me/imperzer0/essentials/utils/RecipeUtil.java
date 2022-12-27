package me.imperzer0.essentials.utils;

import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

public class RecipeUtil
{
	public static void add_recipe(@NotNull Pair<NamespacedKey, Recipe> recipe)
	{
		Main.getInstance().getLogger().info("Loaded recipe \"" + recipe.getKey().getKey() + "\".");
		Bukkit.addRecipe(recipe.getValue());
	}
}
