package me.imperzer0.essentials.utils;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.commands.Bag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BagUtils
{
	public static final String CONFIG_PATH = "BagOf_";
	private static final Map<UUID, Inventory> inventories = new HashMap<>();

	private static @Nullable ItemStack[] load_inventory(@NotNull Main plugin, @NotNull UUID owner)
	{
		try
		{
			List<ItemStack> list = (List<ItemStack>) Main.getInstance().getConfig().getList(CONFIG_PATH + owner);
			assert list != null;
			return list.toArray(new ItemStack[list.size()]);
		}
		catch (Exception e)
		{
			return null;
		}
	}

	public static void save_inventory(@NotNull UUID owner, ItemStack... items)
	{
		Main.getInstance().getConfig().set(CONFIG_PATH + owner, items);
		Main.getInstance().saveConfig();
	}

	public static Inventory open_bag(@NotNull UUID owner)
	{
		if (inventories.containsKey(owner)) return inventories.get(owner);
		else
		{
			ItemStack[] items = load_inventory(Main.getInstance(), owner);
			Inventory inventory = new BagInventoryHolder(owner).getInventory();
			inventory.setMaxStackSize(127);
			if (items != null) inventory.setContents(items);
			inventories.put(owner, inventory);
			return inventory;
		}
	}

	public static void clear_bag(@NotNull CommandSender sender, @NotNull UUID owner)
	{
		if (sender.hasPermission(Bag.PERMISSION_CLEAR + owner) ||
				sender.hasPermission(Bag.PERMISSION_CLEAR + "all"))
		{
			inventories.remove(owner);
			save_inventory(owner);
		}
	}

	public static class BagInventoryHolder implements InventoryHolder
	{
		private final UUID uuid;
		private final Inventory inventory;

		public BagInventoryHolder(UUID uuid)
		{
			this.uuid = uuid;
			this.inventory = Bukkit.createInventory(
					this, 54, ChatColor.DARK_GREEN + "Bag of \"" +
							ChatColor.LIGHT_PURPLE + Bukkit.getOfflinePlayer(uuid).getName() + ChatColor.DARK_GREEN + "\"");
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
