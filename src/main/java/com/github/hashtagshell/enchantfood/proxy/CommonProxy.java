package com.github.hashtagshell.enchantfood.proxy;


import net.minecraftforge.fml.common.event.*;

import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.init.*;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;

public class CommonProxy implements IProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent e)
    {
        Config.preInit(e);

        ModEnchantments.preInit();

        ModItems.preInit();

        NetworkWrapper.preInit();

        ModEvents.preInit();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        ModIntegration.init();
    }

    @Override
    public void serverStarting(FMLServerStartingEvent e)
    {
        ModCommands.init(e.getServer());
    }
}
