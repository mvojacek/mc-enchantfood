package com.github.hashtagshell.enchantfood.potion;

import net.minecraft.potion.Potion;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public enum PotionCategory
{
    GOOD('3'), //DARK_AQUA
    GOOD_UTIL('2'), //DARK_GREEN
    GOOD_PVP('a'), //GREEN
    BAD('5'), //DARK_PURPLE
    BAD_UTIL('4'), //DARK_RED
    BAD_PVP('c'), //RED
    NEUTRAL('6'); //GOLD

    private String formattingCode;

    PotionCategory(char format)
    {
        this.formattingCode = "§" + format;
    }

    public String getFormattingCode()
    {
        return formattingCode;
    }

    private static SortedSet<Integer> util = new TreeSet<>();
    private static SortedSet<Integer> pvp  = new TreeSet<>();

    static
    {
        Collections.addAll(util,
                           1, //speed
                           2, //slowness
                           3, //haste
                           4, //mining_fatigue
                           8, //jump_boost
                           9, //nausea
                           12, //fire_resistance
                           13, //water_breathing
                           14, //invisibility
                           15, //blindness
                           16, //night_vision
                           17, //hunger
                           23, //saturation
                           24, //glowing
                           25, //levitation
                           26, //luck
                           27 //unluck
        );

        Collections.addAll(pvp,
                           5, //strength
                           6, //instant_health
                           7, //instant_damage
                           10, //regeneration
                           11, //resistance
                           18, //weakness
                           19, //poison
                           20, //wither
                           21, //health_boost
                           22 //absorption
        );
    }

    public static PotionCategory ofPotion(Potion potion)
    {
        int id = Potion.REGISTRY.getIDForObject(potion);

        if (potion.isBeneficial() == potion.isBadEffect())
            return NEUTRAL;

        if (util.contains(id))
            return potion.isBeneficial() ? GOOD_UTIL : BAD_UTIL;
        else if (pvp.contains(id))
            return potion.isBeneficial() ? GOOD_PVP : BAD_PVP;
        return potion.isBeneficial() ? GOOD : BAD;
    }
}
