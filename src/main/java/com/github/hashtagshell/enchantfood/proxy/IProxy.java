package com.github.hashtagshell.enchantfood.proxy;


import net.minecraftforge.fml.common.event.*;

@SuppressWarnings({"EmptyMethod", "UnusedParameters", "unused"})
public interface IProxy
{
    default void construct(FMLConstructionEvent e) {}

    default void preInit(FMLPreInitializationEvent e) {}

    default void init(FMLInitializationEvent e) {}

    default void postInit(FMLPostInitializationEvent e) {}

    default void serverAboutToStart(FMLServerAboutToStartEvent e) {}

    default void serverStarting(FMLServerStartingEvent e) {}

    default void serverStarted(FMLServerStartedEvent e) {}

    default void serverStopping(FMLServerStoppingEvent e) {}

    default void state(FMLStateEvent e) {}

    default void load(FMLLoadEvent e) {}

    default void loadComplete(FMLLoadCompleteEvent e) {}

    default String translatef(String key, Object... args) { return translate(key); }

    default String translate(String key) {return key; }
}
