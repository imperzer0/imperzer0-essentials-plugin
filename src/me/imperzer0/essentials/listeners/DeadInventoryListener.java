package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.utils.DeadInventoryUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DeadInventoryListener implements Listener
{
	@EventHandler
	void on_player_death(@NotNull PlayerDeathEvent event)
	{
		boolean is_empty = true;
		for (ItemStack is : event.getDrops())
			if (!is.getType().equals(Material.AIR))
				is_empty = false;
		if (is_empty) return;

		DeadInventoryUtils.save_inventory(event.getEntity().getUniqueId(),
				DeadInventoryUtils.preserve_inventory(event.getEntity(), event.getDrops()));
	}
}
