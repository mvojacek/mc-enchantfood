package com.github.hashtagshell.enchantfood.config;

import net.minecraft.util.ResourceLocation;

import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.hashtagshell.enchantfood.config.Conf.Defaults.*;
import static com.github.hashtagshell.enchantfood.config.Conf.Enchants.*;
import static com.github.hashtagshell.enchantfood.config.Conf.Enums.EnforceRestrictions;
import static com.github.hashtagshell.enchantfood.config.Conf.Enums.EnforceRestrictions.WRITE_STACK;
import static com.github.hashtagshell.enchantfood.config.Conf.Enums.Shiny;
import static com.github.hashtagshell.enchantfood.config.Conf.Enums.Shiny.OFF;
import static com.github.hashtagshell.enchantfood.config.Conf.FoodPotions.*;
import static com.github.hashtagshell.enchantfood.config.Conf.General.foodUseTicksMin;
import static com.github.hashtagshell.enchantfood.config.Conf.General.useFallbackHandlers;
import static com.github.hashtagshell.enchantfood.config.Conf.Stupid.shiny;
import static com.github.hashtagshell.enchantfood.config.Conf.Visual.foodPotionPreviewFull;
import static com.github.hashtagshell.enchantfood.config.Conf.Visual.foodPotionPreviewLines;
import static com.github.hashtagshell.enchantfood.config.Config.config;

public class Conf
{
    public static class General
    {
        private static final String category = "general";

        public static int     foodUseTicksMin     = DefGeneral.foodUseTicksMin;
        public static boolean useFallbackHandlers = DefGeneral.useFallbackHandlers;
    }

    public static class Enchants
    {
        private static final String category = "enchants";

        public static boolean enableNutritious    = DefEnchants.enableNutritious;
        public static boolean enableSaturating    = DefEnchants.enableSaturating;
        public static boolean enableDigestible    = DefEnchants.enableDigestible;
        public static boolean enableAlwaysEdible  = DefEnchants.enableAlwaysEdible;
        public static boolean enableNotNutritious = DefEnchants.enableNotNutritious;
        public static boolean enableNotSaturating = DefEnchants.enableNotSaturating;

        public static boolean enableNotEdible    = DefEnchants.enableNotEdible;
        public static float   modifierNutritious = DefEnchants.modifierNutritious;
        public static float   modifierSaturating = DefEnchants.modifierSaturating;
        public static float   modifierDigestible = DefEnchants.modifierDigestible;

    }

    public static class FoodPotions
    {
        public static final String category = "foodpotions";

        public static SortedSet<ResourceLocation> disabledPotions
                = new TreeSet<>((r1, r2) -> r1.toString().compareTo(r2.toString()));

        public static boolean             enable              = DefFoodPotions.enable;
        public static int                 maxAmplifier        = DefFoodPotions.maxAmplifier;
        public static int                 maxDuration         = DefFoodPotions.maxDuration;
        public static EnforceRestrictions enforceRestrictions = DefFoodPotions.enforceRestrictions;

    }

    public static class Visual
    {
        public static final String category = "visual";

        public static int     foodPotionPreviewLines = DefVisual.foodPotionPreviewLines;
        public static boolean foodPotionPreviewFull  = DefVisual.foodPotionPreviewFull;
    }

    public static class Stupid
    {
        public static final String category = "stupid";

        public static Shiny shiny = DefStupid.shiny;
    }

    static class Defaults
    {
        public static class DefGeneral
        {
            public static final int     foodUseTicksMin     = 2;
            public static final boolean useFallbackHandlers = true;
        }

        public static class DefEnchants
        {
            public static final boolean enableNutritious    = true;
            public static final boolean enableSaturating    = true;
            public static final boolean enableDigestible    = true;
            public static final boolean enableAlwaysEdible  = true;
            public static final boolean enableNotNutritious = true;
            public static final boolean enableNotSaturating = true;

            public static final boolean enableNotEdible    = true;
            public static final float   modifierNutritious = 0.333F;
            public static final float   modifierSaturating = 0.333F;
            public static final float   modifierDigestible = 0.1F;

        }

        public static class DefFoodPotions
        {
            public static final String[]            disabledPotionsDefault = {};
            public static final boolean             enable                 = true;
            public static final int                 maxAmplifier           = 255;
            public static final int                 maxDuration            = 20000000; // in ticks, = 1000000 seconds
            public static final EnforceRestrictions enforceRestrictions    = WRITE_STACK;
        }

        public static class DefVisual
        {
            public static final int     foodPotionPreviewLines = 8;
            public static final boolean foodPotionPreviewFull  = false;
        }

        public static class DefStupid
        {
            public static final Shiny shiny = OFF;
        }
    }

    public static class Enums
    {
        public enum Shiny
        {
            OFF, FLASHY, ON
        }

        public enum EnforceRestrictions
        {
            DISABLE, PREVENT_COMMAND, PREVENT_APPLY, WRITE_STACK;

            public boolean includes(EnforceRestrictions e)
            {
                return ordinal() >= e.ordinal();
            }
        }
    }

    private static String name, category;
    private static float defaultFloat, minFloat, maxFloat;
    private static int defaultInt, minInt, maxInt;
    private static boolean  defaultBool;
    private static String[] defaultStringArray, validStringArray;
    private static Enum<?> defaultEnum;

