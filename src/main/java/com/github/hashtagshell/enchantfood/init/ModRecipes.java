package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.recipe.RecipeFoodEnchanter;

import java.util.ArrayList;

public class ModRecipes
{
    public static ArrayList<RecipeFoodEnchanter> FoodEnchanterRecipes = new ArrayList<>();

    public static void init() {
        registerFoodRecipe("bread", 10, 50);
    }

    private static void registerFoodRecipe(String item, int enchantibility, int cost) {
        FoodEnchanterRecipes.add(new RecipeFoodEnchanter(item, enchantibility, cost));
    }
}
