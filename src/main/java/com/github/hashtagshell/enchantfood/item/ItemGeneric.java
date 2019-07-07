package com.github.hashtagshell.enchantfood.item;

import net.minecraft.item.Item;

import com.github.hashtagshell.enchantfood.init.ModCreativeTabs;
import com.github.hashtagshell.enchantfood.init.RegisterMethods;
import com.github.hashtagshell.enchantfood.reference.Ref;

public class ItemGeneric extends Item
{
    public ItemGeneric(String name)
    {
        super();
        setCreativeTab(ModCreativeTabs.main);
        setRegistryName(name);
        setUnlocalizedName(Ref.Mod.ID + "." + getRegistryName().getResourcePath());
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
