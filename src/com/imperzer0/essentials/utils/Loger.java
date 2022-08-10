package com.imperzer0.essentials.utils;

import com.imperzer0.essentials.Main;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class Loger
{
	public final Main plugin;
	private final Logger logger;
	
	public Loger(@NotNull Main plugin)
	{
		this.plugin = plugin;
		this.logger = plugin.getLogger();
	}
	
	public static @NotNull TextComponent create_component(String str, net.md_5.bungee.api.ChatColor color,
	                                                      boolean underlined, boolean italic, boolean bold,
	                                                      boolean strikethrough)
	{
		return new TextComponent(new ComponentBuilder(str)
				                         .color(color).underlined(underlined).italic(italic).bold(bold)
				                         .strikethrough(strikethrough).create());
	}
	
	public static @NotNull TextComponent create_component(String str, net.md_5.bungee.api.ChatColor color)
	{
		return new TextComponent(new ComponentBuilder(str).color(color).create());
	}
	
	public static @NotNull TextComponent create_component(String str)
	{
		return new TextComponent(new ComponentBuilder(str).create());
	}
	
	public Loger info(String message)
	{
		logger.info(ChatColor.YELLOW + "" + ChatColor.ITALIC + message + ChatColor.RESET);
		return this;
	}
	
	public Loger warning(String message)
	{
		logger.warning(ChatColor.YELLOW + "" + ChatColor.BOLD + message + ChatColor.RESET);
		return this;
	}
	
	public Loger severe(String message)
	{
		logger.severe(ChatColor.RED + "" + ChatColor.BOLD + message + ChatColor.RESET);
		return this;
	}
	
	public Loger fine(String message)
	{
		logger.fine(ChatColor.GREEN + message + ChatColor.RESET);
		return this;
	}
	
	public Loger no_permissions(@NotNull CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You don't have permissions to run this command." +
		                   ChatColor.RESET);
		return this;
	}
	
	public Loger invalid_entity(@NotNull CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You are an unexpected entity." + ChatColor.RESET);
		return this;
	}
	
	public Loger help(@NotNull CommandSender sender, Command cmd, @NotNull String args_usage)
	{
		sender.sendMessage(ChatColor.BOLD + "" + ChatColor.AQUA + "/" + ChatColor.LIGHT_PURPLE + cmd + ChatColor.RESET +
		                   ChatColor.YELLOW + " " + args_usage + ChatColor.RESET);
		return this;
	}
	
	public Loger help(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull TextComponent... args_usage)
	{
		TextComponent[] tcs = new TextComponent[args_usage.length + 1];
		tcs[0] = new TextComponent(
				ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "/" + ChatColor.AQUA + cmd + ChatColor.RESET +
				ChatColor.YELLOW + " ");
		System.arraycopy(args_usage, 0, tcs, 1, args_usage.length);
		sender.spigot().sendMessage(tcs);
		return this;
	}
	
	public Loger error(@NotNull CommandSender sender, @NotNull String what)
	{
		sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "An error occurred: " + ChatColor.ITALIC + what +
		                   ChatColor.RESET);
		return this;
	}
	
	public Loger error(@NotNull CommandSender sender, @NotNull TextComponent message)
	{
		message.setColor(net.md_5.bungee.api.ChatColor.RED);
		message.setItalic(true);
		sender.spigot().sendMessage(message);
		return this;
	}
	
	public Loger message(@NotNull CommandSender sender, @NotNull String message)
	{
		sender.sendMessage(message + ChatColor.RESET);
		return this;
	}
	
	public Loger message(@NotNull CommandSender sender, @NotNull TextComponent... message)
	{
		sender.spigot().sendMessage(message);
		return this;
	}
}
