package com.github.hashtagshell.enchantfood.item.food;

import net.minecraft.item.ItemFood;

import com.github.hashtagshell.enchantfood.init.ModCreativeTab;
import com.github.hashtagshell.enchantfood.init.RegisterMethods;

public class FoodGeneric extends ItemFood
{
    public FoodGeneric(String name, int amount, float saturation, boolean isWolfFood)
    {
        super(amount, saturation, isWolfFood);
        setCreativeTab(ModCreativeTab.main);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public FoodGeneric(String name, int amount, boolean isWolfFood)
    {
        super(amount, isWolfFood);
        setCreativeTab(ModCreativeTab.main);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public FoodGeneric register(boolean enable)
    {
        return RegisterMethods.Items.register(this, enable);
    }

    public FoodGeneric register()
    {
        return RegisterMethods.Items.register(this);
    }
}
