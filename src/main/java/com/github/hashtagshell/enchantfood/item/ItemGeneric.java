package com.github.hashtagshell.enchantfood.item;

import net.minecraft.item.Item;

import com.github.hashtagshell.enchantfood.init.ModCreativeTabs;
import com.github.hashtagshell.enchantfood.init.RegisterMethods;

public class ItemGeneric extends Item
{
    public ItemGeneric(String name)
    {
        super();
        setCreativeTab(ModCreativeTabs.main);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public ItemGeneric register()
    {
        return RegisterMethods.Items.register(this);
    }

    public ItemGeneric register(boolean enable)
    {
        return RegisterMethods.Items.register(this, enable);
    }
}
