package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public final class ObfField extends ObfMember
{
    public ObfField(String nameObfDeobf, String descObfDeobf)
    {
        super(nameObfDeobf, descObfDeobf);
    }

    public ObfField(String nameObfDeobf, String descObf, String descDeobf)
    {
        super(nameObfDeobf, descObf, descDeobf);
    }

    public ObfField(String nameObf, String nameDeobf, String descObf, String descDeobf)
    {
        super(nameObf, nameDeobf, descObf, descDeobf);
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
