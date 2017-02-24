package com.github.hashtagshell.enchantfood.proxy.extra;

import net.minecraft.client.resources.I18n;

public interface ClientProxyExtra extends CommonProxyExtra
{
    @Override
    default String translatef(String key, Object... args)
    {
        return I18n.format(key, args);
    }

    @Override
    default String translate(String key)
    {
        return I18n.format(key);
    }
}
