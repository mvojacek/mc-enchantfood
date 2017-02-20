package com.github.hashtagshell.enchantfood.handler;

import net.minecraft.item.ItemFood;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.hashtagshell.enchantfood.ench.PropertyPotionEffect;
import com.github.hashtagshell.enchantfood.utility.ChatColor;

import java.util.List;

public class ItemHandler
{
    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent e)
    {
        //TODO only show if shift is pressed
        //TODO insert after enchantments, not at the end
        if (e.getItemStack().getItem() instanceof ItemFood && PropertyPotionEffect.tagPresent(e.getItemStack()))
        {
            List<String> tip = PropertyPotionEffect.fromStack(e.getItemStack()).getToolTip(e.isShowAdvancedItemTooltips());
            for (int i = e.getToolTip().size() - 1; i >= 0; i--)
            {
                if (!e.getToolTip().get(i).startsWith(ChatColor.DARK_GRAY.toString()))
                {
                    e.getToolTip().addAll(++i, tip);
                    break;
                }
            }
        }
    }
}
