package me.imperzer0.essentials.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static me.imperzer0.essentials.Main.plugin;

public class SkinUtils
{
	public static void set_skin_of_for(@NotNull Player _of, @NotNull Player _for, @NotNull Property skin)
	{
		CraftPlayer ofp = (CraftPlayer)_of;
		CraftPlayer forp = (CraftPlayer)_for;
		
		GameProfile profile = forp.getProfile();
		PlayerConnection c = forp.getHandle().b;
		
		c.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, ofp.getHandle()));
		
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", skin);
		
		c.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, ofp.getHandle()));
	}
	
	public static @NotNull Property generate_property(@NotNull String texture_value, @NotNull String texture_signature)
	{
		return new Property("textures", texture_value, texture_signature);
	}
	
	public static String get_texture_value(UUID uuid)
	{
		return plugin.getConfig().getString("skins." + uuid + ".value", null);
	}
	
	public static String get_texture_signature(UUID uuid)
	{
		return plugin.getConfig().getString("skins." + uuid + ".signature", null);
	}
	
	@Contract(" -> new")
	public static @NotNull @Unmodifiable List<UUID> list_configured_players()
	{
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("skins");
		if (section != null)
		{
			Iterator<String> iterator = section.getKeys(false).iterator();
			ArrayList<UUID> list = new ArrayList<>();
			while (iterator.hasNext())
				list.add(UUID.fromString(iterator.next()));
			return list;
		}
		else return List.of();
	}
	
	public static void apply_skin(@NotNull Player player)
	{
		String val = get_texture_value(player.getUniqueId());
		String sig = get_texture_signature(player.getUniqueId());
		System.out.println("val = " + val + ";\nsig = " + sig + ";");
		if (val != null && sig != null)
			for (Player p : Bukkit.getOnlinePlayers())
				set_skin_of_for(player, p, generate_property(val, sig));
		
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (!player.getUniqueId().equals(p.getUniqueId()))
			{
				String pval = get_texture_value(p.getUniqueId());
				String psig = get_texture_signature(p.getUniqueId());
				System.out.println("pval = " + pval + ";\npsig = " + psig + ";");
				System.out.println("Setting skin of " + p.getName() + " for " + player.getName() + " ...");
				if (pval != null && psig != null)
					set_skin_of_for(p, player, generate_property(pval, psig));
			}
		}
	}
}
