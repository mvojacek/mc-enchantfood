package com.github.hashtagshell.enchantfood.recipe;

public class RecipeFoodEnchanter {
    public RecipeFoodEnchanter(String item, int enchantibility, int requiredFuel) {
        this.enchantable = item;
        this.enchantibility = enchantibility;
        this.requiredFuel = requiredFuel;
    }

    public final String enchantable;
    public final int enchantibility;
    public final int requiredFuel;
}
