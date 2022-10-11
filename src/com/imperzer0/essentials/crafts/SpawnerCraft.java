package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.utils.Pair;
import com.imperzer0.essentials.utils.RecipeUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import static com.imperzer0.essentials.Main.plugin;

public class SpawnerCraft implements Listener
{
	public final NamespacedKey key;
	
	public SpawnerCraft()
	{
		key = new NamespacedKey(plugin, "spawnercraft");
		RecipeUtil.add_recipe(spawner());
	}
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipe(key);
	}
	
	public Pair<NamespacedKey, Recipe> spawner()
	{
		ItemStack item = new ItemStack(Material.SPAWNER);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"III",
				"I I",
				"III"
		);
		recipe.setIngredient('I', Material.IRON_BLOCK);
		return new Pair<>(key, recipe);
	}
}
