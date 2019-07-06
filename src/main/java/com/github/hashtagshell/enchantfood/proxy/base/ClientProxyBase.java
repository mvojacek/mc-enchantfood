package com.github.hashtagshell.enchantfood.proxy.base;

import com.github.hashtagshell.enchantfood.client.render.ItemRenderRegister;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public interface ClientProxyBase extends CommonProxyBase
{
    @Override
    default void init(FMLInitializationEvent e)
    {
        CommonProxyBase.super.init(e);

        ItemRenderRegister.init();
    }
}
