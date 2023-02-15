package me.imperzer0.essentials.utils;

import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DeadInventoryUtils
{
	static private final Map<UUID, Inventory> inventories = new HashMap<>();

	private static @Nullable ItemStack[] load_inventory(@NotNull UUID owner)
	{
		try
		{
			List<ItemStack> list = (List<ItemStack>) Main.getInstance().get_dead_inventories_config().getList(owner.toString());
			assert list != null;
			return list.toArray(new ItemStack[list.size()]);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static void save_inventory(@NotNull UUID owner, @NotNull Inventory inventory)
	{
		inventories.put(owner, inventory);
		Main.getInstance().get_dead_inventories_config().set(owner.toString(), inventory.getContents());
		Main.getInstance().saveConfig();
	}

	public static Inventory open_dead_inventory(@NotNull UUID owner)
	{
		if (inventories.containsKey(owner)) return inventories.get(owner);
		else
		{
			ItemStack[] items = load_inventory(owner);
			Inventory inventory = new DeadInventoryHolder(owner).getInventory();
			if (items != null) inventory.setContents(items);
			inventories.put(owner, inventory);
			return inventory;
		}
	}

	public static Inventory preserve_inventory(@NotNull Player player, @NotNull List<ItemStack> drops)
	{
		DeadInventoryHolder holder = new DeadInventoryHolder(player.getUniqueId());
		ItemStack[] inv_contents = new ItemStack[drops.size()];
		holder.inventory.setContents(drops.toArray(inv_contents));
		return holder.inventory;
	}

	public static class DeadInventoryHolder implements InventoryHolder
	{
		private final UUID uuid;
		private final Inventory inventory;

		public DeadInventoryHolder(UUID uuid)
		{
			this.uuid = uuid;
			this.inventory = Bukkit.createInventory(
					this, 54, ChatColor.DARK_GREEN + "Dead inventory of \"" +
							ChatColor.LIGHT_PURPLE + Bukkit.getOfflinePlayer(uuid).getName() + ChatColor.DARK_GREEN + "\"");
			this.inventory.setMaxStackSize(127);
		}

		@NotNull
		@Override
		public Inventory getInventory()
		{
			return this.inventory;
		}

		public UUID getUuid()
		{
			return this.uuid;
		}
	}
}
