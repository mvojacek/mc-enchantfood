package com.github.hashtagshell.enchantfood.asm;

import com.github.hashtagshell.enchantfood.utility.Log;

public enum ObfState
{
    OBF, DEOBF;

    private static ObfState current = null;

    private static boolean obfuscatedEnvironment = true;

    static void setObfuscatedEnvironment(boolean obf)
    {
        obfuscatedEnvironment = obf;
    }

    public static ObfState get()
    {
        if (current == null)
        {
            //: :IF ENABLE_RUN_DEOBF :IE !DEV_ENV :THEN OBF :ELSE DEOBF :END
            current = obfuscatedEnvironment ? OBF : DEOBF;
            Log.infof("This environment is %s", current);
        }
        return current;
    }

    public final int id()
    {
        return ordinal();
    }
}
