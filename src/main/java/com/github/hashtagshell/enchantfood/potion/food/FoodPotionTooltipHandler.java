package com.github.hashtagshell.enchantfood.potion.food;

import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.hashtagshell.enchantfood.ench.PropertyPotionEffect;

public class FoodPotionTooltipHandler
{
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent e)
    {
        //TODO only show if shift is pressed
        //TODO insert after enchantments, not at the end
        if (e.getItemStack().getItem() instanceof ItemFood && PropertyPotionEffect.tagPresent(e.getItemStack()))
            e.getToolTip().addAll(PropertyPotionEffect.fromStack(e.getItemStack()).getToolTip());
    }
}
