package me.imperzer0.essentials.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.imperzer0.essentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_21_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
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

		for (Map.Entry<String, Collection<Property>> prop : profile.getProperties().asMap().entrySet())
		{
			loger.debug("'" + prop.getKey() + "' : ");
			for (Property p : prop.getValue())
                loger.debug("{ name: '" + p.name() + "', value: '" + p.value() + "', signature: '" + p.signature() + "' };");
			loger.debug("");
		}

		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", skin);

		hide_player(player);

		show_player(player);
	}

	public static @Nullable Property load_property(@NotNull UUID uuid)
	{
		String texture = Main.getInstance().get_skins_config().getString(uuid + ".texture", null);
		String signature = Main.getInstance().get_skins_config().getString(uuid + ".signature", null);
		if (texture == null) return null;
		else return new Property("textures", texture, signature);
	}

	public static void apply_skin(@NotNull Player player)
	{
		{
			Property prop = load_property(player.getUniqueId());
			if (prop != null)
            {
                change_skin(player, prop);
            }
			else
			{
				loger.message(player, "[Skin Subsystem] If you want your skin to be different,");
				loger.message(player, "[Skin Subsystem] please, go to https://mineskin.org/, upload");
				loger.message(player, "[Skin Subsystem] your skin texture, copy your skin url and");
				loger.message(player, "[Skin Subsystem] ask your Server Administrator to apply it to");
				loger.message(player, "[Skin Subsystem] your UUID in skins.yml");
			}
		}

		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (!player.getUniqueId().equals(p.getUniqueId()))
			{
				Property prop = load_property(p.getUniqueId());
				if (prop != null)
					change_skin(p, prop);
			}
		}
	}
}
