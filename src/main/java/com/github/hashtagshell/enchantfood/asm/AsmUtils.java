package com.github.hashtagshell.enchantfood.asm;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.tree.*;

import com.github.hashtagshell.enchantfood.asm.obf.ObfClass;
import com.github.hashtagshell.enchantfood.asm.obf.ObfMember;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.function.Predicate;

import static org.objectweb.asm.Opcodes.INVOKESTATIC;

public class AsmUtils
{

    public static void staticHookAllInsns(MethodNode method, int labelInsn, HookInsertRelativePos pos,
                                          int loadParInsn,
                                          int loadParIndex,
                                          ObfState state, ObfClass hookClass, ObfMember hookMethod)
    {
        staticHookAllInsns(method, insn -> insn.getOpcode() == labelInsn, pos, loadParInsn, loadParIndex, state, hookClass, hookMethod);
    }

    public static void staticHookAllInsns(MethodNode method, int labelInsn, HookInsertRelativePos pos,
                                          int loadParInsn, int loadParIndex,
                                          String hookClass, String hookMethod, String hookDesc,
                                          boolean hookIsInterface)
    {
        staticHookAllInsns(method, insn -> insn.getOpcode() == labelInsn, pos, loadParInsn, loadParIndex, hookClass, hookMethod, hookDesc, hookIsInterface);
    }

    public static void staticHookAllInsns(MethodNode method, Predicate<AbstractInsnNode> labelInsn,
                                          HookInsertRelativePos pos,
                                          int loadParInsn,
                                          int loadParIndex,
                                          String hookClass, String hookMethod, String hookDesc,
                                          boolean hookIsInterface)
    {
        InsnList hook = new InsnList();
        hook.add(new VarInsnNode(loadParInsn, loadParIndex)); // Loads the hook's second par onto the stack
        hook.add(new MethodInsnNode(INVOKESTATIC, hookClass, hookMethod, hookDesc, hookIsInterface)); // Executes the hook

        for (AbstractInsnNode insn : method.instructions.toArray())
            if (labelInsn.test(insn))
            {
                Log.infof("  - Adding call to static hook %s#%s%s %s opcode %s in method %s%s",
                          hookClass, hookMethod, hookDesc, pos.name().toLowerCase(), insn.getOpcode(), method.name, method.desc);
                if (pos.isBefore())
                    method.instructions.insertBefore(insn, hook);
                else
                    method.instructions.insert(insn, hook);
            }
    }

    public static void staticHookAllInsns(MethodNode method, Predicate<AbstractInsnNode> labelInsn,
                                          HookInsertRelativePos pos,
                                          int loadParInsn, int loadParIndex,
                                          ObfState state, ObfClass hookClass, ObfMember hookMethod)
    {
        staticHookAllInsns(method, labelInsn, pos, loadParInsn, loadParIndex,
                           hookClass.name(state), hookMethod.name(state),
                           hookMethod.desc(state), hookClass.isInterface());
    }

    public static boolean isObf(String name, String nameDeobf)
    {
        return !name.equals(nameDeobf);
    }

    enum HookInsertRelativePos
    {
        BEFORE, AFTER;

        public boolean isBefore() {return this == BEFORE;}

        public boolean isAfter()  {return this == AFTER;}
    }

    public static String toDeobfClassName(ObfState state, String obfClassName)
    {
        return state == ObfState.OBF ? forceToDeobfClassName(obfClassName) : obfClassName;
    }

    public static String forceToDeobfClassName(String obfClassName)
    {
        return FMLDeobfuscatingRemapper.INSTANCE.map(obfClassName.replace('.', '/')).replace('/', '.');
    }

    public static String toObfClassName(ObfState state, String deobfClassName)
    {
        return state == ObfState.OBF ? forceToObfClassName(deobfClassName) : deobfClassName;
    }

    public static String forceToObfClassName(String deobfClassName)
    {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(deobfClassName.replace('.', '/')).replace('/', '.');
    }
}
