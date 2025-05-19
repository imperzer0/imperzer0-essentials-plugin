package me.imperzer0.essentials.crafts;

import me.imperzer0.essentials.utils.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EmeraldCrafts extends Craft
{
	public EmeraldCrafts()
	{
		super("emeraldswordcraft",
				"emeraldpickcraft",
				"emeraldaxecraft",
				"emeraldshovelcraft",
				"emeraldhoecraft",
				"emeraldbowcraft");
	}

	@EventHandler
	void on_login(@NotNull PlayerLoginEvent event)
	{
		event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
	}

	@Override
	protected Set<Pair<NamespacedKey, Recipe>> get_recipes()
	{
		Set<Pair<NamespacedKey, Recipe>> recipes = new HashSet<>();
		recipes.add(emerald_sword_recipe());
		recipes.add(emerald_pick_recipe());
		recipes.add(emerald_axe_recipe());
		recipes.add(emerald_shovel_recipe());
		recipes.add(emerald_hoe_recipe());
		recipes.add(emerald_bow_recipe());
		return recipes;
	}

	public Pair<NamespacedKey, Recipe> emerald_sword_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		item.addUnsafeEnchantment(Enchantment.UNBREAKING, 2); // DURABILITY
		item.addUnsafeEnchantment(Enchantment.SHARPNESS, 5); // DAMAGE_ALL
		item.addUnsafeEnchantment(Enchantment.BANE_OF_ARTHROPODS, 5); // DAMAGE_ARTHROPODS
		item.addUnsafeEnchantment(Enchantment.SMITE, 5); // DAMAGE_UNDEAD
		item.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3);
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 1);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Sword");
			item.setItemMeta(meta);
		} catch (Exception ignored)
		{
		}
		ShapedRecipe recipe = new ShapedRecipe(keys[0], item);
		recipe.shape(
				"E",
				"E",
				"S"
		);

		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return new Pair<>(keys[0], recipe);
	}

	public Pair<NamespacedKey, Recipe> emerald_pick_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
		item.addUnsafeEnchantment(Enchantment.UNBREAKING, 2); // DURABILITY
		item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5); // DIG_SPEED
		item.addUnsafeEnchantment(Enchantment.FORTUNE, 2); // LOOT_BONUS_BLOCKS
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Pickaxe");
			item.setItemMeta(meta);
		} catch (Exception ignored)
		{
		}
		ShapedRecipe recipe = new ShapedRecipe(keys[1], item);
		recipe.shape(
				"EEE",
				" S ",
				" S "
		);

		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return new Pair<>(keys[1], recipe);
	}

	public Pair<NamespacedKey, Recipe> emerald_axe_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_AXE);
		item.addUnsafeEnchantment(Enchantment.UNBREAKING, 2); // DURABILITY
		item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5); // DIG_SPEED
		item.addUnsafeEnchantment(Enchantment.FORTUNE, 2); // LOOT_BONUS_BLOCKS
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Axe");
			item.setItemMeta(meta);
		} catch (Exception ignored)
		{
		}
		ShapedRecipe recipe = new ShapedRecipe(keys[2], item);
		recipe.shape(
				"EE",
				"ES",
				" S"
		);

		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return new Pair<>(keys[2], recipe);
	}

	public Pair<NamespacedKey, Recipe> emerald_shovel_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_SHOVEL);
		item.addUnsafeEnchantment(Enchantment.UNBREAKING, 2); // DURABILITY
		item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5); // DIG_SPEED
		item.addUnsafeEnchantment(Enchantment.FORTUNE, 2); // LOOT_BONUS_BLOCKS
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Shovel");
			item.setItemMeta(meta);
		} catch (Exception ignored)
		{
		}
		ShapedRecipe recipe = new ShapedRecipe(keys[3], item);
		recipe.shape(
				"E",
				"S",
				"S"
		);

		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return new Pair<>(keys[3], recipe);
	}

	public Pair<NamespacedKey, Recipe> emerald_hoe_recipe()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_HOE);
		item.addUnsafeEnchantment(Enchantment.UNBREAKING, 2); // DURABILITY
		item.addUnsafeEnchantment(Enchantment.EFFICIENCY, 5); // DIG_SPEED
		item.addUnsafeEnchantment(Enchantment.FORTUNE, 2); // LOOT_BONUS_BLOCKS
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Hoe");
			item.setItemMeta(meta);
		} catch (Exception ignored)
		{
		}
		ShapedRecipe recipe = new ShapedRecipe(keys[4], item);
		recipe.shape(
				"EE",
				" S",
				" S"
		);

		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STICK);
		return new Pair<>(keys[4], recipe);
	}

	public Pair<NamespacedKey, Recipe> emerald_bow_recipe()
	{
		ItemStack item = new ItemStack(Material.BOW);
		item.addUnsafeEnchantment(Enchantment.UNBREAKING, 10); // DURABILITY
		item.addUnsafeEnchantment(Enchantment.POWER, 10); // ARROW_DAMAGE
		item.addUnsafeEnchantment(Enchantment.FLAME, 10); // ARROW_FIRE
		item.addUnsafeEnchantment(Enchantment.PUNCH, 5); // ARROW_KNOCKBACK
		item.addUnsafeEnchantment(Enchantment.INFINITY, 1); // ARROW_INFINITE
		item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 10);
		item.addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
		item.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, 10);
		try
		{
			ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
			meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.ITALIC + "Emerald Bow");
			item.setItemMeta(meta);
		} catch (Exception ignored)
		{
		}
		ShapedRecipe recipe = new ShapedRecipe(keys[5], item);
		recipe.shape(
				" ES",
				"E S",
				" ES"
		);

		recipe.setIngredient('E', Material.EMERALD_BLOCK);
		recipe.setIngredient('S', Material.STRING);
		return new Pair<>(keys[5], recipe);
	}
}
