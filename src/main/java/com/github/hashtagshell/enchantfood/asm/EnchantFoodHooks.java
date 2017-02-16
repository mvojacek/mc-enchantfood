package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.ench.EnchantmentFood;
import com.github.hashtagshell.enchantfood.init.ModEnchantments;
import com.github.hashtagshell.enchantfood.utility.Log;

public class EnchantFoodHooks
{
    //TODO Remove overly verbose logging

    @SuppressWarnings("unused") //used in dynamically generated code
    public static int processItemFoodHealAmount(int healAmount, ItemStack stack)
    {
        int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.nutritious, stack);
        int result = healAmount + EnchantmentFood.foodBonus(healAmount, lvl);
        Log.infof("[HOOK] %s(%s, %s) -> %s", "processItemFoodHealAmount", healAmount, stack, result);
        return result;
    }

    @SuppressWarnings("unused") //used in dynamically generated code
    public static float processItemFoodSaturationAmount(float saturationAmount, ItemStack stack)
    {
        int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.saturating, stack);
        float result = saturationAmount + EnchantmentFood.saturationBonus(saturationAmount, lvl);
        Log.infof("[HOOK] %s(%s, %s) -> %s", "processItemFoodSaturationAmount", saturationAmount, stack, result);
        return result;
    }

    @SuppressWarnings("unused") //used in dynamically generated code
    public static int processItemFoodMaxUseDuration(int duration, ItemStack stack)
    {
        int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.digestible, stack);
        int result = duration - EnchantmentFood.itemUseDurationDiscount(duration, lvl);
        if (result < Conf.foodUseTicksMin) result = Conf.foodUseTicksMin;
        Log.infof("[HOOK] %s(%s, %s) -> %s", "processItemFoodMaxUseDuration", duration, stack, result);
        return result;
    }
}
