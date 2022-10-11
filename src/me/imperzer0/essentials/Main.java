package me.imperzer0.essentials;


import me.imperzer0.essentials.commands.*;
import me.imperzer0.essentials.crafts.*;
import me.imperzer0.essentials.listeners.BagListener;
import me.imperzer0.essentials.listeners.OwnerEnchantedKitListener;
import me.imperzer0.essentials.listeners.RetListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public static Main plugin;
	
	public Main()
	{
		plugin = this;
	}
	
	/// Before enabled
	@Override
	public void onLoad()
	{
		getLogger().info("====================== Loading plugin " + getName() + " v" + getDescription().getVersion() +
		                 " ... ======================");
		saveDefaultConfig();
	}
	
	/// When enabled
	@Override
	public void onEnable()
	{
		getLogger().info("======================== Enabled plugin " + getName() + " v" + getDescription().getVersion() +
		                 " ========================");
		
		FileConfiguration config = super.getConfig();
		PluginManager manager = getServer().getPluginManager();
		
		
		/// Listeners
		if (config.getBoolean("commands.owner-kit", false)) manager.registerEvents(new OwnerEnchantedKitListener(), this);
		if (config.getBoolean("commands.bag", false)) manager.registerEvents(new BagListener(), this);
		if (config.getBoolean("commands.ret", false)) manager.registerEvents(new RetListener(), this);
		
		
		/// Commands
		if (config.getBoolean("commands.give", false)) new Give();
		if (config.getBoolean("commands.fly", false)) new Fly();
		if (config.getBoolean("commands.owner-kit", false)) new OwnerEnchantedKit();
		if (config.getBoolean("commands.remove-owner-kit", false)) new RemoveOwnerEnchantedKit();
		if (config.getBoolean("commands.gamemode", false)) new Gamemode();
		if (config.getBoolean("commands.mkstack", false)) new MkStack();
		if (config.getBoolean("commands.bag", false)) new Bag();
		if (config.getBoolean("commands.uuid", false)) new GetUUID();
		if (config.getBoolean("commands.ret", false)) new Ret();
		
		
		/// Craft recipes
		
		if (config.getBoolean("crafts.debug-stick", false))
			manager.registerEvents(new DebugStickCraft(), this);
		
		if (config.getBoolean("crafts.emerald", false))
			manager.registerEvents(new EmeraldCrafts(), this);
		
		if (config.getBoolean("crafts.enchanted-golden-apple", false))
			manager.registerEvents(new EnchantedGoldenAppleCraft(), this);
		
		if (config.getBoolean("crafts.spawner", false))
			manager.registerEvents(new SpawnerCraft(), this);
		
		if (config.getBoolean("crafts.spawner-eggs", false))
			manager.registerEvents(new SpawnerEggsCrafts(), this);
	}
	
	/// When disabled
	@Override
	public void onDisable()
	{
		getLogger().info("========================= Disabled plugin " + getName() + " v" + getDescription().getVersion() +
		                 " =======================");
	}
	
	@Override
	public void saveConfig()
	{
		this.getConfig().options().copyDefaults(true);
		super.saveConfig();
	}
}
