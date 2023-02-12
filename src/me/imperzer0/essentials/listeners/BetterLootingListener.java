package me.imperzer0.essentials.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class BetterLootingListener implements Listener
{
	private static final Random random = new Random(LocalDateTime.now().getNano());

	public BetterLootingListener()
	{
	}

	@EventHandler
	void on_player_kill_entity(@NotNull EntityDeathEvent event)
	{
		if (event.getEntity() instanceof Player)
			return;

		Player damager = event.getEntity().getKiller();
		if (damager == null)
			return;

		int multiplier = damager.getInventory().getItemInMainHand().getEnchantments().getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);
		++multiplier;

		if (multiplier <= 0)
			return;

		if (random.nextInt() % multiplier == 0)
			return;

		List<ItemStack> drops = event.getDrops();
		for (int i = 0; i < multiplier; ++i)
			event.getDrops().addAll(drops);
	}
}
