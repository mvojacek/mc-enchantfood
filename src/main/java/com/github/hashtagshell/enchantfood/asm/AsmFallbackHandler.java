package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.hashtagshell.enchantfood.asm.config.AsmConf;
import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.init.ModEnchantments;
import com.github.hashtagshell.enchantfood.init.RegisterMethods;

public class AsmFallbackHandler
{
    public static class FallbackItemFoodMaxUseDuration
    {
        @SubscribeEvent
        public static void handleMaxItemUseDuration(LivingEntityUseItemEvent.Start e)
        {
            if ((e.getItem().getItem() instanceof ItemFood))
                if (0 < EnchantmentHelper.getEnchantmentLevel(ModEnchantments.notEdible, e.getItem()))
                    e.setDuration(-1);
                else
                    e.setDuration(EnchantFoodHooks.processItemFoodMaxUseDuration(e.getDuration(), e.getItem()));
        }
    }

    public static class FallbackHealSaturation
    {
        private static boolean transformHeal       = false;
        private static boolean transformSaturation = false;


        @SubscribeEvent
        public static void handleHealSaturation(LivingEntityUseItemEvent.Finish e)
        {
            if (e.getItem().getItem() instanceof ItemFood && e.getEntityLiving() instanceof EntityPlayer)
            {
                FoodStats food = ((EntityPlayer) e.getEntityLiving()).getFoodStats();
                if (transformHeal)
                    food.setFoodLevel(EnchantFoodHooks.processItemFoodHealAmount(food.getFoodLevel(), e.getItem()));
                if (transformSaturation)
                    food.setFoodSaturationLevel(EnchantFoodHooks.processItemFoodSaturationAmount(food.getSaturationLevel(), e.getItem()));
            }
        }
    }

    public static void registerFallBackHandlers()
    {
        if ((Conf.Enchants.enableDigestible || Conf.Enchants.enableNotEdible)
            && (!AsmConf.transform_ALL || !AsmConf.transform_C_ItemFood
                || !AsmConf.transform_C_ItemFood_M_GetMaxItemUseDuration))
            RegisterMethods.Events.register(FallbackItemFoodMaxUseDuration.class);

        FallbackHealSaturation.transformHeal
                = Conf.Enchants.enableNutritious && (!AsmConf.transform_ALL
                                                     || !AsmConf.transform_C_ItemFood
                                                     || !AsmConf.transform_C_ItemFood_M_GetHealAmount);
        FallbackHealSaturation.transformSaturation
                = Conf.Enchants.enableSaturating && (!AsmConf.transform_ALL
                                                     || !AsmConf.transform_C_ItemFood
                                                     || !AsmConf.transform_C_ItemFood_M_GetSaturationModifier);
        if (FallbackHealSaturation.transformHeal || FallbackHealSaturation.transformSaturation)
            RegisterMethods.Events.register(FallbackHealSaturation.class);
    }
}
