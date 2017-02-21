package com.github.hashtagshell.enchantfood.handler;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.hashtagshell.enchantfood.ench.PropertyPotionEffect;
import com.github.hashtagshell.enchantfood.utility.NBT;

public class LivingHandler
{
    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish e)
    {
        if (e.getEntity().world.isRemote) return;
        ItemStack stack = e.getItem();
        if (!(stack.getItem() instanceof ItemFood) || !NBT.hasModTag(stack)) return;
        PropertyPotionEffect prop = PropertyPotionEffect.fromStack(stack);
        prop.applyToEntity(e.getEntityLiving(), stack);
    }
}
