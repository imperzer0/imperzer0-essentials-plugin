package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;

public class EnchantmentsListener implements Listener
{
	@EventHandler
	void on_inventory_click(PrepareAnvilEvent event)
	{
		int val = Main.getInstance().getConfig().getInt("enchantments.max_level");
		if (val <= 0)
			return;

		event.getInventory().setMaximumRepairCost(val);
	}
}
