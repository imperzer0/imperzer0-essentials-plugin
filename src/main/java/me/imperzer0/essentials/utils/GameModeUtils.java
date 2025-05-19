package me.imperzer0.essentials.utils;

import org.bukkit.GameMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameModeUtils
{
	public static @NotNull List<String> GameMode_list_all()
	{
		ArrayList<String> result = new ArrayList<>();
		for (GameMode gm : GameMode.values())
		{
			result.add(Integer.toString(gm.getValue()));
			result.add(gm.name());
		}
		return result;
	}

	public static GameMode parse_gamemode(String gamemode)
	{
		GameMode gm;
		try
		{
			gm = GameMode.valueOf(gamemode);
		} catch (IllegalArgumentException e)
		{
			int val;
			try
			{
				val = Integer.parseInt(gamemode);
			} catch (NumberFormatException ex)
			{
				return null;
			}

			gm = GameMode.getByValue(val);
		}

		return gm;
	}
}
