package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class EmeraldCrafts implements Listener
{
	public final Main plugin;
	public final NamespacedKey[] keys;
	
	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
	}
	
	public EmeraldCrafts(Main plugin)
	{
		this.plugin = plugin;
		keys = new NamespacedKey[]{
				new NamespacedKey(plugin, "emeraldswordcraft"),
				new NamespacedKey(plugin, "emeraldpickcraft"),
				new NamespacedKey(plugin, "emeraldaxecraft"),
				new NamespacedKey(plugin, "emeraldshovelcraft"),
				new NamespacedKey(plugin, "emeraldhoecraft"),
				new NamespacedKey(plugin, "emeraldbowcraft")
		};
		Bukkit.addRecipe(emerald_sword_recipe());
		Bukkit.addRecipe(emerald_pick_recipe());
		Bukkit.addRecipe(emerald_axe_recipe());
		Bukkit.addRecipe(emerald_shovel_recipe());
		Bukkit.addRecipe(emerald_hoe_recipe());
		Bukkit.addRecipe(emerald_bow_recipe());
	}
	
	public ShapedRecipe emerald_sword_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_ARTHROPODS, 5);
		item.addUnsafeEnchantment(Enchantment.DAMAGE_UNDEAD, 5);
		item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3);
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Sword");
			item.setItemMeta(meta);
		}
		catch (Exception ignored) { }
		ShapedRecipe recipe = new ShapedRecipe(keys[0], item);
		recipe.shape(
				"E",
				"E",
				"S"
		);
		
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return recipe;
	}
	
	public ShapedRecipe emerald_pick_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Pickaxe");
			item.setItemMeta(meta);
		}
		catch (Exception ignored) { }
		ShapedRecipe recipe = new ShapedRecipe(keys[1], item);
		recipe.shape(
				"EEE",
				" S ",
				" S "
		);
		
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return recipe;
	}
	
	public ShapedRecipe emerald_axe_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_AXE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Axe");
			item.setItemMeta(meta);
		}
		catch (Exception ignored) { }
		ShapedRecipe recipe = new ShapedRecipe(keys[2], item);
		recipe.shape(
				"EE",
				"ES",
				" S"
		);
		
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return recipe;
	}
	
	public ShapedRecipe emerald_shovel_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Shovel");
			item.setItemMeta(meta);
		}
		catch (Exception ignored) { }
		ShapedRecipe recipe = new ShapedRecipe(keys[3], item);
		recipe.shape(
				"E",
				"S",
				"S"
		);
		
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return recipe;
	}
	
	public ShapedRecipe emerald_hoe_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_HOE);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 2);
		item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 2);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Hoe");
			item.setItemMeta(meta);
		}
		catch (Exception ignored) { }
		ShapedRecipe recipe = new ShapedRecipe(keys[4], item);
		recipe.shape(
				"EE",
				" S",
				" S"
		);
		
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return recipe;
	}
	
	public ShapedRecipe emerald_bow_recipe()
	{
		ItemStack item = new ItemStack(Material.BOW);
		item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 10);
		item.addUnsafeEnchantment(Enchantment.ARROW_FIRE, 10);
		item.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, 5);
		item.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
		item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
		item.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 10);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Bow");
			item.setItemMeta(meta);
		}
		catch (Exception ignored) { }
		ShapedRecipe recipe = new ShapedRecipe(keys[5], item);
		recipe.shape(
				" ES",
				"E S",
				" ES"
		);
		
		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STRING);
		return recipe;
	}
}
