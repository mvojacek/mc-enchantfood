package com.github.hashtagshell.enchantfood.item;

import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBeefStick extends ItemGeneric {
    public ItemBeefStick(String name) {
        super(name);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(Ref.ToolTips.MAGICAL_BEEF_STICK_TOOLTIP);
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
