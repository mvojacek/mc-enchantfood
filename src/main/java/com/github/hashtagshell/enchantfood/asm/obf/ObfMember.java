package com.github.hashtagshell.enchantfood.asm.obf;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public abstract class ObfMember
{
    protected final String[] name = new String[ObfState.values().length];
    protected final String[] desc = new String[ObfState.values().length];

    public ObfMember(String nameObfSrgDeobf, String descObfDeobf)
    {
        this(nameObfSrgDeobf, descObfDeobf, descObfDeobf);
    }

    public ObfMember(String nameObfSrgDeobf, String descObf, String descDeobf)
    {
        this(nameObfSrgDeobf, nameObfSrgDeobf, nameObfSrgDeobf, descObf, descDeobf);
    }

    public ObfMember(String nameObf, String nameSrg, String nameDeobf, String descObf, String descDeobf)
    {
        name[ObfState.OBF.id()] = nameObf;
        name[ObfState.DEOBF_FULL.id()] = nameDeobf;
        name[ObfState.DEOBF_SRG.id()] = nameSrg;

        desc[ObfState.OBF.id()] = descObf;
        desc[ObfState.DEOBF_FULL.id()] = descDeobf;
        desc[ObfState.DEOBF_SRG.id()] = descDeobf;
    }

    public ObfMember(String nameObf, String nameSrg, String nameDeobf, String descObfDeobf)
    {
        this(nameObf, nameSrg, nameDeobf, descObfDeobf, descObfDeobf);
    }

    public boolean check(ObfState state, String name, String desc)
    {
        return name(state).equals(name) && desc(state).equals(desc);
    }

    public String name(ObfState state)
    {
        return name[state.id()];
    }

    public String desc(ObfState state)
    {
        return desc[state.id()];
    }

}
