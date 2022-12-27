package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.commands.Invsee;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class InvseeListener implements Listener
{
	private static void save_archive()
	{
		for (Map.Entry<UUID, Inventory> inv : Invsee.archive.entrySet())
			Main.getInstance().get_inventories_config().set(inv.getKey().toString(), inv.getValue().getContents());
		Main.getInstance().saveConfig();
	}

	public static void server_reload()
	{
		for (Player p : Bukkit.getOnlinePlayers())
			archive_player_inventory(p);
	}

	private static void archive_player_inventory(Player player)
	{
		Inventory inventory = player.getInventory();
		Inventory inventory1 = Bukkit.createInventory(
				new InvHolder(player.getUniqueId()), (int) Math.ceil((double) inventory.getSize() / 9.0) * 9,
				ChatColor.BLUE + "(archive) " + ChatColor.RESET + "Player"
		);
		for (int i = 0; i < inventory.getType().getDefaultSize(); ++i)
		{
			inventory1.setItem(i, inventory.getItem(i));
		}
		Invsee.archive.put(player.getUniqueId(), inventory1);
		save_archive();
	}

	private static void apply_armor(Player player, Inventory inventory)
	{
		ItemStack[] armor = new ItemStack[4];
		for (int i = 0; i < armor.length; ++i)
		{
			armor[i] = inventory.getItem(i);
		}
		player.getInventory().setArmorContents(armor);
	}

	@EventHandler
	public void player_join(@NotNull PlayerJoinEvent event)
	{
		ArrayList<UUID> list = Invsee.opened_archive.getOrDefault(event.getPlayer().getUniqueId(), new ArrayList<>());
		try
		{
			for (UUID id : list)
				Objects.requireNonNull(Bukkit.getPlayer(id)).closeInventory();
		}
		catch (Exception ignored)
		{
		}
		Inventory inventory = Invsee.archive.getOrDefault(event.getPlayer().getUniqueId(), null);
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
		archive_player_inventory(event.getPlayer());
	}

	@EventHandler
	public void on_player_interact_inv(InventoryClickEvent event)
	{
		try
		{
			Inventory inventory = event.getView().getTopInventory();
			if (inventory.getHolder() instanceof InvHolder)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						save_archive();
					}
				}.runTaskLater(Main.getInstance(), 5);
			}
			else if (inventory.getHolder() instanceof InvHolderArmor)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						apply_armor(Bukkit.getPlayer(((InvHolderArmor) inventory.getHolder()).owner), inventory);
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	@EventHandler
	public void on_player_interact_inv(InventoryCloseEvent event)
	{
		try
		{
			Inventory inventory = event.getView().getTopInventory();
			if (inventory.getHolder() instanceof InvHolder)
			{
				save_archive();
			}
			else if (inventory.getHolder() instanceof InvHolderArmor)
			{
				apply_armor(Bukkit.getPlayer(((InvHolderArmor) inventory.getHolder()).owner), inventory);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	@EventHandler
	public void on_player_interact_inv(InventoryDragEvent event)
	{
		try
		{
			Inventory inventory = event.getView().getTopInventory();
			if (inventory.getHolder() instanceof InvHolder)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						save_archive();
					}
				}.runTaskLater(Main.getInstance(), 5);
			}
			else if (inventory.getHolder() instanceof InvHolderArmor)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						apply_armor(Bukkit.getPlayer(((InvHolderArmor) inventory.getHolder()).owner), inventory);
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	@EventHandler
	public void on_player_interact_inv(InventoryPickupItemEvent event)
	{
		try
		{
			Inventory inventory = event.getInventory();
			if (inventory.getHolder() instanceof InvHolder)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						save_archive();
					}
				}.runTaskLater(Main.getInstance(), 5);
			}
			else if (inventory.getHolder() instanceof InvHolderArmor)
			{
				new BukkitRunnable()
				{
					@Override
					public void run()
					{
						apply_armor(Bukkit.getPlayer(((InvHolderArmor) inventory.getHolder()).owner), inventory);
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	public static class InvHolder implements InventoryHolder
	{
		public final UUID owner;

		public InvHolder(UUID owner)
		{
			this.owner = owner;
		}

		@NotNull
		@Override
		public Inventory getInventory()
		{
			return null;
		}
	}

	public static class InvHolderArmor implements InventoryHolder
	{
		public final UUID owner;

		public InvHolderArmor(UUID owner)
		{
			this.owner = owner;
		}

		@NotNull
		@Override
		public Inventory getInventory()
		{
			return null;
		}
	}
}
