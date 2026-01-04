package me.imperzer0.essentials.utils;

import me.imperzer0.essentials.Main;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;


public class CheckpointUtils
{
    private static final Map<UUID, Map<String, Location>> checkpoints = new HashMap<>();

    public static boolean Load_checkpoints()
    {
        try
        {

            for (String uuid_str : Objects.requireNonNull(Main.getInstance().get_checkpoints_config().getKeys(false)))
            {
                UUID uuid = UUID.fromString(uuid_str);
                ConfigurationSection section = Main.getInstance().get_checkpoints_config().getConfigurationSection(uuid_str);
                if (section == null) continue;

                Map<String, Location> points = new HashMap<>();

                for (String name : section.getKeys(false))
                {
                    Location pos = section.getLocation(name);

                    if (pos != null)
                        points.put(name, pos);
                }

                checkpoints.put(uuid, points);
            }
            return true;
        } catch (Exception e)
        {
            Main.getInstance().getLogger().log(Level.SEVERE, "Failed to load checkpoints into a structure: " + e.getClass().getName(), e);
            return false;
        }
    }

    public static void Save_checkpoints()
    {
        for (Map.Entry<UUID, Map<String, Location>> points : checkpoints.entrySet())
            for (Map.Entry<String, Location> p : points.getValue().entrySet())
                Main.getInstance().get_checkpoints_config().set(points.getKey() + "." + p.getKey(), p.getValue());
        Main.getInstance().saveConfig();
    }


    @NotNull
    public static LinkedList<String> Get_checkpoint_names(UUID uuid, String filter)
    {
        Map<String, Location> points = CheckpointUtils.checkpoints.get(uuid);
        if (points == null)
            return new LinkedList<>();

        LinkedList<String> names = new LinkedList<>();
        for (String name : points.keySet())
        {
            if (filter == null)
                names.add(name);
            else if (name.contains(filter))
                names.add(name);
        }

        return names;
    }

    @NotNull
    public static CoordLists Get_checkpoint_coords(UUID uuid, String filter)
    {
        Map<String, Location> points = CheckpointUtils.checkpoints.get(uuid);
        if (points == null)
            return new CoordLists();

        CoordLists lists = new CoordLists();
        for (Location pos : points.values())
        {
            if (filter == null)
            {
                lists.X.add(Double.toString(pos.getX()));
                lists.Y.add(Double.toString(pos.getY()));
                lists.Z.add(Double.toString(pos.getZ()));
                lists.Yaw.add(Float.toString(pos.getYaw()));
                lists.Pitch.add(Float.toString(pos.getPitch()));
            }
            else
            {
                String x = Double.toString(pos.getX());
                String y = Double.toString(pos.getY());
                String z = Double.toString(pos.getZ());
                String yaw = Double.toString(pos.getYaw());
                String pitch = Double.toString(pos.getPitch());

                if (x.startsWith(filter)) lists.X.add(x);
                if (y.startsWith(filter)) lists.Y.add(y);
                if (z.startsWith(filter)) lists.Z.add(z);
                if (yaw.startsWith(filter)) lists.Yaw.add(yaw);
                if (pitch.startsWith(filter)) lists.Pitch.add(pitch);
            }
        }

        return lists;
    }

    public static class CoordLists
    {
        private LinkedList<String> X = new LinkedList<>();
        private LinkedList<String> Y = new LinkedList<>();
        private LinkedList<String> Z = new LinkedList<>();
        private LinkedList<String> Yaw = new LinkedList<>();
        private LinkedList<String> Pitch = new LinkedList<>();

        public LinkedList<String> X()
        {
            return this.X;
        }

        public LinkedList<String> Y()
        {
            return this.Y;
        }

        public LinkedList<String> Z()
        {
            return this.Z;
        }

        public LinkedList<String> Yaw()
        {
            return this.Yaw;
        }

        public LinkedList<String> Pitch()
        {
            return this.Pitch;
        }
    }

    public static void Set_checkpoint(UUID uuid, String name, Location pos)
    {
        Map<String, Location> points = checkpoints.get(uuid);
        if (points == null)
        {
            Main.getInstance().getLogger().log(Level.INFO, "points == null");
            Map<String, Location> p = new HashMap<>();
            checkpoints.put(uuid, p);

            points = checkpoints.get(uuid);
            Main.getInstance().getLogger().log(Level.INFO, "points = {" + points + "}[" + points.size() + "]");
        }

        points.put(name, pos);
        Save_checkpoints();
    }

    public static Location Get_checkpoint(UUID uuid, String name)
    {
        Map<String, Location> points = checkpoints.get(uuid);
        if (points == null)
            return null;
        return points.get(name);
    }

    public static void Set_death_location(UUID uuid, Location pos)
    {
        Set_checkpoint(uuid, "Death", pos);
    }

    public static Location Get_death_location(UUID uuid)
    {
        return Get_checkpoint(uuid, "Death");
    }

    public static Map<UUID, Map<String, Location>> checkpoints()
    {
        return checkpoints;
    }
}
