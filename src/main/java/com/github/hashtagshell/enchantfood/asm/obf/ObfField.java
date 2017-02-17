package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public final class ObfField extends ObfMember
{
    public ObfField(String nameObfSrgDeobf, String descObfDeobf)
    {
        super(nameObfSrgDeobf, descObfDeobf);
    }

    public ObfField(String nameObfSrgDeobf, String descObf, String descDeobf)
    {
        super(nameObfSrgDeobf, descObf, descDeobf);
    }

    public ObfField(String nameObf, String nameSrg, String nameDeobf, String descObfDeobf)
    {
        super(nameObf, nameSrg, nameDeobf, descObfDeobf);
    }

    public ObfField(String nameObf, String nameSrg, String nameDeobf, String descObf, String descDeobf)
    {
        super(nameObf, nameSrg, nameDeobf, descObf, descDeobf);
    }

    public boolean check(ObfState state, FieldInsnNode node)
    {
        return check(state, node.name, node.desc);
    }

    public boolean check(ObfState state, FieldNode node)
    {
        return check(state, node.name, node.desc);
    }
}
