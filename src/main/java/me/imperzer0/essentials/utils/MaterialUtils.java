package me.imperzer0.essentials.utils;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class MaterialUtils
{
	public static @NotNull LinkedList<String> Material_getAllMaterialsNames(String filter)
	{
		LinkedList<String> names = new LinkedList<>();
		for (Material material : Material.values())
		{
			if (filter == null)
			{
				names.add(material.name());
				names.add(material.name().toLowerCase());
			}
			else if (material.name().contains(filter.toUpperCase()))
			{
				names.add(material.name());
				names.add(material.name().toLowerCase());
			}
		}
		return names;
	}
}
