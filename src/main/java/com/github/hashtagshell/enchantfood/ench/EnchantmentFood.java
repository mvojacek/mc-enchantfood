package com.github.hashtagshell.enchantfood.ench;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.hashtagshell.enchantfood.config.Conf;

public class EnchantmentFood extends Enchantment
{
    public int maxLevel = 1;

    public EnchantmentFood(String name, int maxLevel)
    {
        //noinspection ConstantConditions
        super(Rarity.COMMON, null, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
        this.maxLevel = maxLevel;
        setName(name);
        setRegistryName(name);
    }

    public static int foodBonus(int foodHeal, int lvl)
    {
        if (lvl == 0) return 0;
        return MathHelper.ceil(foodHeal * lvl * Conf.modifierNutritious) + foodBonus(foodHeal, lvl - 1);
    }

    public static float saturationBonus(float foodSaturation, int lvl)
    {
        if (lvl == 0) return 0;
        return MathHelper.ceil(foodSaturation * lvl * Conf.modifierNutritious) + saturationBonus(foodSaturation, lvl - 1);
    }

    public static int itemUseDurationDiscount(int duration, int lvl)
    {
        if (lvl == 0) return 0;
        return MathHelper.ceil(duration * lvl * Conf.modifierDigestible) + itemUseDurationDiscount(duration, lvl - 1);

    }

    @Override
    public int getMaxLevel()
    {
        return maxLevel;
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        //TODO Actually logic for enchantment compatibility
        return true;
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() instanceof ItemFood && EnchantmentHelper.getEnchantmentLevel(this, stack) == 0;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return canApply(stack);
    }

    public EnchantmentFood register()
    {
        GameRegistry.register(this);
        return this;
    }
}
