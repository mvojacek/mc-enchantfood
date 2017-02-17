package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.Type;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public final class ObfClass
{
    private final String[] name = new String[ObfState.values().length];
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
        name[ObfState.OBF.id()] = nameObf;
        name[ObfState.DEOBF_FULL.id()] = nameDeobf;
        name[ObfState.DEOBF_SRG.id()] = nameDeobf;
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
