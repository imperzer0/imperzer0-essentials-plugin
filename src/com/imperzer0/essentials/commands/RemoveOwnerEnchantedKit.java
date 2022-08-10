package com.imperzer0.essentials.commands;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.constants.OwnerConstants;
import com.imperzer0.essentials.utils.Loger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RemoveOwnerEnchantedKit implements CommandExecutor
{
	
	private static final String NAME = "rm_owner_kit";
	private static final String USAGE = "";
	private static final String PERMISSION = "imperzer0-essentials.command.rm_owner_kit";
	
	public final Main plugin;
	private final Loger loger;
	
	public RemoveOwnerEnchantedKit(@NotNull Loger loger)
	{
		this.loger = loger;
		plugin = loger.plugin;
		Objects.requireNonNull(plugin.getCommand(NAME)).setExecutor(this);
	}
	
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
	{
		if (!sender.hasPermission(PERMISSION))
		{
			loger.no_permissions(sender);
			return false;
		}
		
		if (args.length == 0)
			if (sender instanceof HumanEntity human)
			{
				PlayerInventory human_inventory = human.getInventory();
				for (ItemStack stack : human_inventory.getContents())
					if (OwnerConstants.is_owner_item(stack))
						human_inventory.remove(stack);
				human_inventory.setArmorContents(null);
				OwnerConstants.clear_owner_effects(human);
				return true;
			}
			else loger.invalid_entity(sender);
		else loger.help(sender, cmd, USAGE);
		
		return false;
	}
}
