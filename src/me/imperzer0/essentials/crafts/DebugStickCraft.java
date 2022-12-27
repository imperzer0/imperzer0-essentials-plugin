package me.imperzer0.essentials.crafts;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.Pair;
import me.imperzer0.essentials.utils.RecipeUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

public class DebugStickCraft implements Listener
{
	public final NamespacedKey key;

	public DebugStickCraft()
	{
		key = new NamespacedKey(Main.getInstance(), "debugstickcraft");
		RecipeUtil.add_recipe(debug_stick_recipe());
	}

	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipe(key);
	}

	public Pair<NamespacedKey, Recipe> debug_stick_recipe()
	{
		ItemStack item = new ItemStack(Material.DEBUG_STICK);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"G",
				"I",
				"I"
		);

		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('G', Material.GOLD_INGOT);
		return new Pair<>(key, recipe);
	}
}
