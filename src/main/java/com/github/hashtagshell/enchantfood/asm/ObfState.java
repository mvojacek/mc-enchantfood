package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.entity.Entity;

import com.github.hashtagshell.enchantfood.utility.Log;

public enum ObfState
{
    OBF, DEOBF_SRG, DEOBF_FULL;

    private static ObfState current = null;

    private static boolean obfuscatedEnvironment = true;

    static void setObfuscatedEnvironment(boolean obf)
    {
        if (current == null) obfuscatedEnvironment = obf;
    }

    public static ObfState get()
    {
        if (current == null)
        {
            // This is a better way of doing this than that in last commit,
            // because it is more self-sufficient: We pretty much have a
            // guarantee that the IFMLLoadingPlugin's injectData method
            // was run even before the transformer class is loaded, which
            // should be the only class calling this. The previous way
            // would not work or fallback to a rudimentary method if the
            // setDeobf method was not called by the transformer. It would
            // have very probably called it every time, but this is safer.

            // This is true if there is a World class in the classpath before
            // coremod loading start, ie. long before runtime deobf happens.
            // If we are before runtime deobf but the class is deobf anyway,
            // we are in a fully deobf dev env.
            if (obfuscatedEnvironment)
            {
                // We try to get the field for Entity's LOGGER by its SRG name.
                // If this succeeds, runtime deobf has already happened, thus
                // we are in a deobf environment with SRG methods/fields
                try
                {
                    Entity.class.getDeclaredField("field_184243_a"); //Entity.LOGGER / Entity.field_184243_a / sm.a
                    current = DEOBF_SRG;
                }
                catch (NoSuchFieldException e)
                {
                    // We could not get the SRG field, so we have to be in a
                    // fully obfuscated environment.
                    current = OBF;
                }
            }
            else
            {
                // We are in a fully deobf dev env
                current = DEOBF_FULL;
            }
            Log.infof("This environment is %s", current);
        }
        return current;
    }

    public final int id()
    {
        return ordinal();
    }
}
