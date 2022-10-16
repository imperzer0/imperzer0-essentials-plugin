package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.commands.Bag;
import me.imperzer0.essentials.utils.BagUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class BagListener implements Listener
{
	public BagListener() { }
	
	void process_inventory(@NotNull Inventory inventory)
	{
		if (inventory.getHolder() instanceof BagUtils.BagInventoryHolder holder)
			BagUtils.save_inventory(holder.getUuid(), inventory.getContents());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void on_new_player_login(@NotNull PlayerLoginEvent event)
	{
		Permission permission = new Permission(
				Bag.PERMISSION_CLEAR + event.getPlayer().getUniqueId().toString().toLowerCase(),
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
			}.runTaskLater(Main.getInstance(), event.getHandlers().getRegisteredListeners().length + 1);
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
			}.runTaskLater(Main.getInstance(), event.getHandlers().getRegisteredListeners().length + 1);
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
			}.runTaskLater(Main.getInstance(), event.getHandlers().getRegisteredListeners().length + 1);
		}
		catch (Exception ignored) { }
	}
}
