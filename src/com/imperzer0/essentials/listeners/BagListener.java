package com.imperzer0.essentials.listeners;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.utils.BagUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import static com.imperzer0.essentials.commands.Bag.PERMISSION_CLEAR;

public class BagListener implements Listener
{
	public final Main plugin;
	
	public BagListener(Main plugin) { this.plugin = plugin; }
	
	void process_inventory(@NotNull Inventory inventory)
	{
		if (inventory.getHolder() instanceof BagUtils.BagInventoryHolder holder)
			BagUtils.save_inventory(plugin, holder.getUuid(), inventory.getContents());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on_new_player_login(@NotNull PlayerLoginEvent event)
	{
		Permission permission = new Permission(
				PERMISSION_CLEAR + event.getPlayer().getUniqueId().toString().toLowerCase(),
				"Clear specific user's bag", PermissionDefault.FALSE
		);
		try { Bukkit.getServer().getPluginManager().addPermission(permission); }
		catch (Exception ignored) { }
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on_player_clicks_inventory(InventoryClickEvent event)
	{
		try
		{
			final Inventory inventory = event.getView().getTopInventory();
			new BukkitRunnable()
			{
				@Override
				public void run() { process_inventory(inventory); }
			}.runTaskLater(plugin, event.getHandlers().getRegisteredListeners().length + 1);
		}
		catch (Exception ignored) { }
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on_player_closes_inventory(InventoryCloseEvent event)
	{
		try { process_inventory(event.getView().getTopInventory()); }
		catch (Exception ignored) { }
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on_player_drags_inventory(InventoryDragEvent event)
	{
		try
		{
			final Inventory inventory = event.getView().getTopInventory();
			new BukkitRunnable()
			{
				@Override
				public void run() { process_inventory(inventory); }
			}.runTaskLater(plugin, event.getHandlers().getRegisteredListeners().length + 1);
		}
		catch (Exception ignored) { }
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on_player_pickups_inventory_item(InventoryPickupItemEvent event)
	{
		try
		{
			final Inventory inventory = event.getInventory();
			new BukkitRunnable()
			{
				@Override
				public void run() { process_inventory(inventory); }
			}.runTaskLater(plugin, event.getHandlers().getRegisteredListeners().length + 1);
		}
		catch (Exception ignored) { }
	}
}