    static void loadConfiguration()
    {
        loadConfigurationGeneral();
        loadConfigurationEnchants();
        loadConfigurationFoodPotions();
        loadConfigurationVisual();
        loadConfigurationStupid();
    }

    private static void loadConfigurationGeneral()
    {
        category = General.category;

        name = "foodUseTicksMin";
        defaultInt = DefGeneral.foodUseTicksMin;
        minInt = 0;
        maxInt = Integer.MAX_VALUE;
        foodUseTicksMin = getInt();

        name = "useFallbackHandlers";
        defaultBool = DefGeneral.useFallbackHandlers;
        useFallbackHandlers = getBoolean();
    }


    private static void loadConfigurationEnchants()
    {
        category = Enchants.category;

        name = "enableNutritious";
        defaultBool = DefEnchants.enableNutritious;
        enableNutritious = getBoolean();

        name = "enableSaturating";
        defaultBool = DefEnchants.enableSaturating;
        enableSaturating = getBoolean();

        name = "enableDigestible";
        defaultBool = DefEnchants.enableDigestible;
        enableDigestible = getBoolean();

        name = "enableAlwaysEdible";
        defaultBool = DefEnchants.enableAlwaysEdible;
        enableAlwaysEdible = getBoolean();

        name = "enableNotNutritious";
        defaultBool = DefEnchants.enableNotNutritious;
        enableNotNutritious = getBoolean();

        name = "enableNotSaturating";
        defaultBool = DefEnchants.enableNotSaturating;
        enableNotSaturating = getBoolean();

        name = "enableNotEdible";
        defaultBool = DefEnchants.enableNotEdible;
        enableNotEdible = getBoolean();


        minFloat = 0F;
        maxFloat = Float.MAX_VALUE;

        name = "modifierNutritious";
        defaultFloat = DefEnchants.modifierNutritious;
        modifierNutritious = getFloat();

        name = "modifierSaturating";
        defaultFloat = DefEnchants.modifierSaturating;
        modifierSaturating = getFloat();

        name = "modifierDigestible";
        defaultFloat = DefEnchants.modifierDigestible;
        modifierDigestible = getFloat();
    }

    private static void loadConfigurationFoodPotions()
    {
        category = FoodPotions.category;

        name = "enable";
        defaultBool = DefFoodPotions.enable;
        enable = getBoolean();

        name = "disabledPotions";
        validStringArray = null;
        defaultStringArray = DefFoodPotions.disabledPotionsDefault;
        disabledPotions.clear();
        for (String s : getStringArray())
            disabledPotions.add(new ResourceLocation(s));

        name = "maxAmplifier";
        minInt = 0;
        maxInt = 255;
        defaultInt = DefFoodPotions.maxAmplifier;
        maxAmplifier = getInt();

        name = "maxDuration";
        minInt = 0;
        maxInt = Integer.MAX_VALUE;
        defaultInt = DefFoodPotions.maxDuration;
        maxDuration = getInt();

        name = "enforceRestrictions";
        defaultEnum = DefFoodPotions.enforceRestrictions;
        enforceRestrictions = getEnum(EnforceRestrictions::valueOf, EnforceRestrictions::values);
    }

    private static void loadConfigurationVisual()
    {
        category = Visual.category;

        name = "foodPotionPreviewLines";
        defaultInt = DefVisual.foodPotionPreviewLines;
        foodPotionPreviewLines = getInt();

        name = "foodPotionPreviewFull";
        defaultBool = DefVisual.foodPotionPreviewFull;
        foodPotionPreviewFull = getBoolean();
    }

    private static void loadConfigurationStupid()
    {
        category = Stupid.category;

        name = "shiny";
        defaultEnum = DefStupid.shiny;
        shiny = getEnum(Shiny::valueOf, Shiny::values);
    }

    private static int getInt()
    {
        procVals();
        return config.getInt(procName, category, defaultInt, minInt, maxInt, procComment, procLang);
    }

    private static float getFloat()
    {
        procVals();
        return config.getFloat(procName, category, defaultFloat, minFloat, maxFloat, procComment, procLang);
    }

    private static boolean getBoolean()
    {
        procVals();
        return config.getBoolean(procName, category, defaultBool, procComment, procLang);
    }

    private static String[] getStringArray()
    {
        procVals();
        return config.getStringList(procName, category, defaultStringArray, procComment, validStringArray, procLang);
    }

    private static <T extends Enum> T getEnum(Function<String, T> valueOf, Supplier<T[]> values)
    {
        procVals();
        T[] vals = values.get();
        String[] names = new String[vals.length];
        for (int i = 0; i < vals.length; i++)
            names[i] = vals[i].name();
        String s = config.getString(procName, category, defaultEnum.name(), procComment, names, procLang);
        return valueOf.apply(s);
    }

    private static String procName, procLang, procComment;

    private static void procVals()
    {
        procName = makeName(category, name);
        procLang = makeLang(procName);
        procComment = makeComment(procLang);
    }

    private static String makeComment(String lang)
    {
        return Log.translate(lang + ".comment");
    }

    private static String makeName(String category, String name)
    {
        return category + '_' + name;
    }

    private static String makeLang(String name)
    {
        return "config." + name;
    }
}