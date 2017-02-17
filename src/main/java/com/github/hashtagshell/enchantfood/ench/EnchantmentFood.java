package com.github.hashtagshell.enchantfood.ench;

import net.minecraft.enchantment.Enchantment;
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
        return MathHelper.ceil(foodHeal * lvl * Conf.Enchants.modifierNutritious) + foodBonus(foodHeal, lvl - 1);
    }

    public static float saturationBonus(float foodSaturation, int lvl)
    {
        if (lvl == 0) return 0;
        return MathHelper.ceil(foodSaturation * lvl * Conf.Enchants.modifierNutritious) + saturationBonus(foodSaturation, lvl - 1);
    }

    public static int itemUseDurationDiscount(int duration, int lvl)
    {
        if (lvl == 0) return 0;
        return MathHelper.ceil(duration * lvl * Conf.Enchants.modifierDigestible) + itemUseDurationDiscount(duration, lvl - 1);

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

        // The default impl by Object compares references, which is fine, but if devs add a different way
        // to compare enchantments it will surely be used to override Object.equals()
        return !equals(ench);
    }

    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() instanceof ItemFood;
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
