package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.item.ItemGeneric;
import com.github.hashtagshell.enchantfood.item.food.FoodGeneric;
import com.github.hashtagshell.enchantfood.item.food.ItemEnchantableFood;

public class ModItems
{
    public static ItemGeneric test;
    public static FoodGeneric enchFood;

    public static void preInit()
    {
        //TODO Remove test items
        test = new ItemGeneric("test").register();
        enchFood = new ItemEnchantableFood("enchFood", 1, 1, false).register();
    }
}
