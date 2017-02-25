package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.item.ItemGeneric;

public class ModItems
{
    public static ItemGeneric blank;

    public static void preInit()
    {
        blank = new ItemGeneric("blank").register();
    }
}
