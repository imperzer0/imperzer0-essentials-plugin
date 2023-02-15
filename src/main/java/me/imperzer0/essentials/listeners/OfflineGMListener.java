package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

	@EventHandler(priority = EventPriority.LOWEST)
	void on_join(@NotNull PlayerJoinEvent event)
	{
		String gm = Main.getInstance().get_offline_gamemodes_config().getString(event.getPlayer().getUniqueId().toString());
		if (gm == null)
			return;

		event.getPlayer().setGameMode(GameMode.valueOf(gm));
		loger.message(Bukkit.getConsoleSender(), ChatColor.GRAY + "Changed \"" +
				ChatColor.YELLOW + event.getPlayer().getName() + ChatColor.GRAY + "\"'s GameMode to \"" +
				ChatColor.LIGHT_PURPLE + event.getPlayer().getGameMode().toString().toLowerCase() + ChatColor.GRAY + "\".");
		Main.getInstance().get_offline_gamemodes_config().set(event.getPlayer().getUniqueId().toString(), null);
		Main.getInstance().saveConfig();
	}
}
