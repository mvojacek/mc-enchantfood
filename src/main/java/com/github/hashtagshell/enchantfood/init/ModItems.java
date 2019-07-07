package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.item.ItemBeefStick;
import com.github.hashtagshell.enchantfood.item.ItemGeneric;
import com.github.hashtagshell.enchantfood.reference.Ref;

public class ModItems
{
    public static ItemGeneric blank;
    public static ItemGeneric essenceShard;
    public static ItemGeneric beefStick;

    public static void preInit()
    {
        blank = new ItemGeneric(Ref.Items.BLANK).register();
        essenceShard = new ItemGeneric(Ref.Items.ESSENCE_SHARD).register();
        beefStick = new ItemBeefStick(Ref.Items.MAGICAL_BEEF_STICK).register();
    }
}
