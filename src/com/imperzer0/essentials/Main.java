package com.imperzer0.essentials;


import com.imperzer0.essentials.commands.Fly;
import com.imperzer0.essentials.commands.Give;
import com.imperzer0.essentials.commands.OwnerEnchantedKit;
import com.imperzer0.essentials.commands.RemoveOwnerEnchantedKit;
import com.imperzer0.essentials.utils.Loger;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	private final Loger loger;
	
	public Main() { this.loger = new Loger(this); }
	
	/// Before enabled
	@Override
	public void onLoad()
	{
		getLogger().info("====================== Loading plugin " + getName() + " v" + getDescription().getVersion() +
		                 " ... ======================");
	}
	
	/// When disabled
	@Override
	public void onDisable()
	{
		getLogger().info("========================= Disabled plugin " + getName() + " v" + getDescription().getVersion() +
		                 " =======================");
	}
	
	/// When enabled
	@Override
	public void onEnable()
	{
		getLogger().info("======================== Enabled plugin " + getName() + " v" + getDescription().getVersion() +
		                 " ========================");
		new Give(loger);
		new Fly(loger);
		new OwnerEnchantedKit(loger);
		new RemoveOwnerEnchantedKit(loger);
	}
	
	@Override
	public void saveConfig()
	{
		this.getConfig().options().copyDefaults(true);
		super.saveConfig();
	}
}
