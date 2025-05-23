package me.imperzer0.essentials;


import me.imperzer0.essentials.commands.*;
import me.imperzer0.essentials.crafts.*;
import me.imperzer0.essentials.listeners.*;
import me.imperzer0.essentials.utils.InvseeUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin
{
	private static Main instance;


	private File inventories_file;
	private FileConfiguration inventories_config;

	private File skins_file;
	private FileConfiguration skins_config;

	private File bags_file;
	private FileConfiguration bags_config;

	private File dead_inventories_file;
	private FileConfiguration dead_inventories_config;

	private File offline_gamemodes_file;
	private FileConfiguration offline_gamemodes_config;


	public Main()
	{
		instance = this;
	}

	public static Main getInstance()
	{
		return instance;
	}


	private static void save_config_to_file(@NotNull FileConfiguration config, File file, Logger logger)
	{
		config.options().copyDefaults(true);
		try
		{
			config.save(file);
		} catch (IOException var2)
		{
			logger.log(Level.SEVERE, "Could not save config to " + file, var2);
		}
	}


	private void create_inventories_config()
	{
		inventories_file = new File(getDataFolder(), "inventories.yml");
		if (!this.inventories_file.exists())
			this.saveResource("inventories.yml", false);

		inventories_config = YamlConfiguration.loadConfiguration(inventories_file);
	}

	private void create_skins_config()
	{
		skins_file = new File(getDataFolder(), "skins.yml");
		if (!this.skins_file.exists())
			this.saveResource("skins.yml", false);

		skins_config = YamlConfiguration.loadConfiguration(skins_file);
	}

	private void create_bags_config()
	{
		bags_file = new File(getDataFolder(), "bags.yml");
		if (!this.bags_file.exists())
			this.saveResource("bags.yml", false);

		bags_config = YamlConfiguration.loadConfiguration(bags_file);
	}

	private void create_dead_inventories_config()
	{
		dead_inventories_file = new File(getDataFolder(), "dead_inventories.yml");
		if (!this.dead_inventories_file.exists())
			this.saveResource("dead_inventories.yml", false);

		dead_inventories_config = YamlConfiguration.loadConfiguration(dead_inventories_file);
	}

	private void create_offline_gamemodes_config()
	{
		offline_gamemodes_file = new File(getDataFolder(), "offline_gamemodes.yml");
		if (!this.offline_gamemodes_file.exists())
			this.saveResource("offline_gamemodes.yml", false);

		offline_gamemodes_config = YamlConfiguration.loadConfiguration(offline_gamemodes_file);
	}

	public FileConfiguration get_inventories_config()
	{
		return this.inventories_config;
	}

	public FileConfiguration get_skins_config()
	{
		return this.skins_config;
	}

	public FileConfiguration get_bags_config()
	{
		return this.bags_config;
	}

	public FileConfiguration get_dead_inventories_config()
	{
		return this.dead_inventories_config;
	}

	public FileConfiguration get_offline_gamemodes_config()
	{
		return this.offline_gamemodes_config;
	}


	/// Extract default configs
	public void save_default_configs()
	{
		saveDefaultConfig();

		create_inventories_config();
		create_skins_config();
		create_bags_config();
		create_dead_inventories_config();
		create_offline_gamemodes_config();
	}


	/// Before enabled
	@Override
	public void onLoad()
	{
		getLogger().info("====================== Loading plugin " + getName() + " v" + getDescription().getVersion() + " ... ======================");
		save_default_configs();
	}

	/// When enabled
	@Override
	public void onEnable()
	{
		getLogger().info("======================== Enabled plugin " + getName() + " v" + getDescription().getVersion() + " ========================");

		FileConfiguration config = super.getConfig();
		PluginManager manager = getServer().getPluginManager();


		/// Listeners
		new SkinListener(manager);
		new EnchantmentsListener(manager);
		if (config.getBoolean("modules.owner-kit", false)) new OwnerEnchantedKitListener(manager);
		if (config.getBoolean("modules.bag", false)) new BagListener(manager);
		if (config.getBoolean("modules.ret", false)) new RetListener(manager);
		if (config.getBoolean("modules.offlinegm", false)) new OfflineGMListener(manager);
		if (config.getBoolean("modules.invsee", false)) new InvseeListener(manager);
		if (config.getBoolean("modules.dead_inventory", false)) new DeadInventoryListener(manager);
		if (config.getBoolean("modules.boost_librarian", false)) new BoostLibrarianListener(manager);
		if (config.getBoolean("modules.boost_minecart", false)) new BoostMinecartListener(manager);
		if (config.getBoolean("modules.better_looting", false)) new BetterLootingListener(manager);
		if (config.getBoolean("modules.nested_shulkerboxes", false)) new NestedShulkerBoxListener(manager);


		/// Commands
		new me.imperzer0.essentials.commands.Main();
		if (config.getBoolean("modules.give", false)) new Give();
		if (config.getBoolean("modules.fly", false)) new Fly();
		if (config.getBoolean("modules.owner-kit", false)) new OwnerEnchantedKit();
		if (config.getBoolean("modules.remove-owner-kit", false)) new RemoveOwnerEnchantedKit();
		if (config.getBoolean("modules.gamemode", false)) new Gamemode();
		if (config.getBoolean("modules.mkstack", false)) new MkStack();
		if (config.getBoolean("modules.bag", false)) new Bag();
		if (config.getBoolean("modules.uuid", false)) new GetUUID();
		if (config.getBoolean("modules.ret", false)) new Ret();
		if (config.getBoolean("modules.offlinegm", false)) new OfflineGM();
		if (config.getBoolean("modules.invsee", false)) new Invsee();
		if (config.getBoolean("modules.ench", false)) new Ench();
		if (config.getBoolean("modules.dead_inventory", false)) new DeadInventory();
		if (config.getBoolean("modules.boost_librarian", false)) new BoostLibrarian();


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
		getLogger().info("========================= Disabled plugin " + getName() + " v" + getDescription().getVersion() + " =======================");
		InvseeUtils.server_reload();
	}

	@Override
	public void saveConfig()
	{
		this.getConfig().options().copyDefaults(true);
		super.saveConfig();

		save_config_to_file(inventories_config, inventories_file, getLogger());
		save_config_to_file(skins_config, skins_file, getLogger());
		save_config_to_file(bags_config, bags_file, getLogger());
		save_config_to_file(dead_inventories_config, dead_inventories_file, getLogger());
		save_config_to_file(offline_gamemodes_config, offline_gamemodes_file, getLogger());
	}
}
