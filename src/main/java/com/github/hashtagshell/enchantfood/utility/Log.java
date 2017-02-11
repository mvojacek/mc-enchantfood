package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

import com.github.hashtagshell.enchantfood.reference.Ref.Mod;

public class Log
{
    private static String name = Mod.ID;

    public static void logf(String modName, Level logLevel, String format, Object... args)
    {
        FMLLog.log(modName, logLevel, format, args);
    }

    public static void log(String modName, Level logLevel, Object object)
    {
        logf(modName, logLevel, object.toString());
    }

    public static void logexf(String modName, Level logLevel, Throwable throwable, String format, Object... args)
    {
        FMLLog.log(modName, logLevel, throwable, format, args);
    }

    public static void logex(String modName, Level logLevel, Throwable throwable, Object object)
    {
        logexf(modName, logLevel, throwable, object.toString());
    }


    public static void modf(Level logLevel, String format, Object... args)
    {
        logf(name, logLevel, format, args);
    }

    public static void mod(Level logLevel, Object object)
    {
        log(name, logLevel, object);
    }

    public static void modexf(Level logLevel, Throwable throwable, String format, Object... args)
    {
        logexf(name, logLevel, throwable, format, args);
    }

    public static void modex(Level logLevel, Throwable throwable, Object object)
    {
        logex(name, logLevel, throwable, object);
    }


    public static void allf(String format, Object... args)
    {
        modf(Level.ALL, format, args);
    }

    public static void all(Object object)
    {
        mod(Level.ALL, object);
    }

    public static void allexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.ALL, throwable, format, args);
    }

    public static void allex(Throwable throwable, Object object)
    {
        modex(Level.ALL, throwable, object);
    }


    public static void debugf(String format, Object... args)
    {
        modf(Level.DEBUG, format, args);
    }

    public static void debug(Object object)
    {
        mod(Level.DEBUG, object);
    }

    public static void debugexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.DEBUG, throwable, format, args);
    }

    public static void debugex(Throwable throwable, Object object)
    {
        modex(Level.DEBUG, throwable, object);
    }


    public static void errorf(String format, Object... args)
    {
        modf(Level.ERROR, format, args);
    }

    public static void error(Object object)
    {
        mod(Level.ERROR, object);
    }

    public static void errorexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.ERROR, throwable, format, args);
    }

    public static void errorex(Throwable throwable, Object object)
    {
        modex(Level.ERROR, throwable, object);
    }


    public static void fatalf(String format, Object... args)
    {
        modf(Level.FATAL, format, args);
    }

    public static void fatal(Object object)
    {
        mod(Level.FATAL, object);
    }

    public static void fatalexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.FATAL, throwable, format, args);
    }

    public static void fatalex(Throwable throwable, Object object)
    {
        modex(Level.FATAL, throwable, object);
    }


    public static void infof(String format, Object... args)
    {
        modf(Level.INFO, format, args);
    }

    public static void info(Object object)
    {
        mod(Level.INFO, object);
    }

    public static void infoexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.INFO, throwable, format, args);
    }

    public static void infoex(Throwable throwable, Object object)
    {
        modex(Level.INFO, throwable, object);
    }


    public static void offf(String format, Object... args)
    {
        modf(Level.OFF, format, args);
    }

    public static void off(Object object)
    {
        mod(Level.OFF, object);
    }

    public static void offexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.OFF, throwable, format, args);
    }

    public static void offex(Throwable throwable, Object object)
    {
        modex(Level.OFF, throwable, object);
    }


    public static void tracef(String format, Object... args)
    {
        modf(Level.TRACE, format, args);
    }

    public static void trace(Object object)
    {
        mod(Level.TRACE, object);
    }

    public static void traceexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.TRACE, throwable, format, args);
    }

    public static void traceex(Throwable throwable, Object object)
    {
        modex(Level.TRACE, throwable, object);
    }


    public static void warnf(String format, Object... args)
    {
        modf(Level.WARN, format, args);
    }

    public static void warn(Object object)
    {
        mod(Level.WARN, object);
    }

    public static void warnexf(Throwable throwable, String format, Object... args)
    {
        modexf(Level.WARN, throwable, format, args);
    }

    public static void warnex(Throwable throwable, Object object)
    {
        modex(Level.WARN, throwable, object);
    }


    //These two methods are meant for client-side use only
    //Should however one of these get called on the server, which is quite likely to happen,
    // instead of crashing on a ClassNotFoundEx we fall back to the now deprecated serverside
    // I18n located in package net.minecraft.util.text.translation
    @SuppressWarnings("deprecation")
    public static String translate(Object object)
    {
        return Minecraft.getMinecraft().world.isRemote
                ? I18n.format(object.toString())
                : net.minecraft.util.text.translation.I18n.translateToLocal(object.toString());
    }

    @SuppressWarnings("deprecation")
    public static String translatef(String format, Object... args)
    {
        return Minecraft.getMinecraft().world.isRemote
                ? I18n.format(format, args)
                : net.minecraft.util.text.translation.I18n.translateToLocalFormatted(format, args);
    }
}
