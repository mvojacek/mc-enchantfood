package com.github.hashtagshell.enchantfood.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.github.hashtagshell.enchantfood.reference.Ref.Mod;

public class Config
{
    public static Configuration config;



    public static void preInit(FMLPreInitializationEvent e)
    {
        if (config == null)
        {
            config = new Configuration(e.getSuggestedConfigurationFile());
        }
        Conf.loadConfiguration();
    }

    public static void saveToFile()
    {
        if (config.hasChanged())
            config.save();
    }

    public static void onConfigChanged(OnConfigChangedEvent e)
    {
        if (e.getModID().equalsIgnoreCase(Mod.ID))
        {
            Conf.loadConfiguration();
            saveToFile();
        }
    }
}
