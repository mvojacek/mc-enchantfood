package com.github.hashtagshell.enchantfood.recipes;

import net.minecraft.item.Item;

public class RecipeFoodInfusion {
    public Item input;
    public int inputCount;
    public Item output;
    public int outputCount;
    public int essenceCost;

    public RecipeFoodInfusion(Item input, int inputCount, Item output, int outputCount, int essenceCost) {
        this.essenceCost = essenceCost;
        this.input = input;
        this.output = output;
        this.inputCount = inputCount;
        this.outputCount = outputCount;
    }
}
