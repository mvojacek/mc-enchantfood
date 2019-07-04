package com.github.hashtagshell.enchantfood.proxy.base;


import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.init.*;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface CommonProxyBase extends IProxyBase
{
    @Override
    default void preInit(FMLPreInitializationEvent e)
    {
        Config.preInit(e);

        ModEnchantments.preInit();

        ModItems.preInit();

        ModBlocks.preInit();

        NetworkWrapper.preInit();

        ModEvents.preInit();
    }

    @Override
    default void init(FMLInitializationEvent e)
    {
        ModRecipes.init();

        ModIntegration.init();
    }

    @Override
    default void serverStarting(FMLServerStartingEvent e)
    {
        ModCommands.init(e.getServer());
    }
}
