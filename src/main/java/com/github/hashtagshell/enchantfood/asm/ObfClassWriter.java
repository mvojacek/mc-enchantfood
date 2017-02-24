package com.github.hashtagshell.enchantfood.asm;

import org.objectweb.asm.ClassWriter;

public class ObfClassWriter extends ClassWriter
{
    public ObfClassWriter(int flags)
    {
        super(flags);
    }

    // The JVM still is already working with deobf names, while we still get fed obf bytecode. So we need
    // to deobf types manually before resolving it against the current (deobf) state. Once we have resolved
    // the superclass we want to reobfuscate again to meet Forge's expectations of the returned bytecode
    // This is pretty much a copy of the super method, but with added obf/deobf instructions.
    @Override
    protected String getCommonSuperClass(String type1, String type2)
    {
        Class<?> c, d;
        ClassLoader classLoader = getClass().getClassLoader();

        try
        {
            c = Class.forName(AsmUtils.toDeobfClassName(ObfState.get(), type1.replace('/', '.')), false, classLoader);
            d = Class.forName(AsmUtils.toDeobfClassName(ObfState.get(), type2.replace('/', '.')), false, classLoader);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        if (c.isAssignableFrom(d))
            return type1;
        if (d.isAssignableFrom(c))
            return type2;
        if (c.isInterface() || d.isInterface())
            return "java/lang/Object";

        do
            c = c.getSuperclass();
        while (!c.isAssignableFrom(d));

        return AsmUtils.toObfClassName(ObfState.get(), c.getName()).replace('.', '/');
    }
}
