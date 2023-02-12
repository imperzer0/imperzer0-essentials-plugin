package me.imperzer0.essentials.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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

		EntityDamageEvent last_damage = event.getEntity().getLastDamageCause();
		if (last_damage == null)
			return;

		if (!(last_damage.getEntity() instanceof Player damager))
			return;

		int multiplier = damager.getInventory().getItemInMainHand().getEnchantments().getOrDefault(Enchantment.LOOT_BONUS_MOBS, 0);

		if (multiplier <= 0)
			return;

		if (random.nextInt(multiplier + 1) == 0)
			return;

		List<ItemStack> drops = event.getDrops();
		for (ItemStack drop : drops)
			for (int i = 0; i < multiplier; ++i)
				drops.add(drop);
	}
}
