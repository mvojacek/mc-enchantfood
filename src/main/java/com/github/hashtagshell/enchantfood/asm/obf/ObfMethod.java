package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public class ObfMethod extends ObfMember
{
    public ObfMethod(String nameAll, String descAll)
    {
        super(nameAll, descAll);
    }

    public ObfMethod(String nameAll, String descNotch, String descMcp)
    {
        super(nameAll, descNotch, descMcp);
    }

    public ObfMethod(String nameSrg, String nameDev, String nameNotch, String descNotch, String descMcp)
    {
        super(nameSrg, nameDev, nameNotch, descNotch, descMcp);
    }

    public boolean check(ObfState state, MethodNode node)
    {
        return check(state, node.name, node.desc);
    }

    public boolean check(ObfState state, MethodInsnNode node)
    {
        return check(state, node.name, node.desc);
    }

    public boolean check(MethodNode node)
    {
        return check(ObfState.get(), node);
    }

    public boolean check(MethodInsnNode node)
    {
        return check(ObfState.get(), node);
    }
}
