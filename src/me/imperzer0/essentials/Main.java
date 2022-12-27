package me.imperzer0.essentials;


import me.imperzer0.essentials.commands.*;
import me.imperzer0.essentials.crafts.*;
import me.imperzer0.essentials.listeners.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Main extends JavaPlugin
{
	private static Main instance;

	private File inventories_file;
	private FileConfiguration inventories_config;

	private File skins_file;
	private FileConfiguration skins_config;

	public Main()
	{
		instance = this;
	}

	public static Main getInstance()
	{
		return instance;
	}

	private void create_inventories_config()
	{
		inventories_file = new File(getDataFolder(), "inventories.yml");
		if (!this.inventories_file.exists())
			this.saveResource("inventories.yml", false);

		inventories_config = YamlConfiguration.loadConfiguration(inventories_file);
	}

	public FileConfiguration get_inventories_config()
	{
		return this.inventories_config;
	}

	private void create_skins_config()
	{
		skins_file = new File(getDataFolder(), "skins.yml");
		if (!this.skins_file.exists())
			this.saveResource("skins.yml", false);

		skins_config = YamlConfiguration.loadConfiguration(skins_file);
	}

	public FileConfiguration get_skins_config()
	{
		return this.skins_config;
	}

	/// Extract default configs
	public void save_default_configs()
	{
		saveDefaultConfig();
		create_inventories_config();
		create_skins_config();
	}

	/// Before enabled
	@Override
	public void onLoad()
	{
		getLogger().info("====================== Loading plugin " + getName() + " v" + getDescription().getVersion() +
				" ... ======================");
		save_default_configs();
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
		if (config.getBoolean("commands.owner-kit", false)) manager.registerEvents(
				new OwnerEnchantedKitListener(), this);
		if (config.getBoolean("commands.bag", false)) manager.registerEvents(new BagListener(), this);
		if (config.getBoolean("commands.ret", false)) manager.registerEvents(new RetListener(), this);
		if (config.getBoolean("commands.offlinegm", false)) manager.registerEvents(new OfflineGMListener(), this);
		if (config.getBoolean("commands.invsee", false)) manager.registerEvents(new InvseeListener(), this);
		manager.registerEvents(new SkinListener(), this);


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
		if (config.getBoolean("commands.offlinegm", false)) new OfflineGM();
		if (config.getBoolean("commands.invsee", false)) new Invsee();


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
		getLogger().info(
				"========================= Disabled plugin " + getName() + " v" + getDescription().getVersion() +
						" =======================");
	}

	@Override
	public void saveConfig()
	{
		this.getConfig().options().copyDefaults(true);
		super.saveConfig();

		try
		{
			this.inventories_config.save(this.inventories_file);
		}
		catch (IOException var2)
		{
			getLogger().log(Level.SEVERE, "Could not save config to " + this.inventories_file, var2);
		}
	}
}
