package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.utils.SkinUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class SkinListener extends Listener
{
	public SkinListener(@NotNull PluginManager manager)
	{
		super(manager);
	}

	@EventHandler
	void on_login(@NotNull PlayerJoinEvent event)
	{
		SkinUtils.apply_skin(event.getPlayer());
	}
}
