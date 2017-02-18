package com.github.hashtagshell.enchantfood.potion.food;

import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.hashtagshell.enchantfood.ench.PropertyPotionEffect;

public class FoodPotionTooltipHandler
{
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent e)
    {
        //TODO only show if shift is pressed
        e.getToolTip().addAll(PropertyPotionEffect.fromStack(e.getItemStack()).getToolTip());
    }
}
