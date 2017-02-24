package com.github.hashtagshell.enchantfood.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import com.github.hashtagshell.enchantfood.reference.Ref.Mod;

public class ModCreativeTabs
{
    public static CreativeTabs main;

    static {
        main = new CreativeTabs(Mod.ID + "_main") {
            @Override
            public ItemStack getTabIconItem()
            {
                return new ItemStack(ModItems.test);
            }
        };
    }
}
