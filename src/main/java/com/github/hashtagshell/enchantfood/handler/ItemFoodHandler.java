package com.github.hashtagshell.enchantfood.handler;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import com.github.hashtagshell.enchantfood.ench.PropertyPotionEffect;
import com.github.hashtagshell.enchantfood.init.ModEnchantments;
import com.github.hashtagshell.enchantfood.utility.ChatColor;
import com.github.hashtagshell.enchantfood.utility.NBT;

import java.util.List;

public class ItemFoodHandler
{
    @SubscribeEvent
    public static void addFoodPotionEffects(LivingEntityUseItemEvent.Finish e)
    {
        if (e.getEntity().world.isRemote) return;
        ItemStack stack = e.getItem();
        if (!(stack.getItem() instanceof ItemFood) || !NBT.hasModTag(stack)) return;
        PropertyPotionEffect prop = PropertyPotionEffect.fromStack(stack);
        prop.applyToEntity(e.getEntityLiving(), stack);
    }

    @SubscribeEvent
    public static void makeTooltip(ItemTooltipEvent e)
    {
        if (e.getItemStack().getItem() instanceof ItemFood && PropertyPotionEffect.tagPresent(e.getItemStack()))
        {
            List<String> tip = PropertyPotionEffect.fromStack(e.getItemStack()).getToolTip(Keyboard.isKeyDown(42)
                                                                                           || Keyboard.isKeyDown(54));
            for (int i = e.getToolTip().size() - 1; i >= 0; i--)
                if (!e.getToolTip().get(i).startsWith(ChatColor.DARK_GRAY.toString()))
                {
                    e.getToolTip().addAll(++i, tip);
                    break;
                }
        }
    }

    @SubscribeEvent
    public static void stopEatingNotEdible(LivingEntityUseItemEvent.Start e)
    {
        if (e.getItem().getItem() instanceof ItemFood
            && 0 < EnchantmentHelper.getEnchantmentLevel(ModEnchantments.notEdible, e.getItem()))
            e.setCanceled(true);
    }
}
