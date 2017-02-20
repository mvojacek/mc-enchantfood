package com.github.hashtagshell.enchantfood.potion.food;

import net.minecraft.potion.Potion;

import com.github.hashtagshell.enchantfood.utility.ChatColor;

import static com.github.hashtagshell.enchantfood.utility.ChatColor.*;

public enum PotionCategory
{
    GOOD(DARK_GREEN),
    BAD(DARK_RED),
    NEUTRAL(GOLD);

    private ChatColor color;

    PotionCategory(ChatColor color)
    {
        this.color = color;
    }

    public ChatColor getColor()
    {
        return color;
    }

    public String getChatFormatting()
    {
        return color.toString();
    }

    public static PotionCategory ofPotion(Potion potion)
    {
        if (potion.isBeneficial() == potion.isBadEffect())
            return NEUTRAL;
        return potion.isBeneficial() ? GOOD : BAD;
    }
}
