package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.ench.EnchantmentFood;

public class ModEnchantments
{
    public static EnchantmentFood nutritious;
    public static EnchantmentFood saturating;
    public static EnchantmentFood digestible;

    public static void preInit()
    {
        nutritious = new EnchantmentFood("nutritious", 3).register();
        saturating = new EnchantmentFood("saturating", 3).register();
        digestible = new EnchantmentFood("digestible", 3).register();
    }
}
