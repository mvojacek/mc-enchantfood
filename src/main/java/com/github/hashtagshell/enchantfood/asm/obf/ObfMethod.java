package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public final class ObfMethod extends ObfMember
{
    public ObfMethod(String nameObfDeobf, String descObfDeobf)
    {
        super(nameObfDeobf, descObfDeobf);
    }

    public ObfMethod(String nameObfDeobf, String descObf, String descDeobf)
    {
        super(nameObfDeobf, descObf, descDeobf);
    }

    public ObfMethod(String nameObf, String nameDeobf, String descObf, String descDeobf)
    {
        super(nameObf, nameDeobf, descObf, descDeobf);
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
