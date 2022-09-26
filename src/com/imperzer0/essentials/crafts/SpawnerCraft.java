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

public class SpawnerCraft implements Listener
{
	public final Main plugin;
	public final NamespacedKey key;
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipe(key);
	}
	
	public SpawnerCraft(Main plugin)
	{
		this.plugin = plugin;
		key = new NamespacedKey(this.plugin, "spawnercraft");
		Bukkit.addRecipe(spawner());
	}
	
	public ShapedRecipe spawner()
	{
		ItemStack item = new ItemStack(Material.SPAWNER);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"III",
				"I I",
				"III"
		);
		recipe.setIngredient('I', Material.IRON_BLOCK);
		return recipe;
	}
}
