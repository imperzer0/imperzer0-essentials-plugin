package me.imperzer0.essentials.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static me.imperzer0.essentials.utils.Loger.loger;


public class SkinUtils
{
	private static void hide_player(@NotNull Player player)
	{
		for (Player p : Bukkit.getOnlinePlayers())
			p.hidePlayer(Main.getInstance(), player);
	}

	private static void show_player(@NotNull Player player)
	{
		for (Player p : Bukkit.getOnlinePlayers())
			p.showPlayer(Main.getInstance(), player);
	}

	public static void change_skin(@NotNull Player player, @NotNull Property skin)
	{
		GameProfile profile = ((CraftPlayer) player).getProfile();

		if (profile == null)
		{
			loger.severe("Couldn't get GameProfile for \"" + player.getName() + "\"");
			return;
		}

		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", skin);

		hide_player(player);

		show_player(player);
	}

	public static @Nullable Property generate_property(@NotNull UUID uuid)
	{
		String val = Main.getInstance().get_skins_config().getString(uuid.toString(), null);
		if (val == null) return null;
		else return new Property("textures", val);
	}

	public static void apply_skin(@NotNull Player player)
	{
		{
			Property prop = generate_property(player.getUniqueId());
			if (prop != null)
				change_skin(player, prop);
		}

		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (!player.getUniqueId().equals(p.getUniqueId()))
			{
				Property prop = generate_property(p.getUniqueId());
				if (prop != null)
					change_skin(p, prop);
			}
		}
	}
}
