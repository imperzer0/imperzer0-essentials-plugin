package me.imperzer0.essentials.commands;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.CheckpointUtils;
import me.imperzer0.essentials.utils.WorldUtils;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static me.imperzer0.essentials.utils.Loger.loger;

public class Checkpoint extends me.imperzer0.essentials.commands.Command implements TabCompleter
{
    public static final String NAME = "checkpoint";
    public static final String USAGE = "( def <name> [ <world> <x> <y> <z> [<yaw> <pitch>] ] ) / ( tp <name> )";
    public static final String PERMISSION = "imperzer0-essentials.command." + NAME;

    public Checkpoint()
    {
        super(Objects.requireNonNull(me.imperzer0.essentials.Main.getInstance().getCommand(NAME)), PERMISSION);

        if (!CheckpointUtils.Load_checkpoints())
            Main.getInstance().getLogger().log(Level.SEVERE, "Checkpoint(): Could not load checkpoints.");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args)
    {
        if (super.init_command(sender, cmd, args, PERMISSION, USAGE)) return false;

        if (args.length < 2)
        {
            loger.help(sender, cmd, USAGE);
            return false;
        }

        if (!(sender instanceof Player player))
        {
            loger.invalid_entity(sender);
            return false;
        }

        String name = args[1];


        if (!CheckpointUtils.Load_checkpoints())
        {
            loger.error(sender, "Plugin issue: Could not load checkpoints.");
            return false;
        }

        if (Objects.equals(args[0], "def"))
        {
            switch (args.length)
            {
                case 2: // def <name>
                {
                    CheckpointUtils.Set_checkpoint(player.getUniqueId(), name, player.getLocation());
                    loger.message(sender, "Set '" + name + "' to [ " +
                            player.getLocation().getX() + " " +
                            player.getLocation().getY() + " " +
                            player.getLocation().getZ() + " " +
                            player.getLocation().getYaw() + " " +
                            player.getLocation().getPitch() + " ]");
                    break;
                }
                case 6: // def <name> <world> <x> <y> <z>
                {
                    World world = Bukkit.getWorld(args[2]);
                    if (world == null)
                    {
                        loger.error(sender, "World '" + args[2] + "' does not exist!");
                        return false;
                    }

                    try
                    {
                        Location pos = new Location(world, Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
                        CheckpointUtils.Set_checkpoint(player.getUniqueId(), name, pos);

                        loger.message(sender, "Set '" + name + "' to [ " +
                                pos.getX() + " " +
                                pos.getY() + " " +
                                pos.getZ() + " " +
                                pos.getYaw() + " " +
                                pos.getPitch() + " ]");

                        new CheckpointPreview(player, pos).preview().runTaskLater(Main.getInstance(), 80L); // 80t / 20tps = 4sec
                    } catch (NumberFormatException e)
                    {
                        loger.error(sender, "Invalid coordinate values. Use float!");
                        return false;
                    }
                    break;
                }
                case 8: // def <name> <world> <x> <y> <z> <yaw> <pitch>
                {
                    World world = Bukkit.getWorld(args[2]);
                    if (world == null)
                    {
                        loger.error(sender, "World '" + args[2] + "' does not exist!");
                        return false;
                    }

                    try
                    {
                        Location pos = new Location(world, Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]),
                                Float.parseFloat(args[6]), Float.parseFloat(args[7]));
                        CheckpointUtils.Set_checkpoint(player.getUniqueId(), name, pos);

                        loger.message(sender, "Set '" + name + "' to [ " +
                                pos.getX() + " " +
                                pos.getY() + " " +
                                pos.getZ() + " " +
                                pos.getYaw() + " " +
                                pos.getPitch() + " ]");

                        new CheckpointPreview(player, pos).preview().runTaskLater(Main.getInstance(), 80L); // 80t / 20tps = 4sec
                    } catch (NumberFormatException e)
                    {
                        loger.error(sender, "Invalid coordinate values. Use float!");
                        return false;
                    }

                    break;
                }
            }

            CheckpointUtils.Save_checkpoints();
            return true;
        } else if (Objects.equals(args[0], "tp"))
        {
            Location pos = CheckpointUtils.Get_checkpoint(player.getUniqueId(), name);
            if (pos == null)
            {
                loger.message(sender, "Checkpoint '" + name + "' does not exist.");
                return false;
            }
            player.teleport(pos);

            loger.message(sender, "Teleported to '" + name + "' checkpoint.");
            return true;
        }

        loger.help(sender, cmd, USAGE);
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command cmd, @NotNull String label, @NotNull String[] args)
    {
        ArrayList<String> list = new ArrayList<>();
        if (args.length > 2 && args[0].equals("tp"))
            return list; // Do not suggest arguments after 3rd for TP command

        switch (args.length)
        {
            case 0:
            {
                list.add("def");
                list.add("tp");
                break;
            }
            case 1:
            {
                if ("def".contains(args[0])) list.add("def");
                if ("tp".contains(args[0])) list.add("tp");
                break;
            }
            case 2:
            {
                if (sender instanceof Player player)
                    list.addAll(CheckpointUtils.Get_checkpoint_names(player.getUniqueId(), args[1]));
                break;
            }
            case 3:
            {
                list.addAll(WorldUtils.Bukkit_getWorlds(args[2]));
                break;
            }

            case 4:
            {
                if (sender instanceof Player player)
                    list.addAll(CheckpointUtils.Get_checkpoint_coords(player.getUniqueId(), args[3]).X());
                break;
            }
            case 5:
            {
                if (sender instanceof Player player)
                    list.addAll(CheckpointUtils.Get_checkpoint_coords(player.getUniqueId(), args[4]).Y());
                break;
            }
            case 6:
            {
                if (sender instanceof Player player)
                    list.addAll(CheckpointUtils.Get_checkpoint_coords(player.getUniqueId(), args[5]).Z());
                break;
            }
            case 7:
            {
                if (sender instanceof Player player)
                    list.addAll(CheckpointUtils.Get_checkpoint_coords(player.getUniqueId(), args[6]).Yaw());
                break;
            }
            case 8:
            {
                if (sender instanceof Player player)
                    list.addAll(CheckpointUtils.Get_checkpoint_coords(player.getUniqueId(), args[7]).Pitch());
                break;
            }
        }

        return list;
    }

    private static class CheckpointPreview extends BukkitRunnable
    {
        Player player;
        Location pos;
        GameMode initial_gm = GameMode.SURVIVAL;
        Location initial_pos = null;

        public CheckpointPreview(Player player, Location pos)
        {
            this.player = player;
            this.pos = pos;
        }

        @Override
        public void run()
        {
            player.teleport(initial_pos);
            player.setGameMode(initial_gm);
        }

        public CheckpointPreview preview()
        {
            initial_gm = player.getGameMode();
            player.setGameMode(GameMode.SPECTATOR);

            initial_pos = player.getLocation();
            player.teleport(pos);

            return this;
        }
    }
}
