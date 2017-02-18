package com.github.hashtagshell.enchantfood.asm.config;

import static com.github.hashtagshell.enchantfood.asm.config.AsmConfig.props;

public class AsmConf
{
    public static boolean transform_ALL = true;

    public static boolean transform_C_ItemFood                         = true;
    public static boolean transform_C_ItemFood_M_GetHealAmount         = true;
    public static boolean transform_C_ItemFood_M_GetSaturationModifier = true;
    public static boolean transform_C_ItemFood_M_GetMaxItemUseDuration = true;
    public static boolean transform_C_ItemFood_M_OnItemRightClick      = true;

    private static String  name;
    private static boolean defaultBool;

    static void loadProps()
    {
        name = "transform_ALL";
        defaultBool = transform_ALL;
        transform_ALL = getBoolean();

        name = "transform_C_ItemFood";
        defaultBool = transform_C_ItemFood;
        transform_C_ItemFood = getBoolean();

        name = "transform_C_ItemFood_M_GetHealAmount";
        defaultBool = transform_C_ItemFood_M_GetHealAmount;
        transform_C_ItemFood_M_GetHealAmount = getBoolean();

        name = "transform_C_ItemFood_M_GetSaturationModifier";
        defaultBool = transform_C_ItemFood_M_GetSaturationModifier;
        transform_C_ItemFood_M_GetSaturationModifier = getBoolean();

        name = "transform_C_ItemFood_M_GetMaxItemUseDuration";
        defaultBool = transform_C_ItemFood_M_GetMaxItemUseDuration;
        transform_C_ItemFood_M_GetMaxItemUseDuration = getBoolean();

        name = "transform_C_ItemFood_M_OnItemRightClick";
        defaultBool = transform_C_ItemFood_M_OnItemRightClick;
        transform_C_ItemFood_M_OnItemRightClick = getBoolean();
    }

    private static boolean getBoolean()
    {
        if (props.containsKey(name))
        {
            String value = props.getProperty(name);
            switch (value)
            {
                case "true":
                case "yes":
                case "1":
                case "one":
                    return true;
                case "false":
                case "no":
                case "0":
                case "zero":
                    return false;
            }
        }
        saveDefault();
        return defaultBool;
    }

    private static void saveDefault()
    {
        props.setProperty(name, String.valueOf(defaultBool));
        AsmConfig.markDirty();
    }
}
