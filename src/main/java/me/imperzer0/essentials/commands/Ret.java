package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.utils.CheckpointUtils;
import me.imperzer0.essentials.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Ret extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
    public static final String NAME = "ret";
    public static final String USAGE = "";
    public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

    public Ret()
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

        if (!(sender instanceof Player player))
        {
            loger.invalid_entity(sender);
            return false;
        }

        Location location = CheckpointUtils.Get_death_location(player.getUniqueId());
        if (location == null)
        {
            loger.error(sender, "" + ChatColor.ITALIC + ChatColor.GRAY + "Die first!!!");
            return false;
        }

        player.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
        loger.message(sender, ChatColor.GRAY + "Teleported to " + ChatColor.ITALIC + ChatColor.LIGHT_PURPLE + location);
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
                                      @NotNull String[] args)
    {
        if (args.length == 0)
            return PlayerUtils.Bukkit_getAllPlayersIdentifiers(null);
        else if (args.length == 1)
            return PlayerUtils.Bukkit_getAllPlayersIdentifiers(args[0]);
        else return null;
    }
}
