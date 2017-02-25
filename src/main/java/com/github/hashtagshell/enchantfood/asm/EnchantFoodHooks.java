package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.ench.EnchantmentFood;
import com.github.hashtagshell.enchantfood.ench.PropertyPotionEffect;
import com.github.hashtagshell.enchantfood.init.ModEnchantments;

public class EnchantFoodHooks
{
    @SuppressWarnings("unused") //used in dynamically generated code
    public static int processItemFoodHealAmount(int healAmount, ItemStack stack)
    {
        if (0 < EnchantmentHelper.getEnchantmentLevel(ModEnchantments.notNutritious, stack))
            return 0;
        int lvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.nutritious, stack);
        return healAmount + EnchantmentFood.foodBonus(healAmount, lvl);
    }

    @SuppressWarnings("unused") //used in dynamically generated code
    public static float processItemFoodSaturationAmount(float saturationAmount, ItemStack stack)
    {
        if (0 < EnchantmentHelper.getEnchantmentLevel(ModEnchantments.notSaturating, stack))
            return 0F;
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

    @SuppressWarnings("unused") //used in dynamically generated code
    public static boolean processItemFoodCanAlwaysEat(boolean canAlwaysEat, ItemStack stack)
    {
        return canAlwaysEat
               || 0 != EnchantmentHelper.getEnchantmentLevel(ModEnchantments.alwaysEdible, stack)
               || 0 != EnchantmentHelper.getEnchantmentLevel(ModEnchantments.notNutritious, stack);
    }

    @SuppressWarnings("unused") //used in dynamically generated code
    public static boolean processItemStackIsItemEnchanted(boolean isEnchanted, ItemStack stack)
    {
        return isEnchanted
               || PropertyPotionEffect.tagPresent(stack) && !PropertyPotionEffect.fromStack(stack).isEmpty()
               || Conf.Stupid.shiny == Conf.Enums.Shiny.ON
               || (Conf.Stupid.shiny == Conf.Enums.Shiny.FLASHY && shouldFlashStack(stack));
    }

    private static boolean shouldFlashStack(ItemStack stack)
    {
        if (Minecraft.getMinecraft().world == null) return false;
        long time = Minecraft.getMinecraft().world.getTotalWorldTime();
        int phase = (int) (time % 38 >= 19 ? 19 - (time % 38 - 19) : time % 38); // oscillate 0 -> 19 -> 0 -> 19... without dupes
        char[] name = stack.getDisplayName().toCharArray();
        int ch = stack.getCount();
        if (name.length != 0) ch ^= name[phase * name.length / 20];
        ch = ch & 0b1111 * 20 >> 4;
        return ch > phase;
    }
}
