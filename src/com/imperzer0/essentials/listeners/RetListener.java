package com.imperzer0.essentials.listeners;

import com.imperzer0.essentials.Main;
import com.imperzer0.essentials.commands.Ret;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RetListener implements Listener
{
	private static final Map<UUID, Location> death_location = new HashMap<>();
	public final Main plugin;
	
	public RetListener(Main plugin) { this.plugin = plugin; }
	
	public static Location get_death_location(UUID uuid) { return death_location.get(uuid); }
	
	@EventHandler(priority = EventPriority.LOWEST)
	void on_player_dies(@NotNull PlayerDeathEvent event)
	{
		if (event.getEntity().hasPermission(Ret.PERMISSION))
			death_location.put(event.getEntity().getUniqueId(), event.getEntity().getLocation());
	}
}
