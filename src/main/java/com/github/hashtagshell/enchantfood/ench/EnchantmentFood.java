package com.github.hashtagshell.enchantfood.ench;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.init.RegisterMethods;
import com.github.hashtagshell.enchantfood.utility.Predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class EnchantmentFood extends Enchantment
{
    public int                          maxLevel     = 1;
    public List<Predicate<Enchantment>> incompatible = new ArrayList<>();

    public EnchantmentFood(String name, int maxLevel)
    {
        //noinspection ConstantConditions
        super(Rarity.COMMON, null, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
        this.maxLevel = maxLevel;
        setName(name);
        setRegistryName(name);
    }

    public EnchantmentFood addIncompE(Predicate<Enchantment> rest)
    {
        incompatible.add(rest);
        return this;
    }

    public EnchantmentFood addIncompE(Enchantment e)
    {
        return addIncompE(en -> en.getName().equals(e.getName()));
    }

    public EnchantmentFood addIncompS(Predicate<String> rest)
    {
        return addIncompE(e -> rest.test(e.getName()));
    }

    public EnchantmentFood addIncompS(String name)
    {
        return addIncompS(name::equals);
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

    {
        addIncompS(getName());
    }

    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        return !Predicates.or(incompatible, ench);
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

    public EnchantmentFood register(boolean enable)
    {
        return RegisterMethods.RegistryObjects.register(this, enable);
    }

    public EnchantmentFood register()
    {
        return RegisterMethods.RegistryObjects.register(this);
    }
}
