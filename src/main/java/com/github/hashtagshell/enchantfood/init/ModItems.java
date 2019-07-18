package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.item.ItemBeefStick;
import com.github.hashtagshell.enchantfood.item.ItemGeneric;
import com.github.hashtagshell.enchantfood.item.tools.ItemMeteorStaff;
import com.github.hashtagshell.enchantfood.reference.Ref;

public class ModItems
{
    public static ItemGeneric blank;
    public static ItemGeneric essenceShard;
    public static ItemGeneric beefStick;
    public static ItemGeneric overloadedEssenceShard;
    public static ItemGeneric essenceStick;
    public static ItemGeneric basicPumpChamber;
    public static ItemGeneric meteorStaff;

    public static void preInit()
    {
        blank = new ItemGeneric(Ref.Items.BLANK).register();
        essenceShard = new ItemGeneric(Ref.Items.ESSENCE_SHARD).register();
        overloadedEssenceShard = new ItemGeneric(Ref.Items.OVERLOADED_ESSENCE_SHARD).register();
        beefStick = new ItemBeefStick(Ref.Items.MAGICAL_BEEF_STICK).register();
        essenceStick = new ItemGeneric(Ref.Items.ESSENCE_STICK).register();
        basicPumpChamber = new ItemGeneric(Ref.Items.BASIC_PRESSURE_CHAMBER).register();
        meteorStaff = new ItemMeteorStaff(Ref.Items.METEOR_STAFF).register();
    }
}
