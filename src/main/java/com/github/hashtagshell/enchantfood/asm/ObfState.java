package com.github.hashtagshell.enchantfood.asm;

import com.github.hashtagshell.enchantfood.utility.Log;

public enum ObfState
{
    SRG, DEV, NOTCH;

    private static ObfState state = null;

    static void set(ObfState state)
    {
        ObfState.state = state;
    }

    public static ObfState get()
    {
        if (state == null)
            Log.fatal("Obfuscation state is not set!");
        return state;
    }

    public final int id()
    {
        return ordinal();
    }

    public static String decideClassMcpNotch(ObfState state, String notch, String mcp)
    {
        switch (state)
        {
            case NOTCH:
                return notch;

            case DEV:
            case SRG:
            default:
                return mcp;
        }
    }
}
