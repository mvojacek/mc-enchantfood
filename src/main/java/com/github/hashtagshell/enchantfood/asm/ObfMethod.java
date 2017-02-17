package com.github.hashtagshell.enchantfood.asm;

import org.objectweb.asm.tree.MethodNode;

import static com.github.hashtagshell.enchantfood.asm.ObfState.*;

public final class ObfMethod
{
    private final String[] name = new String[values().length];
    private final String[] desc = new String[values().length];

    public ObfMethod(String nameObfSrgDeobf, String descObfDeobf)
    {
        this(nameObfSrgDeobf, descObfDeobf, descObfDeobf);
    }

    public ObfMethod(String nameObfSrgDeobf, String descObf, String descDeobf)
    {
        this(nameObfSrgDeobf, nameObfSrgDeobf, nameObfSrgDeobf, descObf, descDeobf);
    }

    public ObfMethod(String nameObf, String nameSrg, String nameDeobf, String descObf, String descDeobf)
    {
        name[OBF.id()] = nameObf;
        name[DEOBF_FULL.id()] = nameDeobf;
        name[DEOBF_SRG.id()] = nameSrg;

        desc[OBF.id()] = descObf;
        desc[DEOBF_FULL.id()] = descDeobf;
        desc[DEOBF_SRG.id()] = descDeobf;
    }

    public boolean check(ObfState state, MethodNode node)
    {
        return check(state, node.name, node.desc);
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
