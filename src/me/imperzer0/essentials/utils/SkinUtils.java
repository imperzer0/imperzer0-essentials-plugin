package me.imperzer0.essentials.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
}
