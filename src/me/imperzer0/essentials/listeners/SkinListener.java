package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.utils.SkinUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class SkinListener implements Listener
{
	@EventHandler
	void on_login(@NotNull PlayerJoinEvent event)
	{
		SkinUtils.apply_skin(event.getPlayer());
	}
}
