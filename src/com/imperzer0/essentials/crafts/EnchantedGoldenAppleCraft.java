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

public class EnchantedGoldenAppleCraft implements Listener
{
	public final Main plugin;
	public final NamespacedKey key;
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipe(key);
	}
	
	public EnchantedGoldenAppleCraft(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.addRecipe(apple_recipe());
		key = new NamespacedKey(this.plugin, "enchantedgoldenapplecraft");
	}
	
	public ShapedRecipe apple_recipe()
	{
		ItemStack item = new ItemStack(Material.MAP);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		recipe.shape(
				"GGG",
				"GAG",
				"GGG"
		);
		recipe.setIngredient('G', Material.GOLD_BLOCK);
		recipe.setIngredient('A', Material.APPLE);
		return recipe;
	}
}
