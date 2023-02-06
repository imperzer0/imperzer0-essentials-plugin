package me.imperzer0.essentials.utils;

import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InvseeUtils
{
	/// All users who opened archived inventory of specific player
	public static final Map<UUID, ArrayList<UUID>> opened_archive = new HashMap<>();
	/// Loaded inventories
	public static final Map<UUID, Inventory> archive = new HashMap<>();

	public static void load_archive()
	{
		try
		{
			Map<String, Object> serialized_archive = Main.getInstance().get_inventories_config().getConfigurationSection("").getValues(false);
			for (Map.Entry<String, Object> inv : serialized_archive.entrySet())
			{
				UUID owner = UUID.fromString(inv.getKey());
				ArrayList<ItemStack> inv_serial = (ArrayList<ItemStack>) inv.getValue();

				Inventory inventory = new InvseeUtils.InvHolder(owner).getInventory();
				for (int i = 0; i < InventoryType.PLAYER.getDefaultSize(); ++i)
					inventory.setItem(i, inv_serial.get(i));
				InvseeUtils.put_archive(owner, inventory);
			}
		}
		catch (Exception ignored)
		{
		}
	}

	public static @NotNull Inventory load_armor(@NotNull Player player)
	{
		Inventory inventory = new InvseeUtils.InvArmorHolder(player).getInventory();

		ItemStack[] armor = player.getInventory().getArmorContents();
		for (int i = 0; i < armor.length; ++i)
		{
			inventory.setItem(i, armor[i]);
		}

		return inventory;
	}

	public static ArrayList<UUID> get_users_who_opened_archive(@NotNull OfflinePlayer player)
	{
		return opened_archive.getOrDefault(player.getUniqueId(), new ArrayList<>());
	}

	public static Inventory get_archived_inventory(@NotNull OfflinePlayer player)
	{
		return archive.get(player.getUniqueId());
	}

	public static void put_archive(@NotNull UUID owner, @NotNull Inventory inventory)
	{
		archive.put(owner, inventory);
	}

	public static void add_current_archive_viewer(@NotNull OfflinePlayer owner, @NotNull Player viewer)
	{

		ArrayList<UUID> opened = InvseeUtils.get_users_who_opened_archive(owner);
		opened.add(viewer.getUniqueId());
		opened_archive.put(owner.getUniqueId(), opened);
	}

	public static void save_archive()
	{
		for (Map.Entry<UUID, Inventory> inv : archive.entrySet())
			Main.getInstance().get_inventories_config().set(inv.getKey().toString(), inv.getValue().getContents());
		Main.getInstance().saveConfig();
	}

	public static void server_reload()
	{
		for (Player p : Bukkit.getOnlinePlayers())
			archive_player_inventory(p);
	}

	public static void archive_player_inventory(@NotNull Player player)
	{
		Inventory inventory = player.getInventory();
		Inventory inventory1 = new InvHolder(player.getUniqueId()).getInventory();
		for (int i = 0; i < inventory.getType().getDefaultSize(); ++i)
		{
			inventory1.setItem(i, inventory.getItem(i));
		}
		archive.put(player.getUniqueId(), inventory1);
		save_archive();
	}

	public static void apply_armor(Player player, Inventory inventory)
	{
		ItemStack[] armor = new ItemStack[4];
		for (int i = 0; i < armor.length; ++i)
		{
			armor[i] = inventory.getItem(i);
		}
		player.getInventory().setArmorContents(armor);
	}

	public static class InvHolder implements InventoryHolder
	{
		public final UUID owner;
		private final Inventory inventory;

		public InvHolder(UUID owner)
		{
			this.owner = owner;
			this.inventory = Bukkit.createInventory(this, 45, ChatColor.BLUE + "(archive) " + ChatColor.RESET + "Player");
		}

		@NotNull
		@Override
		public Inventory getInventory()
		{
			return inventory;
		}
	}

	public static class InvArmorHolder implements InventoryHolder
	{
		public final UUID owner;
		private final Inventory inventory;

		public InvArmorHolder(@NotNull Player player)
		{
			this.owner = player.getUniqueId();
			this.inventory = Bukkit.createInventory(this, 9, player.getDisplayName() + "'s armor");
		}

		@NotNull
		@Override
		public Inventory getInventory()
		{
			return inventory;
		}
	}
}
