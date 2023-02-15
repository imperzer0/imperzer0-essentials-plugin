package me.imperzer0.essentials.crafts;

import me.imperzer0.essentials.Main;
import me.imperzer0.essentials.utils.Pair;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class Craft implements Listener
{
	public final NamespacedKey[] keys;

	protected Craft(@NotNull String @NotNull ... identifiers)
	{
		keys = new NamespacedKey[identifiers.length];
		for (int i = 0; i < identifiers.length; ++i)
			keys[i] = new NamespacedKey(Main.getInstance(), identifiers[i]);

		for (Pair<NamespacedKey, Recipe> r : get_recipes())
		{
			Main.getInstance().getLogger().info("Loaded recipe \"" + r.getKey().getKey() + "\".");
			Bukkit.addRecipe(r.getValue());
		}
	}

	protected abstract Set<Pair<NamespacedKey, Recipe>> get_recipes();
}
