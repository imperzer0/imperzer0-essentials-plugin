package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public abstract class Listener implements org.bukkit.event.Listener
{
	protected Listener(@NotNull PluginManager manager)
	{
		manager.registerEvents(this, Main.getInstance());
		Main.getInstance().getLogger().info("Loaded listener [" + getClass().getName() + "].");
	}
}
