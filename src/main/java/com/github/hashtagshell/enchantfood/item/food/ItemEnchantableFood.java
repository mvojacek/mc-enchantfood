package com.github.hashtagshell.enchantfood.item.food;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.github.hashtagshell.enchantfood.ench.EnchantmentFood;
import com.github.hashtagshell.enchantfood.init.ModEnchantments;

public class ItemEnchantableFood extends FoodGeneric
{
    public ItemEnchantableFood(String name, int food, float saturation, boolean isWolfFood)
    {
        super(name, food, saturation, isWolfFood);
    }

    @Override
    public int getHealAmount(ItemStack stack)
    {
        int base = super.getHealAmount(stack);

        int nutritiousLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.nutritious, stack);
        base += EnchantmentFood.foodBonus(base, nutritiousLvl);

        return base;
    }

    @Override
    public float getSaturationModifier(ItemStack stack)
    {
        float base = super.getSaturationModifier(stack);

        int saturatingLvl = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.saturating, stack);
        base += EnchantmentFood.saturationBonus(base, saturatingLvl);

        return base;
    }

    @Override
    public boolean isWolfsFavoriteMeat()
    {
        return super.isWolfsFavoriteMeat();
    }
}
