package com.github.hashtagshell.enchantfood.config;

import net.minecraft.util.ResourceLocation;

import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.github.hashtagshell.enchantfood.config.Conf.Enchants.*;
import static com.github.hashtagshell.enchantfood.config.Conf.FoodPotions.*;
import static com.github.hashtagshell.enchantfood.config.Conf.FoodPotions.EnforceRestrictions.WRITE_STACK;
import static com.github.hashtagshell.enchantfood.config.Conf.General.foodUseTicksMin;
import static com.github.hashtagshell.enchantfood.config.Conf.General.useFallbackHandlers;
import static com.github.hashtagshell.enchantfood.config.Conf.Visual.foodPotionPreviewFull;
import static com.github.hashtagshell.enchantfood.config.Conf.Visual.foodPotionPreviewLines;
import static com.github.hashtagshell.enchantfood.config.Config.config;

public class Conf
{
    public static class Enchants
    {
        private static final String category = "enchants";

        public static boolean enableNutritious    = true;
        public static boolean enableSaturating    = true;
        public static boolean enableDigestible    = true;
        public static boolean enableAlwaysEdible  = true;
        public static boolean enableNotNutritious = true;
        public static boolean enableNotSaturating = true;
        public static boolean enableNotEdible     = true;

        public static float modifierNutritious = 0.333F;
        public static float modifierSaturating = 0.333F;
        public static float modifierDigestible = 0.1F;
    }

    public static class General
    {
        private static final String category = "general";

        public static int     foodUseTicksMin     = 2;
        public static boolean useFallbackHandlers = true;
    }

    public static class FoodPotions
    {
        public static final String category = "foodpotions";

        // @formatter:off
        public static SortedSet<ResourceLocation> disabledPotions
                = new TreeSet<>((r1, r2) -> r1.toString().compareTo(r2.toString()));
        static final String[] disabledPotionsDefault = {};
        // @formatter:on

        public static boolean             enable              = true;
        public static int                 maxAmplifier        = 255;
        public static int                 maxDuration         = 20000000; // in ticks, = 1000000 seconds
        public static EnforceRestrictions enforceRestrictions = WRITE_STACK;

        public enum EnforceRestrictions
        {
            DISABLE, PREVENT_COMMAND, PREVENT_APPLY, WRITE_STACK;

            public boolean includes(EnforceRestrictions e)
            {
                return ordinal() >= e.ordinal();
            }
        }
    }

    public static class Visual
    {
        public static final String category = "visual";

        public static int     foodPotionPreviewLines = 8;
        public static boolean foodPotionPreviewFull  = false;
    }

    private static String name, category;
    private static float defaultFloat, minFloat, maxFloat;
    private static int defaultInt, minInt, maxInt;
    private static boolean  defaultBool;
    private static String[] defaultStringArray, validStringArray;
    private static Enum<?> defaultEnum;

    public static void loadConfiguration()
    {
        loadConfigurationGeneral();
        loadConfigurationEnchants();
        loadConfigurationFoodPotions();
        loadConfigurationVisual();
    }

    private static void loadConfigurationGeneral()
    {
        category = General.category;

        name = "foodUseTicksMin";
        defaultInt = foodUseTicksMin;
        minInt = 0;
        maxInt = Integer.MAX_VALUE;
        foodUseTicksMin = getInt();

        name = "useFallbackHandlers";
        defaultBool = useFallbackHandlers;
        useFallbackHandlers = getBoolean();
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

        name = "enableNotNutritious";
        defaultBool = enableNotNutritious;
        enableNotNutritious = getBoolean();

        name = "enableNotSaturating";
        defaultBool = enableNotSaturating;
        enableNotSaturating = getBoolean();

        name = "enableNotEdible";
        defaultBool = enableNotEdible;
        enableNotEdible = getBoolean();


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

    private static void loadConfigurationFoodPotions()
    {
        category = FoodPotions.category;

        name = "enable";
        defaultBool = enable;
        enable = getBoolean();

        name = "disabledPotions";
        validStringArray = null;
        defaultStringArray = disabledPotionsDefault;
        disabledPotions.clear();
        for (String s : getStringArray())
            disabledPotions.add(new ResourceLocation(s));

        name = "maxAmplifier";
        minInt = 0;
        maxInt = 255;
        defaultInt = maxAmplifier;
        maxAmplifier = getInt();

        name = "maxDuration";
        minInt = 0;
        maxInt = Integer.MAX_VALUE;
        defaultInt = maxDuration;
        maxDuration = getInt();

        name = "enforceRestrictions";
        defaultEnum = enforceRestrictions;
        enforceRestrictions = getEnum(EnforceRestrictions::valueOf, EnforceRestrictions::values);
    }

    private static void loadConfigurationVisual()
    {
        category = Visual.category;

        name = "foodPotionPreviewLines";
        defaultInt = foodPotionPreviewLines;
        foodPotionPreviewLines = getInt();

        name = "foodPotionPreviewFull";
        defaultBool = foodPotionPreviewFull;
        foodPotionPreviewFull = getBoolean();
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
