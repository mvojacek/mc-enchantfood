package com.github.hashtagshell.enchantfood.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.hashtagshell.enchantfood.asm.EnchantFoodHooks;
import com.github.hashtagshell.enchantfood.asm.config.AsmConf;
import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.init.RegisterMethods;

public class AsmFallbackHandler
{
    public static class FallbackItemFoodMaxUseDuration
    {
        @SubscribeEvent
        public static void handleMaxItemUseDuration(LivingEntityUseItemEvent.Start e)
        {
            if ((e.getItem().getItem() instanceof ItemFood))
                    e.setDuration(EnchantFoodHooks.processItemFoodMaxUseDuration(e.getDuration(), e.getItem()));
        }
    }

    public static class FallbackHealSaturation
    {
        private static boolean processHeal       = false;
        private static boolean processSaturation = false;


        @SubscribeEvent
        public static void handleHealSaturation(LivingEntityUseItemEvent.Finish e)
        {
            if (e.getItem().getItem() instanceof ItemFood && e.getEntityLiving() instanceof EntityPlayer)
            {
                FoodStats food = ((EntityPlayer) e.getEntityLiving()).getFoodStats();
                if (processHeal)
                    food.setFoodLevel(EnchantFoodHooks.processItemFoodHealAmount(food.getFoodLevel(), e.getItem()));
                if (processSaturation)
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


        FallbackHealSaturation.processHeal
                = Conf.Enchants.enableNutritious && (!AsmConf.transform_ALL
                                                     || !AsmConf.transform_C_ItemFood
                                                     || !AsmConf.transform_C_ItemFood_M_GetHealAmount);
        FallbackHealSaturation.processSaturation
                = Conf.Enchants.enableSaturating && (!AsmConf.transform_ALL
                                                     || !AsmConf.transform_C_ItemFood
                                                     || !AsmConf.transform_C_ItemFood_M_GetSaturationModifier);

        if (FallbackHealSaturation.processHeal || FallbackHealSaturation.processSaturation)
            RegisterMethods.Events.register(FallbackHealSaturation.class);
    }
}
