package com.github.hashtagshell.enchantfood.recipes;

import net.minecraft.item.ItemStack;

public class RecipeFoodInfusion {
    public ItemStack input;
    public ItemStack output;
    public int essenceCost;

    public RecipeFoodInfusion(ItemStack input, ItemStack output, int essenceCost) {
        this.essenceCost = essenceCost;
        this.input = input;
        this.output = output;
    }
}
