package com.github.hashtagshell.enchantfood.init;

import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.hashtagshell.enchantfood.ench.EnchantmentNutritious;

public class ModEnchantments
{
    public static EnchantmentNutritious nutritious;

    public static void preInit()
    {
        GameRegistry.register(nutritious = new EnchantmentNutritious("nutritious"));
    }
}
