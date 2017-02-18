package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.hashtagshell.enchantfood.asm.config.AsmConf;
import com.github.hashtagshell.enchantfood.asm.obf.ObfClass;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.function.BiConsumer;

import static com.github.hashtagshell.enchantfood.asm.AsmUtils.HookInsertRelativePos.AFTER;
import static com.github.hashtagshell.enchantfood.asm.AsmUtils.HookInsertRelativePos.BEFORE;
import static com.github.hashtagshell.enchantfood.asm.AsmUtils.staticHookAllInsns;
import static com.github.hashtagshell.enchantfood.asm.ObfConstants.ObfHooks;
import static com.github.hashtagshell.enchantfood.asm.ObfConstants.ObfItemFood;
import static org.objectweb.asm.Opcodes.*;

public class EnchantFoodTransformer implements IClassTransformer
{
    @Override
    public byte[] transform(String name, String nameDeobf, byte[] cls)
    {
        if (!AsmConf.transform_ALL) return cls;

        switch (nameDeobf)
        {
            case "net.minecraft.item.ItemFood":
                return transformClass(name, nameDeobf, cls, Transformers::transformItemFood);
        }

        return cls;
    }

    public static byte[] transformClass(String name, String nameDeobf, byte[] cls,
                                        BiConsumer<ClassNode, ObfState> transformer)
    {
        ObfState state = ObfState.get();
        Log.infof("Transforming %s (%s):", name, nameDeobf);
        try
        {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(cls);
            classReader.accept(classNode, 0);

            transformer.accept(classNode, state);

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        catch (Exception e)
        {
            Log.errorexf(e, "Could not transform %s (%s)", name, nameDeobf);
        }
        return cls;
    }

    public static class Transformers
    {
        public static void transformItemFood(ClassNode node, ObfState state)
        {
            if (!AsmConf.transform_C_ItemFood) return;

            final ObfClass HOOK_CLASS = ObfHooks.CLS;

            for (MethodNode method : node.methods)
            {
                if (AsmConf.transform_C_ItemFood_M_GetHealAmount
                    && ObfItemFood.GET_HEAL_AMOUNT.check(state, method))
                {
                    staticHookAllInsns(method, IRETURN, BEFORE, ALOAD, 1,
                                       state, HOOK_CLASS, ObfHooks.PROCESS_HEAL_AMOUNT);
                }
                else if (AsmConf.transform_C_ItemFood_M_GetSaturationModifier
                         && ObfItemFood.GET_SATURATION_MODIFIER.check(state, method))
                {
                    staticHookAllInsns(method, FRETURN, BEFORE, ALOAD, 1,
                                       state, HOOK_CLASS, ObfHooks.PROCESS_SATURATION_AMOUNT);
                }
                else if (AsmConf.transform_C_ItemFood_M_GetMaxItemUseDuration
                         && ObfItemFood.GET_MAX_ITEM_USE_DURATION.check(state, method))
                {
                    staticHookAllInsns(method, IRETURN, BEFORE, ALOAD, 1,
                                       state, HOOK_CLASS, ObfHooks.PROCESS_MAX_ITEM_USE_DURATION);
                }
                else if (AsmConf.transform_C_ItemFood_M_OnItemRightClick
                         && ObfItemFood.ON_ITEM_RIGHTCLICK.check(state, method))
                {
                    staticHookAllInsns(method,
                                       insn ->
                                               insn.getOpcode() == GETFIELD
                                               && ObfItemFood.F_ALWAYS_EDIBLE.check(state, (FieldInsnNode) insn),
                                       AFTER, ALOAD, 4, state, HOOK_CLASS, ObfHooks.PROCESS_CAN_ALWAYS_EAT);
                }
            }
        }
    }
}
