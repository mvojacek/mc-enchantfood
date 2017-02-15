package com.github.hashtagshell.enchantfood.proxy;

import net.minecraft.client.resources.I18n;
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

    @Override
    public String translatef(String key, Object... args)
    {
        return I18n.format(key, args);
    }

    @Override
    public String translate(String key)
    {
        return I18n.format(key);
    }
}
