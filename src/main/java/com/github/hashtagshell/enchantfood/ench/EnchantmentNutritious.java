package com.github.hashtagshell.enchantfood.ench;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import com.github.hashtagshell.enchantfood.item.food.ItemEnchantableFood;

public class EnchantmentNutritious extends Enchantment
{
    public static final float modifier = 1;

    public EnchantmentNutritious(String name)
    {
        //noinspection ConstantConditions
        super(Rarity.COMMON, null, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
        setName(name);
        setRegistryName(name);
    }


    @Override
    public int getMaxLevel()
    {
        return 3;
    }


    @Override
    public boolean canApplyTogether(Enchantment ench)
    {
        //TODO Actually logic for enchantment compatibility
        return true;
    }

    /**
     * This applies specifically to applying at the enchanting table. The other method {@link #canApply(ItemStack)}
     * applies for <i>all possible</i> enchantments.
     *
     * @param stack
     * @return
     */
    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return canApply(stack);
    }

    /**
     * Determines if this enchantment can be applied to a specific ItemStack.
     *
     * @param stack
     */
    @Override
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() instanceof ItemEnchantableFood;
    }

    public static int applyWithModifier(int lvl)
    {
        return lvl == 0 ? 0 : (int) (lvl * modifier) + 1;
    }
}
