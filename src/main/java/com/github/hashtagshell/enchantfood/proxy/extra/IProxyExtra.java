package com.github.hashtagshell.enchantfood.proxy.extra;

@SuppressWarnings({"EmptyMethod", "UnusedParameters", "unused"})
public interface IProxyExtra
{
    default String translatef(String key, Object... args) { return translate(key); }

    default String translate(String key)                  {return key; }
}
