package com.imperzer0.essentials.crafts;

import com.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class EmeraldCrafts
{
	public final Main plugin;
	public static final String[] keys = {
			"emeraldsword",
			"emeraldpick",
			"emeraldaxe",
			"emeraldshovel",
			"emeraldhoe",
			"emeraldbow"
	};
	
	public EmeraldCrafts(Main plugin)
	{
		this.plugin = plugin;
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
		NamespacedKey key = new NamespacedKey(this.plugin, keys[0]);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
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
		NamespacedKey key = new NamespacedKey(this.plugin, keys[1]);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
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
		NamespacedKey key = new NamespacedKey(this.plugin, keys[2]);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
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
		NamespacedKey key = new NamespacedKey(this.plugin, keys[3]);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
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
		NamespacedKey key = new NamespacedKey(this.plugin, keys[4]);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
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
		NamespacedKey key = new NamespacedKey(this.plugin, keys[5]);
		ShapedRecipe recipe = new ShapedRecipe(key, item);
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
