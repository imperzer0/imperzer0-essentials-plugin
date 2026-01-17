package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.constants.OwnerConstants;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class RemoveOwnerEnchantedKit extends me.imperzer0.essentials.commands.Command
{
    public static final String NAME = "rm_owner_kit";
    public static final String USAGE = "";
    public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

    public RemoveOwnerEnchantedKit()
    {
        super(Objects.requireNonNull(me.imperzer0.essentials.Main.getInstance().getCommand(NAME)), PERMISSION);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
    {
        if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

        if (args.length != 0)
        {
            loger.help(sender, cmd, USAGE);
            return false;
        }

        if (!(sender instanceof HumanEntity human))
        {
            loger.invalid_entity(sender);
            return false;
        }

        PlayerInventory human_inventory = human.getInventory();
        for (ItemStack stack : human_inventory.getContents())
            if (OwnerConstants.is_owner_item(stack))
                human_inventory.remove(stack);
        human_inventory.setArmorContents(null);
        OwnerConstants.clear_owner_effects(human);

        loger.message(sender, ChatColor.GRAY + "Removed owner kit items from " + ChatColor.GOLD + human.getName() + ChatColor.GRAY + ".");
        return true;
    }
}
