package com.github.hashtagshell.enchantfood.asm.obf;

import com.github.hashtagshell.enchantfood.asm.ObfState;

import static com.github.hashtagshell.enchantfood.asm.ObfState.DEOBF;
import static com.github.hashtagshell.enchantfood.asm.ObfState.OBF;

public abstract class ObfMember
{
    protected final String[] name = new String[ObfState.values().length];
    protected final String[] desc = new String[ObfState.values().length];

    public ObfMember(String nameObfDeobf, String descObfDeobf)
    {
        this(nameObfDeobf, descObfDeobf, descObfDeobf);
    }

    public ObfMember(String nameObfDeobf, String descObf, String descDeobf)
    {
        this(nameObfDeobf, nameObfDeobf, descObf, descDeobf);
    }

    public ObfMember(String nameObf, String nameDeobf, String descObf, String descDeobf)
    {
        name[OBF.id()] = nameObf;
        name[DEOBF.id()] = nameDeobf;

        desc[OBF.id()] = descObf;
        desc[DEOBF.id()] = descDeobf;
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
