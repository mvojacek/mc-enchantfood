package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.recipe.RecipeFoodPotion;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Recipe.craft;

public class ModRecipes
{
    public static void init()
    {
        craft(new RecipeFoodPotion());
    }
}
