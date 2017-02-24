package com.github.hashtagshell.enchantfood.proxy.extra;

public interface ServerProxyExtra extends CommonProxyExtra
{
    @SuppressWarnings("deprecation")
    @Override
    default String translatef(String key, Object... args)
    {
        return net.minecraft.util.text.translation.I18n.translateToLocalFormatted(key, args);
    }

    @SuppressWarnings("deprecation")
    @Override
    default String translate(String key)
    {
        return net.minecraft.util.text.translation.I18n.translateToLocal(key);
    }
}
