package com.github.hashtagshell.enchantfood.asm.obf;

import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;

import com.github.hashtagshell.enchantfood.asm.ObfState;

public class ObfField extends ObfMember
{
    public ObfField(String nameAll, String descAll)
    {
        super(nameAll, descAll);
    }

    public ObfField(String nameAll, String descNotch, String descMcp)
    {
        super(nameAll, descNotch, descMcp);
    }

    public ObfField(String nameSrg, String nameDev, String nameNotch, String descNotch, String descMcp)
    {
        super(nameSrg, nameDev, nameNotch, descNotch, descMcp);
    }

    public boolean check(ObfState state, FieldInsnNode node)
    {
        return check(state, node.name, node.desc);
    }

    public boolean check(ObfState state, FieldNode node)
    {
        return check(state, node.name, node.desc);
    }

    public boolean check(FieldInsnNode node)
    {
        return check(ObfState.get(), node.name, node.desc);
    }

    public boolean check(FieldNode node)
    {
        return check(ObfState.get(), node.name, node.desc);
    }

}
