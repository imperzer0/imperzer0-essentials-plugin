package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.commands.OfflineGM;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import static me.imperzer0.essentials.utils.Loger.loger;

public class OfflineGMListener extends Listener
{
	public OfflineGMListener(@NotNull PluginManager manager)
	{
		super(manager);
	}

	@EventHandler
	void on_join(@NotNull PlayerJoinEvent event)
	{
		String gm = OfflineGM.gamemodes.get(event.getPlayer().getUniqueId());
		if (gm != null)
		{
			event.getPlayer().setGameMode(GameMode.valueOf(gm));
			loger.message(Bukkit.getConsoleSender(), ChatColor.GRAY + "Changed \"" +
					ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.GRAY + "\"'s GameMode to \"" +
					ChatColor.LIGHT_PURPLE + event.getPlayer().getGameMode().toString().toLowerCase() + ChatColor.GRAY + "\".");
			OfflineGM.gamemodes.put(event.getPlayer().getUniqueId(), null);
			OfflineGM.save_config_vals();
		}
	}
}
