package com.github.hashtagshell.enchantfood.recipes;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class RecipeEssenceInfusion {
    public int essenceCost;
    public ItemStack craftingReagment;
    public ItemStack result;
    public ArrayList<ItemStack> infuses;
    public boolean isInfusing = false;

    public RecipeEssenceInfusion(ItemStack craftingReagment, ItemStack result, int essenceCost) {
        this.craftingReagment = craftingReagment;
        this.essenceCost = essenceCost;
        this.result = result;
    }

    public RecipeEssenceInfusion(ItemStack craftingReagment, ItemStack result, int essenceCost, ArrayList<ItemStack> infuses) {
        this(craftingReagment, result, essenceCost);
        this.infuses = infuses;
        this.isInfusing = true;
    }
}
