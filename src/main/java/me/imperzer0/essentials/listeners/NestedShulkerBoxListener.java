package me.imperzer0.essentials.listeners;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class NestedShulkerBoxListener extends Listener
{
	public NestedShulkerBoxListener(@NotNull PluginManager manager)
	{
		super(manager);
	}

	private @Nullable ArrayList<ItemStack> get_shulkerboxes_from_inventory(@NotNull ItemStack item)
	{
		if (!(item.getItemMeta() instanceof BlockStateMeta meta))
			return null;

		if (!(meta.getBlockState() instanceof ShulkerBox box))
			return null;

		ArrayList<ItemStack> boxes = new ArrayList<>();

		for (ItemStack i : box.getInventory().getContents())
		{
			if (i == null)
				continue;
			if (i.getType().equals(Material.SHULKER_BOX))
				boxes.add(i);
		}

		return boxes.isEmpty() ? null : boxes;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	void shulkerbox_click(@NotNull InventoryClickEvent event)
	{
		if (!(event.getView().getBottomInventory().getHolder() instanceof Player player))
			return;

		ItemStack item = player.getItemOnCursor();

		if (event.isShiftClick())
		{
			if (!item.getType().equals(Material.SHULKER_BOX))
			{
				item = event.getCurrentItem();
				if (item == null)
					return;

				if (!item.getType().equals(Material.SHULKER_BOX))
					return;
			}

			ArrayList<ItemStack> inner_boxes = get_shulkerboxes_from_inventory(item);
			if (inner_boxes != null)
				for (ItemStack box : inner_boxes)
					if (get_shulkerboxes_from_inventory(box) != null)
						return;

			Inventory inventory = event.getClickedInventory();
			if (inventory == null)
				return;

			Inventory top_inventory = event.getView().getTopInventory();

			if (!(inventory instanceof CraftInventoryPlayer) ||
					top_inventory == inventory || top_inventory instanceof CraftInventoryPlayer)
				return;

			int pos = 0;
			for (; pos < top_inventory.getSize(); ++pos)
				if (top_inventory.getItem(pos) == null)
					break;

			if (pos >= top_inventory.getSize())
				return;

			ItemStack item2 = inventory.getItem(event.getSlot());
			inventory.setItem(event.getSlot(), null);
			top_inventory.setItem(pos, item2);
		}
		else
		{
			if (!item.getType().equals(Material.SHULKER_BOX))
				return;

			ArrayList<ItemStack> inner_boxes = get_shulkerboxes_from_inventory(item);
			if (inner_boxes != null)
				for (ItemStack box : inner_boxes)
					if (get_shulkerboxes_from_inventory(box) != null)
						return;

			Inventory inventory = event.getClickedInventory();
			if (inventory == null)
				return;

			if (inventory instanceof CraftInventoryPlayer)
				return;

			ItemStack item2 = inventory.getItem(event.getSlot());
			inventory.setItem(event.getSlot(), item);
			player.setItemOnCursor(item2);
		}

		event.setCancelled(true);
	}
}
