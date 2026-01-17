package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Get extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
    public static final String NAME = "get";
    public static final String USAGE = "[ <player> ] [ stack ]";
    public static final String PERMISSION = "imperzer0-essentials.command." + NAME;
    public static final int DISTANCE = 10;

    public Get()
    {
        super(Objects.requireNonNull(Main.getInstance().getCommand(NAME)), PERMISSION);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args)
    {
        if (super.init_command(sender, cmd, args, PERMISSION, USAGE))
            return false;

        Player who;
        boolean stack = false;

        switch (args.length)
        {
            case 0:
            {
                if (!(sender instanceof Player player))
                {
                    loger.invalid_entity(sender);
                    return false;
                }
                who = player;
                break;
            }
            case 1:
            {
                if (args[0].equals("stack"))
                {
                    stack = true;
                    if (!(sender instanceof Player player))
                    {
                        loger.invalid_entity(sender);
                        return false;
                    }
                    who = player;
                }
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null)
                {
                    loger.error(sender, "Player " + args[0] + " not found.");
                    return false;
                }
                who = player;
                break;
            }
            case 2:
            {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null)
                {
                    loger.error(sender, "Player " + args[0] + " not found.");
                    return false;
                }
                who = player;
                stack = args[1].equals("stack");
                break;
            }
            default:
            {
                loger.help(sender, cmd, USAGE);
                return false;
            }
        }

        Block block = who.getTargetBlock(null, DISTANCE);
        int amount = 1;
        if (stack) amount = block.getBlockData().getMaterial().getMaxStackSize();

        who.getInventory().addItem(new ItemStack(block.getBlockData().getMaterial(), amount));

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                                      @NotNull String[] args)
    {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 0)
        {
            list.add("stack");
            list.addAll(PlayerUtils.Bukkit_getAllPlayersIdentifiers(null));
        } else if (args.length == 1)
        {
            list.add("stack");
        }
        return list;
    }
}
