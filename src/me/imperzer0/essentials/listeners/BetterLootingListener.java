package me.imperzer0.essentials.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

		if (!(event.getEntity() instanceof Boss ||
				event.getEntity() instanceof WitherSkeleton ||
				event.getEntity() instanceof Slime ||
				event.getEntity() instanceof MagmaCube))
			return;

		Player damager = event.getEntity().getKiller();
		if (damager == null)
			return;

		int multiplier = damager.getInventory().getItemInMainHand().getEnchantments().getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);

		if (multiplier <= 0)
			return;

		if (random.nextInt() % multiplier == 0)
			return;

		ArrayList<ItemStack> drops = new ArrayList<>(event.getDrops());
		for (ItemStack drop : drops)
			for (int i = 0; i < multiplier; ++i)
				event.getDrops().add(new ItemStack(drop));

		event.setDroppedExp(event.getDroppedExp() * (multiplier + 1));
	}
}
