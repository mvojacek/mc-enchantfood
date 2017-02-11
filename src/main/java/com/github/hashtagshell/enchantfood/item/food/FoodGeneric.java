package com.github.hashtagshell.enchantfood.item.food;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.hashtagshell.enchantfood.client.render.ItemRenderRegister;
import com.github.hashtagshell.enchantfood.init.ModCreativeTab;

public class FoodGeneric extends ItemFoodPublic
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

    public FoodGeneric register()
    {
        GameRegistry.register(this);
        ItemRenderRegister.schedule(this);
        return this;
    }
}
