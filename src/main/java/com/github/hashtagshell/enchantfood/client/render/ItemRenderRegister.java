package com.github.hashtagshell.enchantfood.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemRenderRegister
{
    private static List<Item> scheduled = new ArrayList<>();

    private static void reg(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                 .register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    public static void schedule(Item item)
    {
        scheduled.add(item);
    }

    public static void init()
    {
        scheduled.forEach(ItemRenderRegister::reg);
    }
}
