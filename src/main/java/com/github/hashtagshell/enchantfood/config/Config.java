package com.github.hashtagshell.enchantfood.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.github.hashtagshell.enchantfood.reference.Ref;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.io.File;
import java.io.IOException;

public class Config
{
    public static Configuration config;

    public static void preInit(FMLPreInitializationEvent e)
    {
        if (config == null)
        {
            File result;
            try
            {
                File dModConfig = Ref.Files.MOD_CONF_DIR();
                File fConfig = new File(dModConfig, "config.cfg");
                if (!fConfig.exists())
                    //noinspection ResultOfMethodCallIgnored
                    fConfig.createNewFile();
                else if (!fConfig.isFile())
                    throw new IOException("Preferred mod config file, %s, exists but is a directory!");
                result = fConfig;
            }
            catch (IOException ex)
            {
                Log.errorex(ex, "Could not open config file in custom config folder, "
                                + "using suggested config file instead");
                result = e.getSuggestedConfigurationFile();
            }
            config = new Configuration(result);
        }
        applyChanges();
    }

    public static void applyChanges()
    {
        Conf.loadConfiguration();
        saveToFile();
    }

    public static void saveToFile()
    {
        if (config.hasChanged())
            config.save();
    }

    @SuppressWarnings("unused") // called from event bus
    @SubscribeEvent
    public static void onConfigChanged(OnConfigChangedEvent e)
    {
        if (e.getModID().equalsIgnoreCase(Mod.ID))
        {
            applyChanges();
        }
    }
}