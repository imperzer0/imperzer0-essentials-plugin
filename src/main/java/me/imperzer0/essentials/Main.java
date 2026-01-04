package me.imperzer0.essentials;


import me.imperzer0.essentials.commands.*;
import me.imperzer0.essentials.crafts.*;
import me.imperzer0.essentials.listeners.*;
import me.imperzer0.essentials.utils.InvseeUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class Main extends JavaPlugin
{
    private static Main instance;


    static class ConfigWrapper
    {
        public final String YML;
        private File file;
        private FileConfiguration config;

        ConfigWrapper(String YML)
        {
            this.YML = YML;
            this.file = null;
            this.config = null;
        }


        public void saveDefaultConfig(JavaPlugin plugin)
        {
            file = new File(plugin.getDataFolder(), YML);

            if (!file.exists())
                plugin.saveResource(YML, false);
        }


        public void saveConfig(JavaPlugin plugin) throws NullPointerException
        {
            if (file == null)
                file = new File(plugin.getDataFolder(), YML);

            if (config == null)
                throw new NullPointerException("saveConfig(): FileConfiguration{ } is null. Can not save!");

            try
            {
                InputStream defaults = plugin.getResource(YML);
                if (defaults == null)
                {
                    plugin.getLogger().warning("saveConfig(): Resource '" + YML + "' is null.");
                    return;
                }
                Reader defaults_stream = new InputStreamReader(defaults, StandardCharsets.UTF_8);
                config.setDefaults(YamlConfiguration.loadConfiguration(defaults_stream));
            } catch (Exception e)
            {
                plugin.getLogger().log(Level.SEVERE, "saveConfig(): Failed to save '" + YML + "'.", e);
            }

            config.options().copyDefaults(true);

            try
            {
                config.save(file);
            } catch (IOException var2)
            {
                plugin.getLogger().log(Level.SEVERE, "Could not save config to " + file, var2);
            }
        }


        public void reloadConfig(JavaPlugin plugin)
        {
            if (file == null)
                file = new File(plugin.getDataFolder(), YML);

            config = YamlConfiguration.loadConfiguration(file);

            try
            {
                InputStream defaults = plugin.getResource(YML);
                if (defaults == null)
                {
                    plugin.getLogger().warning("reloadConfig(): Resource '" + YML + "' is null.");
                    return;
                }
                Reader defaults_stream = new InputStreamReader(defaults, StandardCharsets.UTF_8);
                config.setDefaults(YamlConfiguration.loadConfiguration(defaults_stream));
            } catch (Exception e)
            {
                plugin.getLogger().log(Level.SEVERE, "reloadConfig(): Failed to reload '" + YML + "'.", e);
            }
        }

        public FileConfiguration getConfig(JavaPlugin plugin)
        {
            if (config == null)
                reloadConfig(plugin);
            return config;
        }
    }

    public static final String INVENTORIES_YML = "inventories.yml";
    private ConfigWrapper inventories_config = new ConfigWrapper(INVENTORIES_YML);

    public static final String SKINS_YML = "skins.yml";
    private ConfigWrapper skins_config = new ConfigWrapper(SKINS_YML);

    public static final String BAGS_YML = "bags.yml";
    private ConfigWrapper bags_config = new ConfigWrapper(BAGS_YML);

    public static final String DEAD_INVENTORIES_YML = "dead_inventories.yml";
    private ConfigWrapper dead_inventories_config = new ConfigWrapper(DEAD_INVENTORIES_YML);

    public static final String OFFLINE_GAMEMODES_YML = "offline_gamemodes.yml";
    private ConfigWrapper offline_gamemodes_config = new ConfigWrapper(OFFLINE_GAMEMODES_YML);

    public static final String CHECKPOINTS_YML = "checkpoints.yml";
    private ConfigWrapper checkpoints_config = new ConfigWrapper(CHECKPOINTS_YML);


    public Main()
    {
        instance = this;
    }

    public static Main getInstance()
    {
        return instance;
    }


    public FileConfiguration get_inventories_config()
    {
        return inventories_config.getConfig(this);
    }

    public FileConfiguration get_skins_config()
    {
        return skins_config.getConfig(this);
    }

    public FileConfiguration get_bags_config()
    {
        return bags_config.getConfig(this);
    }

    public FileConfiguration get_dead_inventories_config()
    {
        return dead_inventories_config.getConfig(this);
    }

    public FileConfiguration get_offline_gamemodes_config()
    {
        return offline_gamemodes_config.getConfig(this);
    }

    public FileConfiguration get_checkpoints_config()
    {
        return checkpoints_config.getConfig(this);
    }


    /// Extract default configs
    public void saveDefaultConfig()
    {
        super.saveDefaultConfig();

        inventories_config.saveDefaultConfig(this);
        skins_config.saveDefaultConfig(this);
        bags_config.saveDefaultConfig(this);
        dead_inventories_config.saveDefaultConfig(this);
        offline_gamemodes_config.saveDefaultConfig(this);
        checkpoints_config.saveDefaultConfig(this);
    }

    ///  Reload Config from disk
    public void reloadConfig()
    {
        super.reloadConfig();

        inventories_config.reloadConfig(this);
        skins_config.reloadConfig(this);
        bags_config.reloadConfig(this);
        dead_inventories_config.reloadConfig(this);
        offline_gamemodes_config.reloadConfig(this);
        checkpoints_config.reloadConfig(this);
    }


    /// Before enabled
    @Override
    public void onLoad()
    {
        getLogger().info("====================== Loading plugin " + getName() + " v" + getDescription().getVersion() + " ... ======================");
        saveDefaultConfig();
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
        if (config.getBoolean("modules.anvil_max_level", false)) new EnchantmentsListener(manager);
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
        if (config.getBoolean("modules.get", false)) new Get();
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
        if (config.getBoolean("modules.checkpoints", false)) new Checkpoint();


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

        saveConfig();

        InvseeUtils.server_reload();
    }

    @Override
    public void saveConfig()
    {
        getConfig().options().copyDefaults(true);
        super.saveConfig();

        inventories_config.saveConfig(this);
        skins_config.saveConfig(this);
        bags_config.saveConfig(this);
        dead_inventories_config.saveConfig(this);
        offline_gamemodes_config.saveConfig(this);
        checkpoints_config.saveConfig(this);
    }
}
