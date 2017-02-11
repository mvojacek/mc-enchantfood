package com.github.hashtagshell.enchantfood.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import com.github.hashtagshell.enchantfood.client.render.ItemRenderRegister;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);

        ItemRenderRegister.init();
    }
}
