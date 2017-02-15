package com.github.hashtagshell.enchantfood.proxy;

public class ServerProxy extends CommonProxy
{
    @SuppressWarnings("deprecation")
    @Override
    public String translatef(String key, Object... args)
    {
        return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(key, args);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String translate(String key)
    {
        return net.minecraft.util.text.translation.I18n.translateToLocal(key);
    }
}
