package com.github.hashtagshell.enchantfood.item.food;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import com.github.hashtagshell.enchantfood.ench.EnchantmentNutritious;
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

        int nutritious = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.nutritious, stack);

        return base + EnchantmentNutritious.applyWithModifier(nutritious);
    }

    @Override
    public float getSaturationModifier(ItemStack stack)
    {
        return super.getSaturationModifier(stack);
    }

    @Override
    public boolean isWolfsFavoriteMeat()
    {
        return super.isWolfsFavoriteMeat();
    }
}
