package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public final class ObfMethod extends ObfMember
{
    public ObfMethod(String nameObfSrgDeobf, String descObfDeobf)
    {
        super(nameObfSrgDeobf, descObfDeobf);
    }

    public ObfMethod(String nameObfSrgDeobf, String descObf, String descDeobf)
    {
        super(nameObfSrgDeobf, descObf, descDeobf);
    }

    public ObfMethod(String nameObf, String nameSrg, String nameDeobf, String descObfDeobf)
    {
        super(nameObf, nameSrg, nameDeobf, descObfDeobf);
    }

    public ObfMethod(String nameObf, String nameSrg, String nameDeobf, String descObf, String descDeobf)
    {
        super(nameObf, nameSrg, nameDeobf, descObf, descDeobf);
    }

    public boolean check(ObfState state, MethodNode node)
    {
        return check(state, node.name, node.desc);
    }

    public boolean check(ObfState state, MethodInsnNode node)
    {
        return check(state, node.name, node.desc);
    }
}
