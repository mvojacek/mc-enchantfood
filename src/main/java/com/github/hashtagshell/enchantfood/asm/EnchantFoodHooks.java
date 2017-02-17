package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.ench.EnchantmentFood;
import com.github.hashtagshell.enchantfood.init.ModEnchantments;

public class EnchantFoodHooks
{
    @SuppressWarnings("unused") //used in dynamically generated code
    public static int processItemFoodHealAmount(int healAmount, ItemStack stack)
    {
        int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.nutritious, stack);
        return healAmount + EnchantmentFood.foodBonus(healAmount, lvl);
    }

    @SuppressWarnings("unused") //used in dynamically generated code
    public static float processItemFoodSaturationAmount(float saturationAmount, ItemStack stack)
    {
        int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.saturating, stack);
        return saturationAmount + EnchantmentFood.saturationBonus(saturationAmount, lvl);
    }

    @SuppressWarnings("unused") //used in dynamically generated code
    public static int processItemFoodMaxUseDuration(int duration, ItemStack stack)
    {
        int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.digestible, stack);
        int result = duration - EnchantmentFood.itemUseDurationDiscount(duration, lvl);
        if (result < Conf.General.foodUseTicksMin) result = Conf.General.foodUseTicksMin;
        return result;
    }
}
