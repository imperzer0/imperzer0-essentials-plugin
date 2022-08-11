package com.imperzer0.essentials.utils;

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
}
