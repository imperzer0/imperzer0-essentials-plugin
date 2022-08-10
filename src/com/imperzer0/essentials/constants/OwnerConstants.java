package com.imperzer0.essentials.constants;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class OwnerEnchantedKit
{
	public static final Map<Enchantment, Integer> ENCHANTMENTS = new HashMap<Enchantment, Integer>();
	public static final ItemStack[] owner_items = new ItemStack[11];
	public static final String[] owner_item_name = new String[11];
	
	
	enum OWNER_ITEMS
	{
		ANNIHILATOR(0),
		LIGHTNING_AXE(1),
		BOW_L(2),
		AUTO_AIM_BOW(3),
		SPRAY_ARROW(4),
		FLYING_TRIDENT(5),
		HELMET(6),
		WINGS(7),
		LEGGINGS(8),
		BOOTS(9),
		ELITE_GUARDIANS(10);
		
		OWNER_ITEMS(int index) { this.index = index; }
		
		public int index() { return index; }
		
		private final int index;
	}
	
	static
	{
		// ====
		ENCHANTMENTS.put(Enchantment.ARROW_DAMAGE, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.ARROW_FIRE, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.ARROW_INFINITE, 1);
		ENCHANTMENTS.put(Enchantment.ARROW_KNOCKBACK, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.CHANNELING, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.DAMAGE_ALL, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.DAMAGE_ARTHROPODS, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.DAMAGE_UNDEAD, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.DEPTH_STRIDER, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.DIG_SPEED, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.DURABILITY, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.FIRE_ASPECT, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.FROST_WALKER, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.IMPALING, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.KNOCKBACK, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.LOOT_BONUS_BLOCKS, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.LOOT_BONUS_MOBS, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.LOYALTY, 79);
		ENCHANTMENTS.put(Enchantment.LUCK, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.LURE, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.MENDING, 1);
		ENCHANTMENTS.put(Enchantment.MULTISHOT, 1);
		ENCHANTMENTS.put(Enchantment.OXYGEN, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.PIERCING, (Short.MAX_VALUE - 1));
		ENCHANTMENTS.put(Enchantment.PROTECTION_ENVIRONMENTAL, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.PROTECTION_EXPLOSIONS, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.PROTECTION_FALL, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.PROTECTION_FIRE, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.PROTECTION_PROJECTILE, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.QUICK_CHARGE, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.SILK_TOUCH, 1);
		ENCHANTMENTS.put(Enchantment.SWEEPING_EDGE, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.THORNS, Short.MAX_VALUE - 1);
		ENCHANTMENTS.put(Enchantment.WATER_WORKER, Short.MAX_VALUE - 1);
		// ====
		
		
		for (ItemStack item : owner_items)
			item.addEnchantments(ENCHANTMENTS);
	}
	
	static
	{
		owner_item_name[0] = "ANNIHILATOR";
		owner_item_name[1] = "LIGHTNING_AXE";
		owner_item_name[2] = "BOW_L";
		owner_item_name[3] = "AUTO_AIM_BOW";
		owner_item_name[4] = "SPRAY_ARROW";
		owner_item_name[5] = "FLYING_TRIDENT";
		owner_item_name[6] = "HELMET";
		owner_item_name[7] = "WINGS";
		owner_item_name[8] = "LEGGINGS";
		owner_item_name[9] = "BOOTS";
		owner_item_name[10] = "ELITE_GUARDIANS";
		
		owner_items[0] = new ItemStack(Material.BLAZE_ROD, 1);
		owner_items[1] = new ItemStack(Material.NETHERITE_AXE, 1);
		owner_items[2] = new ItemStack(Material.BOWL, 1);
		owner_items[3] = new ItemStack(Material.BOW, 1);
		owner_items[4] = new ItemStack(Material.ARROW, 1);
		owner_items[5] = new ItemStack(Material.TRIDENT, 1);
		owner_items[6] = new ItemStack(Material.BEDROCK, 1);
		owner_items[7] = new ItemStack(Material.ELYTRA, 1);
		owner_items[8] = new ItemStack(Material.NETHERITE_LEGGINGS, 1);
		owner_items[9] = new ItemStack(Material.NETHERITE_BOOTS, 1);
		owner_items[10] = new ItemStack(Material.ZOMBIE_HEAD, 1);
		
		for (int i = 0; i < owner_items.length; ++i)
		{
			ItemMeta meta = owner_items[i].getItemMeta();
			meta.setUnbreakable(true);
			meta.setDisplayName(owner_item_name[i]);
			owner_items[i].setItemMeta(meta);
		}
	}
}
