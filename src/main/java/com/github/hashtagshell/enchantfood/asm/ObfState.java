package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.entity.Entity;

import com.github.hashtagshell.enchantfood.reference.Ref;
import com.github.hashtagshell.enchantfood.utility.Log;

public enum ObfState
{
    OBF, DEOBF_SRG, DEOBF_FULL;

    private static ObfState current = null;

    private static boolean classesObfuscated = !Ref.Asm.RUNS_AFTER_DEOBF_REMAPPER;

    static void setClassesObfuscated(boolean obf)
    {
        if (current == null)
            classesObfuscated = obf;
    }

    public static ObfState get()
    {
        if (current == null)
        {
            if (classesObfuscated) // This should be set by transformer when we first hit a class we want to transform
            {
                // The classes are obfuscated so we are definitely
                // in an obf environment before the remapper ran
                current = OBF;
            }
            else
            {
                // The classes are deobfuscated, so either we are
                // in a fully deobf environment or we are in a deobf
                // environment but the remapper has run already.

                try
                {
                    // Now we get a deobf field from the class via
                    // reflection, if we fail, a NoSuchFieldException
                    // is thrown and we know it has an SRG name.
                    // Otherwise, if we succeed, we are fully deobf.
                    // We can ignore the return value because we don't
                    // need it for anything, the 'getting' is what we
                    // care about
                    Entity.class.getDeclaredField("LOGGER");

                    current = DEOBF_FULL;
                }
                catch (NoSuchFieldException e)
                {
                    current = DEOBF_SRG;
                }
                catch (Exception e)
                {
                    // Something failed, we will decide between
                    // FULL/SRG deobf by manually comparing our
                    // SortingIndex to that of the remapper.
                    if (!Ref.Asm.RUNS_AFTER_DEOBF_REMAPPER)
                    {
                        // We are running before the remapper,
                        // which means are have to be in a full
                        // deobf
                        current = DEOBF_FULL;
                    }
                    else
                    {
                        // We are running after the remapper, so
                        // we don't know if the fact that we are
                        // deobf was caused by the remapper or
                        // if we were deobf from the start. We
                        // default to SRGs because that's the one
                        // that will work if we are in a real client.
                        current = DEOBF_SRG;
                    }
                }
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
