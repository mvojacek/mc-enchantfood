package com.github.hashtagshell.enchantfood.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.github.hashtagshell.enchantfood.client.render.ItemRenderRegister;
import com.github.hashtagshell.enchantfood.init.ModCreativeTab;

public class ItemGeneric extends Item
{
    public ItemGeneric(String name)
    {
        super();
        setCreativeTab(ModCreativeTab.main);
        setUnlocalizedName(name);
        setRegistryName(name);
    }

    public ItemGeneric register()
    {
        GameRegistry.register(this);
        ItemRenderRegister.schedule(this);
        return this;
    }
}
