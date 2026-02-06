package me.imperzer0.essentials.crafts;

import me.imperzer0.essentials.utils.Pair;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CharcoalBlockCraft extends Craft
{
    public CharcoalBlockCraft()
    {
        super("charcoalblockcraft", "charcoalcraft");
    }

    @EventHandler
    void on_login(@NotNull PlayerLoginEvent event)
    {
        event.getPlayer().discoverRecipes(Arrays.stream(keys).toList());
    }

    @Override
    protected Set<Pair<NamespacedKey, Recipe>> get_recipes()
    {
        Set<Pair<NamespacedKey, Recipe>> recipes = new HashSet<>();
        recipes.add(charcoal_block_recipe());
        return recipes;
    }

    public Pair<NamespacedKey, Recipe> charcoal_block_recipe()
    {
        ItemStack item = new ItemStack(Material.COAL_BLOCK);
        try
        {
            ItemMeta meta = Objects.requireNonNull(item.getItemMeta());
            meta.setDisplayName("Charcoal Block");
            item.setItemMeta(meta);
        } catch (Exception ignored)
        {
        }
        ShapedRecipe recipe = new ShapedRecipe(keys[0], item);
        recipe.shape(
                "CCC",
                "CCC",
                "CCC"
        );

        recipe.setIngredient('C', Material.CHARCOAL);
        return new Pair<>(keys[0], recipe);
    }
}
