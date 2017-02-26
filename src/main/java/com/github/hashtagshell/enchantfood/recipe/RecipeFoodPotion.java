package com.github.hashtagshell.enchantfood.recipe;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;


//TODO Maybe do this with the brewing recipe instead? Might make more sense
public class RecipeFoodPotion implements IRecipe
{
    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param inv
     * @param worldIn
     */
    @Override
    public boolean matches(InventoryCrafting inv, World worldIn)
    {
        ItemStack food = null;
        ItemStack ingr = null;
        boolean blaze = false;
        for (int i = 0; i < inv.getSizeInventory(); i++)
        {
            ItemStack is = inv.getStackInSlot(i);
            if (is.isEmpty())
                continue;
            if (!blaze && is.getItem().getRegistryName().equals(Items.BLAZE_POWDER.getRegistryName()))
            {
                blaze = true;
                continue;
            }

            boolean isIngr = BrewingRecipeRegistry.isValidIngredient(is);
            boolean isFood = is.getItem() instanceof ItemFood;
            boolean foodNull = food == null;
            boolean ingrNull = ingr == null;

            if (!foodNull && !ingrNull || !isFood && !isIngr)
                return false;
            if (foodNull && isFood)
                food = is;
            else if (ingrNull && isIngr)
                ingr = is;
        }
        if (!blaze || food == null || ingr == null) return false;

        //TODO somehow get the result of brewing of a potion with the individual effects (+EMPTY) and this ingredient and write the output

        return false;
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param inv
     */
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv)
    {
        return null;
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize()
    {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return null;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        return null;
    }
}
