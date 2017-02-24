package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.ench.EnchantmentFood;

import static com.github.hashtagshell.enchantfood.config.Conf.Enchants.*;

public class ModEnchantments
{
    public static EnchantmentFood nutritious;
    public static EnchantmentFood saturating;
    public static EnchantmentFood digestible;
    public static EnchantmentFood alwaysEdible;
    public static EnchantmentFood notNutritious;
    public static EnchantmentFood notSaturating;
    public static EnchantmentFood notEdible;

    public static void preInit()
    {
        nutritious = new EnchantmentFood("nutritious", 3).register(enableNutritious);
        saturating = new EnchantmentFood("saturating", 3).register(enableSaturating);
        digestible = new EnchantmentFood("digestible", 3).register(enableDigestible);
        alwaysEdible = new EnchantmentFood("alwaysedible", 1).register(enableAlwaysEdible);
        notNutritious = new EnchantmentFood("notnutritious", 1).addIncompE(nutritious).register(enableNotNutritious);
        notSaturating = new EnchantmentFood("notsaturating", 1).addIncompE(saturating).register(enableNotSaturating);
        notEdible = new EnchantmentFood("notedible", 1).register(enableNotEdible);
    }
}
