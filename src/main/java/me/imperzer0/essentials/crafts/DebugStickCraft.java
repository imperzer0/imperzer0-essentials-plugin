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

public class DebugStickCraft extends Craft
{

	public DebugStickCraft()
	{
		super("debugstickcraft");
	}

	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
	}

	@Override
	public Set<Pair<NamespacedKey, Recipe>> get_recipes()
	{
		ItemStack item = new ItemStack(Material.DEBUG_STICK);
		ShapedRecipe recipe = new ShapedRecipe(keys[0], item);
		recipe.shape(
				"G",
				"I",
				"I"
		);

		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('G', Material.GOLD_INGOT);

		Set<Pair<NamespacedKey, Recipe>> recipes = new HashSet<>();
		recipes.add(new Pair<>(keys[0], recipe));
		return recipes;
	}
}
