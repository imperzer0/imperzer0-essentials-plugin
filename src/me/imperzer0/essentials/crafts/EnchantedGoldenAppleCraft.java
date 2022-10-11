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

public class EnchantedGoldenAppleCraft implements Listener
{
	public final NamespacedKey key;
	
	public EnchantedGoldenAppleCraft()
	{
		key = new NamespacedKey(Main.plugin, "enchantedgoldenapplecraft");
		RecipeUtil.add_recipe(apple_recipe());
	}
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipe(key);
	}
	
	public Pair<NamespacedKey, Recipe> apple_recipe()
	{
		ItemStack item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"GGG",
				"GAG",
				"GGG"
		);
		recipe.setIngredient('G', Material.GOLD_BLOCK);
		recipe.setIngredient('A', Material.APPLE);
		return new Pair<>(key, recipe);
	}
}
