package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.github.hashtagshell.enchantfood.asm.ObfConstants.ObfItemStack;
import com.github.hashtagshell.enchantfood.asm.config.AsmConf;
import com.github.hashtagshell.enchantfood.asm.obf.ObfClass;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.function.BiConsumer;

import static com.github.hashtagshell.enchantfood.asm.AsmUtils.HookInsertRelativePos.AFTER;
import static com.github.hashtagshell.enchantfood.asm.AsmUtils.HookInsertRelativePos.BEFORE;
import static com.github.hashtagshell.enchantfood.asm.AsmUtils.staticHookAllInsns;
import static com.github.hashtagshell.enchantfood.asm.ObfConstants.ObfHooks;
import static com.github.hashtagshell.enchantfood.asm.ObfConstants.ObfItemFood;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.FRETURN;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.IRETURN;

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
            case "net.minecraft.item.ItemStack":
                return transformClass(name, nameDeobf, cls, Transformers::transformItemStack);
        }

        return cls;
    }

    public static byte[] transformClass(String name, String nameDeobf, byte[] cls,
                                        BiConsumer<ClassNode, ObfState> transformer)
    {
        ObfState state = ObfState.get();
        Log.infof("Transforming %s (%s) in env %s:", name, nameDeobf, state);
        try
        {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(cls);
            classReader.accept(classNode, 0);

            transformer.accept(classNode, state);

            ObfClassWriter classWriter = new ObfClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
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
        private static final ObfClass HOOK_CLASS = ObfHooks.CLS;

        public static void transformItemFood(ClassNode node, ObfState state)
        {
            if (!AsmConf.transform_C_ItemFood) return;

            for (MethodNode method : node.methods)
            {
                if (AsmConf.transform_C_ItemFood_M_GetHealAmount
                    && ObfItemFood.GET_HEAL_AMOUNT.check(method))
                {
                    staticHookAllInsns(method, IRETURN, BEFORE, ALOAD, 1,
                                       state, HOOK_CLASS, ObfHooks.PROCESS_ITEM_FOOD_HEAL_AMOUNT);
                }
                else if (AsmConf.transform_C_ItemFood_M_GetSaturationModifier
                         && ObfItemFood.GET_SATURATION_MODIFIER.check(method))
                {
                    staticHookAllInsns(method, FRETURN, BEFORE, ALOAD, 1,
                                       state, HOOK_CLASS, ObfHooks.PROCESS_ITEM_FOOD_SATURATION_AMOUNT);
                }
                else if (AsmConf.transform_C_ItemFood_M_GetMaxItemUseDuration
                         && ObfItemFood.GET_MAX_ITEM_USE_DURATION.check(method))
                {
                    staticHookAllInsns(method, IRETURN, BEFORE, ALOAD, 1,
                                       state, HOOK_CLASS, ObfHooks.PROCESS_ITEM_FOOD_MAX_ITEM_USE_DURATION);
                }
                else if (AsmConf.transform_C_ItemFood_M_OnItemRightClick
                         && ObfItemFood.ON_ITEM_RIGHTCLICK.check(method))
                {
                    staticHookAllInsns(method,
                                       insn ->
                                               insn.getOpcode() == GETFIELD
                                               && ObfItemFood.F_ALWAYS_EDIBLE.check((FieldInsnNode) insn),
                                       AFTER, ALOAD, 4, state, HOOK_CLASS, ObfHooks.PROCESS_ITEM_FOOD_CAN_ALWAYS_EAT);
                }
            }
        }

        public static void transformItemStack(ClassNode node, ObfState state)
        {
            if (!AsmConf.transform_C_ItemStack) return;

            //noinspection Convert2streamapi - there may be more branches later
            for (MethodNode method : node.methods)
            {
                if (AsmConf.transform_C_ItemStack_M_IsItemEnchanted // CLIENT-ONLY (ignored on server)
                    && ObfItemStack.IS_ITEM_ENCHANTED.check(method))
                {
                    staticHookAllInsns(method, IRETURN, BEFORE, ALOAD, 0,
                                       state, HOOK_CLASS, ObfHooks.PROCESS_ITEM_STACK_IS_ITEM_ENCHANTED);
                }
            }
        }
    }
}
