package com.github.hashtagshell.enchantfood.utility;

public class Objects
{
    public static <T> T assertNull(T object, T nullCase)
    {
        return object == null ? nullCase : object;
    }
}
