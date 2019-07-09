package com.github.hashtagshell.enchantfood.proxy.base;

import com.github.hashtagshell.enchantfood.client.render.BlockRenderRegister;
import com.github.hashtagshell.enchantfood.client.render.ItemRenderRegister;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface ClientProxyBase extends CommonProxyBase
{
    @Override
    default void preInit(FMLPreInitializationEvent e) {
        CommonProxyBase.super.preInit(e);

        BlockRenderRegister.preinit();
    }

    @Override
    default void init(FMLInitializationEvent e)
    {
        CommonProxyBase.super.init(e);

        ItemRenderRegister.init();
    }
}
