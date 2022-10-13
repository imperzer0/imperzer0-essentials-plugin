package me.imperzer0.essentials.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.level.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import static me.imperzer0.essentials.Main.plugin;

public class SkinUtils
{
	private static void sendPackets(Packet... packets)
	{
		for (Player p : Bukkit.getOnlinePlayers())
		{
			for (Packet packet : packets)
			{
				EntityPlayer nmsPlayer = ((CraftPlayer)p).getHandle();
				
				nmsPlayer.b.a(packet);
			}
		}
	}
	
	private static void sendPacketsNotFor(String notFor, Packet... packets)
	{
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (!p.getName().equals(notFor))
			{
				for (Packet packet : packets)
				{
					EntityPlayer nmsPlayer = ((CraftPlayer)p).getHandle();
					nmsPlayer.b.a(packet);
				}
			}
		}
	}
	
	public static void change_skin(@NotNull Player player, @NotNull Property skin)
	{
		CraftPlayer cp = (CraftPlayer)player;
		
		GameProfile profile = cp.getProfile();
		
		sendPackets(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, cp.getHandle()));
		
		profile.getProperties().removeAll("textures");
		profile.getProperties().put("textures", skin);
		
		sendPackets(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, cp.getHandle()));
	}
	
	public static @Nullable Property generate_property(@NotNull UUID uuid)
	{
		String val = plugin.getConfig().getString("skins." + uuid, null);
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
