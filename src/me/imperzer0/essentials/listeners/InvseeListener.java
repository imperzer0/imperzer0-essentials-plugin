package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.InvseeUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class InvseeListener implements Listener
{
	@EventHandler
	public void player_join(@NotNull PlayerJoinEvent event)
	{
		ArrayList<UUID> list = InvseeUtils.get_users_who_opened_archive(event.getPlayer());
		try
		{
			for (UUID id : list)
				Objects.requireNonNull(Bukkit.getPlayer(id)).closeInventory();
		}
		catch (Exception ignored)
		{
		}
		Inventory inventory = InvseeUtils.get_archived_inventory(event.getPlayer());
		if (inventory == null)
		{
			return;
		}
		int sz = event.getPlayer().getInventory().getType().getDefaultSize();
		for (int i = 0; i < sz; ++i)
			event.getPlayer().getInventory().setItem(i, inventory.getItem(i));
	}

	@EventHandler
	public void player_leave(@NotNull PlayerQuitEvent event)
	{
		InvseeUtils.archive_player_inventory(event.getPlayer());
	}

	@EventHandler
	public void on_player_interact_inv(@NotNull InventoryClickEvent event)
	{
		try
		{
			Inventory inventory = event.getView().getTopInventory();
			if (inventory.getHolder() instanceof InvseeUtils.InvHolder)
			{
				if (event.getSlot() >= 41 && event.getSlot() < 45)
				{
					event.setCancelled(true);
					return;
				}

				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						InvseeUtils.save_archive();
					}
				}.runTaskLater(Main.getInstance(), 5);
			}
			else if (inventory.getHolder() instanceof InvseeUtils.InvArmorHolder)
			{
				if (event.getSlot() >= 5 && event.getSlot() < 9)
				{
					event.setCancelled(true);
					return;
				}

				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						InvseeUtils.apply_armor(Bukkit.getPlayer(((InvseeUtils.InvArmorHolder) inventory.getHolder()).owner), inventory);
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	@EventHandler
	public void on_player_interact_inv(@NotNull InventoryCloseEvent event)
	{
		try
		{
			Inventory inventory = event.getView().getTopInventory();
			if (inventory.getHolder() instanceof InvseeUtils.InvHolder)
			{
				InvseeUtils.save_archive();
			}
			else if (inventory.getHolder() instanceof InvseeUtils.InvArmorHolder)
			{
				InvseeUtils.apply_armor(Bukkit.getPlayer(((InvseeUtils.InvArmorHolder) inventory.getHolder()).owner), inventory);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	@EventHandler
	public void on_player_interact_inv(@NotNull InventoryDragEvent event)
	{

		try
		{
			Inventory inventory = event.getView().getTopInventory();
			if (inventory.getHolder() instanceof InvseeUtils.InvHolder)
			{
				for (Integer s : event.getRawSlots())
					if (s >= 41 && s < 45)
					{
						event.setCancelled(true);
						return;
					}

				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						InvseeUtils.save_archive();
					}
				}.runTaskLater(Main.getInstance(), 5);
			}
			else if (inventory.getHolder() instanceof InvseeUtils.InvArmorHolder)
			{
				for (Integer s : event.getRawSlots())
					if (s >= 5 && s < 9)
					{
						event.setCancelled(true);
						return;
					}

				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						InvseeUtils.apply_armor(Bukkit.getPlayer(((InvseeUtils.InvArmorHolder) inventory.getHolder()).owner), inventory);
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	@EventHandler
	public void on_player_interact_inv(@NotNull InventoryPickupItemEvent event)
	{
		try
		{
			Inventory inventory = event.getInventory();
			if (inventory.getHolder() instanceof InvseeUtils.InvHolder)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						InvseeUtils.save_archive();
					}
				}.runTaskLater(Main.getInstance(), 5);
			}
			else if (inventory.getHolder() instanceof InvseeUtils.InvArmorHolder)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						InvseeUtils.apply_armor(Bukkit.getPlayer(((InvseeUtils.InvArmorHolder) inventory.getHolder()).owner), inventory);
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
		catch (Exception ignored)
		{
		}
	}
}
