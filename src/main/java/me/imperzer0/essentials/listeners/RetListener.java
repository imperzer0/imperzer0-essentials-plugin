package me.imperzer0.essentials.listeners;

import me.imperzer0.essentials.commands.Ret;
import me.imperzer0.essentials.utils.CheckpointUtils;
import me.imperzer0.essentials.utils.Loger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class RetListener extends Listener
{

    public RetListener(@NotNull PluginManager manager)
    {
        super(manager);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    void on_player_dies(@NotNull PlayerDeathEvent event)
    {
        if (event.getEntity().hasPermission(Ret.PERMISSION))
        {
            CheckpointUtils.Set_death_location(event.getEntity().getUniqueId(), event.getEntity().getLocation());
            Loger.loger.info("Saved death location for '" + event.getEntity().getName() + "'. Location = { " + event.getEntity().getLocation() + " }");
        }
    }
}
