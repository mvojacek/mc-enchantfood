package com.github.hashtagshell.enchantfood.config;

import com.github.hashtagshell.enchantfood.utility.Log;

public class Conf
{
    public static float modifierNutritious = 0.333F;
    public static float modifierSaturating = 0.333F;
    public static float modifierDigestible = 0.1F;
    public static int   foodUseTicksMin    = 2;

    public static void loadConfiguration()
    {
        String name, category, comment, lang;
        float defaultFloat, minFloat, maxFloat;
        int defaultInt, minInt, maxInt;

        name = "modifierNutritious";
        category = "enchants";
        defaultFloat = modifierNutritious;
        minFloat = 0F;
        maxFloat = Float.MAX_VALUE;
        comment = Log.translate("config." + name + ".comment");
        lang = "config." + name;
        modifierNutritious = Config.config.getFloat(name, category, defaultFloat, minFloat, maxFloat, comment, lang);

        name = "modifierSaturating";
        category = "enchants";
        defaultFloat = modifierSaturating;
        minFloat = 0F;
        maxFloat = Float.MAX_VALUE;
        comment = Log.translate("config." + name + ".comment");
        lang = "config." + name;
        modifierSaturating = Config.config.getFloat(name, category, defaultFloat, minFloat, maxFloat, comment, lang);

        name = "modifierDigestible";
        category = "enchants";
        defaultFloat = modifierDigestible;
        minFloat = 0F;
        maxFloat = Float.MAX_VALUE;
        comment = Log.translate("config." + name + ".comment");
        lang = "config." + name;
        modifierDigestible = Config.config.getFloat(name, category, defaultFloat, minFloat, maxFloat, comment, lang);

        name = "foodUseTicksMin";
        category = "general";
        defaultInt = foodUseTicksMin;
        minInt = 0;
        maxInt = Integer.MAX_VALUE;
        comment = Log.translate("config." + name + ".comment");
        lang = "config." + name;
        foodUseTicksMin = Config.config.getInt(name, category, defaultInt, minInt, maxInt, comment, lang);


    }
}
