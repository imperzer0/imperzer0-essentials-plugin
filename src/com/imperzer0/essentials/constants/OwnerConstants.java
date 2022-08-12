package com.imperzer0.essentials.constants;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class OwnerConstants
{
	public static final Map<Enchantment, Integer> ENCHANTMENTS = new HashMap<>();
	public static final List<PotionEffect> POTION_EFFECTS = new ArrayList<>();
	public static final ItemStack[] owner_items = new ItemStack[11];
	
	static
	{
		POTION_EFFECTS.add(new PotionEffect(PotionEffectType.REGENERATION, 1000000, Short.MAX_VALUE - 1, false, false, false));
		POTION_EFFECTS.add(new PotionEffect(PotionEffectType.SATURATION, 1000000, 255, false, false, false));
		POTION_EFFECTS.add(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1, false, false, false));
		POTION_EFFECTS.add(new PotionEffect(PotionEffectType.WATER_BREATHING, 1000000, 1, false, false, false));
		POTION_EFFECTS.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000, 1, false, false, false));
		POTION_EFFECTS.add(new PotionEffect(PotionEffectType.LUCK, 1000000, 255, false, false, false));
		POTION_EFFECTS.add(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 1000000, 255, false, false, false));
		
		
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
		
		
		// ====
		
		
		owner_items[0] = new ItemStack(Material.BLAZE_ROD, 1);
		owner_items[1] = new ItemStack(Material.NETHERITE_AXE, 1);
		owner_items[2] = new ItemStack(Material.BOWL, 1);
		owner_items[3] = new ItemStack(Material.BOW, 1);
		owner_items[4] = new ItemStack(Material.ARROW, 1);
		owner_items[5] = new ItemStack(Material.TRIDENT, 1);
		owner_items[6] = new ItemStack(Material.ZOMBIE_HEAD, 1);
		owner_items[7] = new ItemStack(Material.NETHERITE_BOOTS, 1);
		owner_items[8] = new ItemStack(Material.NETHERITE_LEGGINGS, 1);
		owner_items[9] = new ItemStack(Material.ELYTRA, 1);
		owner_items[10] = new ItemStack(Material.BEDROCK, 1);
		
		for (int i = 0; i < owner_items.length; ++i)
		{
			ItemMeta meta = owner_items[i].getItemMeta();
			assert meta != null;
			meta.setUnbreakable(true);
			meta.setDisplayName(OWNER_ITEMS.from_index(i).name());
			owner_items[i].setItemMeta(meta);
			owner_items[i].addUnsafeEnchantments(ENCHANTMENTS);
		}
	}
	
	@Contract(value = " -> new", pure = true)
	public static ItemStack @NotNull [] filter_owner_items_for_inventory()
	{ return Arrays.copyOf(owner_items, owner_items.length - 4); }
	
	@Contract(value = " -> new", pure = true)
	public static ItemStack @NotNull [] filter_owner_items_for_armor()
	{ return Arrays.copyOfRange(owner_items, owner_items.length - 4, owner_items.length); }
	
	public static void apply_owner_effects(@NotNull HumanEntity owner)
	{
		for (PotionEffect effect : owner.getActivePotionEffects())
			owner.removePotionEffect(effect.getType());
		owner.addPotionEffects(POTION_EFFECTS);
		owner.setMaxHealth(999999.0);
		owner.setHealth(owner.getMaxHealth());
	}
	
	public static void clear_owner_effects(@NotNull HumanEntity owner)
	{
		for (PotionEffect effect : owner.getActivePotionEffects())
			owner.removePotionEffect(effect.getType());
		owner.setMaxHealth(20.0);
		owner.setHealth(20.0);
		owner.setFoodLevel(20);
	}
	
	public static boolean is_owner_item(ItemStack item)
	{
		return item != null && item.getEnchantments().equals(ENCHANTMENTS);
	}
	
	public static boolean is_from_kit(ItemStack item)
	{
		if (item == null || !item.getEnchantments().equals(ENCHANTMENTS)) return false;
		for (ItemStack oi : owner_items) if (item.getType().equals(oi.getType())) return true;
		return false;
	}
	
	public enum OWNER_ITEMS
	{
		ANNIHILATOR(0),
		LIGHTNING_AXE(1),
		BOW_L(2),
		AUTO_AIM_BOW(3),
		SPRAY_ARROW(4),
		FLYING_TRIDENT(5),
		ELITE_GUARDIANS(6),
		BOOTS(7),
		LEGGINGS(8),
		WINGS(9),
		HELMET(10);
		
		private final int idx;
		
		OWNER_ITEMS(int idx) { this.idx = idx; }
		
		public static OWNER_ITEMS from_index(int idx)
		{
			return switch (idx)
					       {
						       case 0 -> OWNER_ITEMS.ANNIHILATOR;
						       case 1 -> OWNER_ITEMS.LIGHTNING_AXE;
						       case 2 -> OWNER_ITEMS.BOW_L;
						       case 3 -> OWNER_ITEMS.AUTO_AIM_BOW;
						       case 4 -> OWNER_ITEMS.SPRAY_ARROW;
						       case 5 -> OWNER_ITEMS.FLYING_TRIDENT;
						       case 6 -> OWNER_ITEMS.ELITE_GUARDIANS;
						       case 7 -> OWNER_ITEMS.BOOTS;
						       case 8 -> OWNER_ITEMS.LEGGINGS;
						       case 9 -> OWNER_ITEMS.WINGS;
						       case 10 -> OWNER_ITEMS.HELMET;
						       default -> null;
					       };
		}
		
		public final int index() { return idx; }
	}
}
