package com.github.hashtagshell.enchantfood.asm.obf;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public abstract class ObfMember
{
    private final String nameSrg;
    private final String nameDev;
    private final String nameNotch;

    private final String descNotch;
    private final String descMcp;

    public ObfMember(String nameAll, String descAll)
    {
        this(nameAll, descAll, descAll);
    }

    public ObfMember(String nameAll, String descNotch, String descMcp)
    {
        this(nameAll, nameAll, nameAll, descNotch, descMcp);
    }

    public ObfMember(String nameSrg, String nameDev, String nameNotch, String descNotch, String descMcp)
    {
        this.nameSrg = nameSrg;
        this.nameDev = nameDev;
        this.nameNotch = nameNotch;
        this.descNotch = descNotch;
        this.descMcp = descMcp;
    }

    public boolean check(ObfState state, String name, String desc)
    {
        return name(state).equals(name) && desc(state).equals(desc);
    }

    public String name(ObfState state)
    {
        switch (state)
        {
            case SRG:
                return nameSrg;
            case NOTCH:
                return nameNotch;
            case DEV:
            default:
                return nameDev;
        }
    }

    public String desc(ObfState state)
    {
        return ObfState.decideClassMcpNotch(state, descNotch, descMcp);
    }

    public String name()
    {
        return name(ObfState.get());
    }

    public String desc()
    {
        return desc(ObfState.get());
    }

    public boolean check(String name, String desc)
    {
        return check(ObfState.get(), name, desc);
    }
}
