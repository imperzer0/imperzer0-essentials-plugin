package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.BoostLibrarianUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import me.imperzer0.essentials.listeners.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BoostLibrarianListener extends Listener
{
	public BoostLibrarianListener(@NotNull PluginManager manager)
	{
		super(manager);
	}

	@EventHandler
	void villager_acquire_trade(VillagerAcquireTradeEvent event)
	{
		if (!BoostLibrarianUtils.is_enchantment_valid())
			return;

		if (!(event.getEntity() instanceof Villager villager))
			return;

		int level = villager.getVillagerLevel();
		Villager.Profession profession = villager.getProfession();
		if (!profession.equals(Villager.Profession.LIBRARIAN))
			return;

		Villager.Type type = villager.getVillagerType();
		List<MerchantRecipe> exists = villager.getRecipes();
		Location loc = villager.getLocation();

		MerchantRecipe old_book_recipe = event.getRecipe();

		if (!old_book_recipe.getResult().getType().equals(Material.ENCHANTED_BOOK))
			return;

		ItemStack reward = new ItemStack(Material.ENCHANTED_BOOK);
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) reward.getItemMeta();
		if (meta == null) return;
		meta.addStoredEnchant(BoostLibrarianUtils.obtain_next_enchantment(), BoostLibrarianUtils.obtain_next_enchantment_level(), true);
		reward.setItemMeta(meta);

		MerchantRecipe new_book_recipe = new MerchantRecipe(reward, old_book_recipe.getUses(), old_book_recipe.getMaxUses(),
				old_book_recipe.hasExperienceReward(), old_book_recipe.getVillagerExperience(), old_book_recipe.getPriceMultiplier());
		new_book_recipe.setIngredients(old_book_recipe.getIngredients());
		event.setRecipe(new_book_recipe);

		Main.getInstance().getLogger().info("entityId " + villager.getEntityId() + ", location " +
				loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() +
				", level " + level + ", type " + type + ", profession " + profession + ", " + reward + ", " + exists.size());
	}

	@EventHandler
	void player_click(PlayerInteractEntityEvent event)
	{
		if (!(event.getRightClicked() instanceof Villager villager))
			return;
		if (!BoostLibrarianUtils.is_enchantment_valid())
			return;

		int level = villager.getVillagerLevel();
		Villager.Profession profession = villager.getProfession();
		if (!profession.equals(Villager.Profession.LIBRARIAN))
			return;

		Villager.Type type = villager.getVillagerType();
		List<MerchantRecipe> exists = villager.getRecipes();
		Location loc = villager.getLocation();

		MerchantRecipe old_book_recipe = null;

		int i = 0;
		for (; i < villager.getRecipeCount(); ++i)
		{
			MerchantRecipe r = villager.getRecipe(i);
			if (r.getResult().getType().equals(Material.ENCHANTED_BOOK))
			{
				old_book_recipe = r;
				break;
			}
		}

		if (old_book_recipe == null)
			return;

		ItemStack reward = new ItemStack(Material.ENCHANTED_BOOK);
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) reward.getItemMeta();

		if (meta == null)
			return;

		meta.addStoredEnchant(BoostLibrarianUtils.obtain_next_enchantment(), BoostLibrarianUtils.obtain_next_enchantment_level(), true);
		reward.setItemMeta(meta);

		MerchantRecipe new_book_recipe = new MerchantRecipe(reward, old_book_recipe.getUses(), old_book_recipe.getMaxUses(),
				old_book_recipe.hasExperienceReward(), old_book_recipe.getVillagerExperience(), old_book_recipe.getPriceMultiplier());
		new_book_recipe.setIngredients(old_book_recipe.getIngredients());
		villager.setRecipe(i, new_book_recipe);

		Main.getInstance().getLogger().info("entityId " + villager.getEntityId() + ", location " +
				loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() +
				", level " + level + ", type " + type + ", profession " + profession + ", " + reward + ", " + exists.size());
	}
}
