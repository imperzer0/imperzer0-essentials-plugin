package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DebugStickCraft implements Listener
{
	public final Main plugin;
	public final NamespacedKey key;
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipe(key);
	}
	
	public DebugStickCraft(Main plugin)
	{
		this.plugin = plugin;
		key = new NamespacedKey(this.plugin, "debugstickcraft");
		Bukkit.addRecipe(debug_stick_recipe());
	}
	
	
	public ShapedRecipe debug_stick_recipe()
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
		return recipe;
	}
}
