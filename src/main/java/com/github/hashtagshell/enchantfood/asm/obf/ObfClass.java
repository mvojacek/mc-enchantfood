package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.Type;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public class ObfClass
{
    private final String nameNotch, nameMcp;
    private final boolean isInterface;

    public ObfClass(Class clsNotchMcp)
    {
        this(Type.getInternalName(clsNotchMcp), clsNotchMcp.isInterface());
    }

    public ObfClass(String nameNotchMcp, boolean isInterface)
    {
        this(nameNotchMcp, nameNotchMcp, isInterface);
    }

    public ObfClass(String nameNotch, String nameMcp, boolean isInterface)
    {
        this.nameNotch = nameNotch;
        this.nameMcp = nameMcp;
        this.isInterface = isInterface;
    }

    public String name(ObfState state)
    {
        return ObfState.decideClassMcpNotch(state, nameNotch, nameMcp);
    }

    public boolean isInterface()
    {
        return isInterface;
    }
}
