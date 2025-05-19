package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class EnchantmentsListener extends Listener
{
	public EnchantmentsListener(@NotNull PluginManager manager)
	{
		super(manager);
	}

	@EventHandler
	void on_inventory_click(PrepareAnvilEvent event)
	{
		int val = Main.getInstance().getConfig().getInt("enchantments.max_level");
		if (val <= 0)
			return;

		event.getView().setMaximumRepairCost(val);
	}
}
