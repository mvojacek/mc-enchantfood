package com.github.hashtagshell.enchantfood.asm;

import org.objectweb.asm.Type;

import static com.github.hashtagshell.enchantfood.asm.ObfState.*;

public final class ObfClass
{
    private final String[] name = new String[values().length];
    private final boolean isIface;

    public ObfClass(Class clsObfDeobf)
    {
        this(Type.getInternalName(clsObfDeobf), clsObfDeobf.isInterface());
    }

    public ObfClass(String nameObfDeobf, boolean isInterface)
    {
        this(nameObfDeobf, nameObfDeobf, isInterface);
    }

    public ObfClass(String nameObf, String nameDeobf, boolean isInterface)
    {
        name[OBF.id()] = nameObf;
        name[DEOBF_FULL.id()] = nameDeobf;
        name[DEOBF_SRG.id()] = nameDeobf;
        isIface = isInterface;
    }

    public String name(ObfState state)
    {
        return name[state.id()];
    }

    public boolean isInterface()
    {
        return isIface;
    }
}
