package com.github.hashtagshell.enchantfood.utility;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.*;
import java.util.regex.Pattern;


/**
 * Copied from {@link net.minecraft.util.text.TextFormatting}
 * but reformatted to make sense and added utils for rgb colors
 */
public enum ChatColor
{
    BLACK("BLACK", '0', 0),
    DARK_BLUE("DARK_BLUE", '1', 1),
    DARK_GREEN("DARK_GREEN", '2', 2),
    DARK_AQUA("DARK_AQUA", '3', 3),
    DARK_RED("DARK_RED", '4', 4),
    DARK_PURPLE("DARK_PURPLE", '5', 5),
    GOLD("GOLD", '6', 6),
    GRAY("GRAY", '7', 7),
    DARK_GRAY("DARK_GRAY", '8', 8),
    BLUE("BLUE", '9', 9),
    GREEN("GREEN", 'a', 10),
    AQUA("AQUA", 'b', 11),
    RED("RED", 'c', 12),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13),
    YELLOW("YELLOW", 'e', 14),
    WHITE("WHITE", 'f', 15),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1);

    //------------------HASHTAG START------------------

    public static ChatColor fromTextFormatting(TextFormatting formatting)
    {
        return values()[formatting.ordinal()];
    }

    public TextFormatting toTextFormatting()
    {
        return TextFormatting.values()[ordinal()];
    }

    public int getRGBColor(boolean isLighter)
    {
        return GuiUtils.getColorCode(formattingCode, isLighter);
    }

    public int getRGBColor()
    {
        return getRGBColor(true);
    }

    public boolean isDark()
    {
        return getColorIndex() < 8;
    }

    public ChatColor lighterVariant()
    {
        return isDark() ? fromColorIndex(getColorIndex() + 8) : this;
    }

    public ChatColor darkerVariant()
    {
        return isDark() ? this : fromColorIndex(getColorIndex() - 8);
    }

    public ChatColor oppositeVariant()
    {
        return isDark() ? lighterVariant() : darkerVariant();
    }

    public boolean isShadeOfGray()
    {
        return this == BLACK || this == DARK_GRAY || this == GRAY || this == WHITE;
    }

    //-------------------HASHTAG END-------------------

    private static final Map<String, ChatColor> NAME_MAPPING            = new HashMap<>();
    /**
     * Matches formatting codes that indicate that the client should treat the following text as bold, recolored,
     * obfuscated, etc.
     */
    private static final Pattern                FORMATTING_CODE_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    /**
     * The name of this category/formatting
     */
    private final String  name;
    /**
     * The formatting code that produces this format.
     */
    private final char    formattingCode;
    private final boolean fancyStyling;
    /**
     * The control string (section sign + formatting code) that can be inserted into client-side text to display
     * subsequent text in this format.
     */
    private final String  controlString;
    /**
     * The numerical index that represents this category
     */
    private final int     colorIndex;

    private static String lowercaseAlpha(String p_175745_0_)
    {
        return p_175745_0_.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    ChatColor(String formattingName, char formattingCodeIn, int colorIndex)
    {
        this(formattingName, formattingCodeIn, false, colorIndex);
    }

    ChatColor(String formattingName, char formattingCodeIn, boolean fancyStylingIn)
    {
        this(formattingName, formattingCodeIn, fancyStylingIn, -1);
    }

    ChatColor(String formattingName, char formattingCodeIn, boolean fancyStylingIn, int colorIndex)
    {
        this.name = formattingName;
        this.formattingCode = formattingCodeIn;
        this.fancyStyling = fancyStylingIn;
        this.colorIndex = colorIndex;
        this.controlString = "\u00a7" + formattingCodeIn;
    }

    /**
     * Returns the numerical category index that represents this formatting
     */
    public int getColorIndex()
    {
        return this.colorIndex;
    }

    /**
     * False if this is just changing the category or resetting; true otherwise.
     */
    public boolean isFancyStyling()
    {
        return this.fancyStyling;
    }

    /**
     * Checks if this is a category code.
     */
    public boolean isColor()
    {
        return !this.fancyStyling && this != RESET;
    }

    /**
     * Gets the friendly name of this value.
     */
    public String getFriendlyName()
    {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString()
    {
        return this.controlString;
    }

    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     */

    @SuppressWarnings("unused")
    public static String getTextWithoutFormattingCodes(String text)
    {
        return text == null ? null : FORMATTING_CODE_PATTERN.matcher(text).replaceAll("");
    }

    /**
     * Gets a value by its friendly name; null if the given name does not map to a defined value.
     */

    @SuppressWarnings("unused")
    public static ChatColor getValueByName(String friendlyName)
    {
        return friendlyName == null ? null : NAME_MAPPING.get(lowercaseAlpha(friendlyName));
    }

    /**
     * Get a ChatColor from it's category index
     */

    public static ChatColor fromColorIndex(int index)
    {
        if (index < 0)
        {
            return RESET;
        }
        else
        {
            for (ChatColor textformatting : values())
            {
                if (textformatting.getColorIndex() == index)
                {
                    return textformatting;
                }
            }

            return null;
        }
    }

    @SuppressWarnings("unused")
    public static Collection<String> getValidValues(boolean p_96296_0_, boolean p_96296_1_)
    {
        List<String> list = new ArrayList<>();

        for (ChatColor textformatting : values())
        {
            if ((!textformatting.isColor() || p_96296_0_) && (!textformatting.isFancyStyling() || p_96296_1_))
            {
                list.add(textformatting.getFriendlyName());
            }
        }

        return list;
    }

    static
    {
        for (ChatColor textformatting : values())
        {
            NAME_MAPPING.put(lowercaseAlpha(textformatting.name), textformatting);
        }
    }
}
