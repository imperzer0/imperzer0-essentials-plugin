package me.imperzer0.essentials.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PlayerUtils
{
	public static @Nullable Player Bukkit_getPlayer(String identifier)
	{
		Player player = Bukkit.getPlayerExact(identifier);

		try
		{
			if (player == null) player = Bukkit.getPlayer(UUID.fromString(identifier));
		} catch (IllegalArgumentException e)
		{
			return null;
		}

		return player;
	}

	public static @Nullable OfflinePlayer Bukkit_getOfflinePlayer(String identifier)
	{
		OfflinePlayer player = null;
		for (OfflinePlayer p : Bukkit.getOfflinePlayers())
			if (Objects.equals(p.getName(), identifier))
				player = p;

		try
		{
			if (player == null) player = Bukkit.getOfflinePlayer(UUID.fromString(identifier));
		} catch (IllegalArgumentException e)
		{
			return null;
		}

		return player;
	}

	public static @Nullable Player Bukkit_getPlayer(String identifier, Loger loger, CommandSender sender)
	{
		Player player = Bukkit.getPlayerExact(identifier);

		try
		{
			if (player == null) player = Bukkit.getPlayer(UUID.fromString(identifier));
		} catch (IllegalArgumentException e)
		{
			loger.error(sender, "Player identifier \"" + identifier + "\" is invalid.");
			return null;
		}

		return player;
	}

	public static @Nullable OfflinePlayer Bukkit_getOfflinePlayer(String identifier, Loger loger, CommandSender sender)
	{
		OfflinePlayer player = null;
		for (OfflinePlayer p : Bukkit.getOfflinePlayers())
			if (Objects.equals(p.getName(), identifier))
				player = p;

		try
		{
			if (player == null) player = Bukkit.getPlayer(UUID.fromString(identifier));
		} catch (IllegalArgumentException e)
		{
			loger.error(sender, "Player identifier \"" + identifier + "\" is invalid.");
			return null;
		}

		return player;
	}

	public static @NotNull ArrayList<String> Bukkit_getAllPlayersIdentifiers(String filter)
	{
		ArrayList<String> identifiers = new ArrayList<>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers())
			if (filter == null)
			{
				identifiers.add(player.getName());
				identifiers.add(player.getUniqueId().toString());
			}
			else
			{
				if (player.getName() != null && player.getName().contains(filter)) identifiers.add(player.getName());
				if (player.getUniqueId().toString().contains(filter)) identifiers.add(player.getUniqueId().toString());
			}
		return identifiers;
	}

	public static @NotNull ArrayList<String> Bukkit_getAllPlayersUUIDs(String filter)
	{
		ArrayList<String> identifiers = new ArrayList<>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers())
			if (filter == null)
				identifiers.add(player.getUniqueId().toString());
			else if (player.getUniqueId().toString().contains(filter))
				identifiers.add(player.getUniqueId().toString());
		return identifiers;
	}

	public static @NotNull ArrayList<String> Bukkit_getAllPlayersNames(String filter)
	{
		ArrayList<String> identifiers = new ArrayList<>();
		for (OfflinePlayer player : Bukkit.getOfflinePlayers())
			if (filter == null)
				identifiers.add(player.getName());
			else if (player.getName() != null && player.getName().contains(filter))
				identifiers.add(player.getName());
		return identifiers;
	}
}
