package me.imperzer0.essentials.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class WorldUtils
{
    public static @NotNull LinkedList<String> Bukkit_getWorlds(String filter)
    {
        LinkedList<String> worlds = new LinkedList<>();
        for (World world : Bukkit.getWorlds())
        {
            if (filter == null)
                worlds.add(world.getName());
            else if (world.getName().contains(filter))
                worlds.add(world.getName());
        }

        return worlds;
    }
}
