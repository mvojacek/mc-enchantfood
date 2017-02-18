package com.github.hashtagshell.enchantfood.config;

import com.github.hashtagshell.enchantfood.utility.Log;

import static com.github.hashtagshell.enchantfood.config.Conf.Enchants.*;
import static com.github.hashtagshell.enchantfood.config.Conf.General.foodUseTicksMin;
import static com.github.hashtagshell.enchantfood.config.Config.config;

public class Conf
{
    public static class Enchants
    {
        private static final String category = "enchants";

        public static boolean enableNutritious   = true;
        public static boolean enableSaturating   = true;
        public static boolean enableDigestible   = true;
        public static boolean enableAlwaysEdible = true;

        public static float modifierNutritious = 0.333F;
        public static float modifierSaturating = 0.333F;
        public static float modifierDigestible = 0.1F;
    }

    public static class General
    {
        private static final String category = "general";

        public static int foodUseTicksMin = 2;
    }


    private static String name, category;
    private static float defaultFloat, minFloat, maxFloat;
    private static int defaultInt, minInt, maxInt;
    private static boolean defaultBool;

    public static void loadConfiguration()
    {
        loadConfigurationGeneral();
        loadConfigurationEnchants();
    }

    private static void loadConfigurationGeneral()
    {
        category = General.category;

        name = "foodUseTicksMin";
        defaultInt = foodUseTicksMin;
        minInt = 0;
        maxInt = Integer.MAX_VALUE;
        foodUseTicksMin = getInt();
    }

    private static void loadConfigurationEnchants()
    {
        category = Enchants.category;

        name = "enableNutritious";
        defaultBool = enableNutritious;
        enableNutritious = getBoolean();

        name = "enableSaturating";
        defaultBool = enableSaturating;
        enableSaturating = getBoolean();

        name = "enableDigestible";
        defaultBool = enableDigestible;
        enableDigestible = getBoolean();

        name = "enableAlwaysEdible";
        defaultBool = enableAlwaysEdible;
        enableAlwaysEdible = getBoolean();


        minFloat = 0F;
        maxFloat = Float.MAX_VALUE;

        name = "modifierNutritious";
        defaultFloat = modifierNutritious;
        modifierNutritious = getFloat();

        name = "modifierSaturating";
        defaultFloat = modifierSaturating;
        modifierSaturating = getFloat();

        name = "modifierDigestible";
        defaultFloat = modifierDigestible;
        modifierDigestible = getFloat();
    }

    private static int getInt()
    {
        String lang = makeLang(name);
        return config.getInt(name, category, defaultInt, minInt, maxInt, makeComment(lang), lang);
    }

    private static float getFloat()
    {
        String lang = makeLang(name);
        return config.getFloat(name, category, defaultFloat, minFloat, maxFloat, makeComment(lang), lang);
    }

    private static boolean getBoolean()
    {
        String lang = makeLang(name);
        return config.getBoolean(name, category, defaultBool, makeComment(lang), lang);
    }

    private static String makeComment(String lang)
    {
        return Log.translate(lang + ".comment");
    }

    private static String makeLang(String name)
    {
        return "config." + name;
    }
}
