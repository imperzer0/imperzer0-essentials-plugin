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

public class EnchantedGoldenAppleCraft extends Craft
{
	public EnchantedGoldenAppleCraft()
	{
		super("enchantedgoldenapplecraft");
	}

	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
	}

	@Override
	protected Set<Pair<NamespacedKey, Recipe>> get_recipes()
	{
		ItemStack item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
		ShapedRecipe recipe = new ShapedRecipe(keys[0], item);
		recipe.shape(
				"GGG",
				"GAG",
				"GGG"
		);
		recipe.setIngredient('G', Material.GOLD_BLOCK);
		recipe.setIngredient('A', Material.APPLE);

		Set<Pair<NamespacedKey, Recipe>> recipes = new HashSet<>();
		recipes.add(new Pair<>(keys[0], recipe));
		return recipes;
	}
}
