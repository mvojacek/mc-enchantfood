package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import com.github.hashtagshell.enchantfood.asm.obf.ObfClass;
import com.github.hashtagshell.enchantfood.asm.obf.ObfMember;
import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static com.github.hashtagshell.enchantfood.asm.EnchantFoodTransformer.HookInsertRelativePos.AFTER;
import static com.github.hashtagshell.enchantfood.asm.EnchantFoodTransformer.HookInsertRelativePos.BEFORE;
import static com.github.hashtagshell.enchantfood.asm.ObfConstants.ObfHooks;
import static com.github.hashtagshell.enchantfood.asm.ObfConstants.ObfItemFood;
import static org.objectweb.asm.Opcodes.*;

public class EnchantFoodTransformer implements IClassTransformer
{
    public static void transformItemFood(ClassNode node, ObfState state)
    {
        if (!Conf.Asm.enable_C_ItemFood) return;

        final ObfClass HOOK_CLASS = ObfHooks.CLS;

        for (MethodNode method : node.methods)
        {
            //FIXME Config not yet loaded so default (true) values are always used
            if (Conf.Enchants.enableNutritious && Conf.Asm.enable_C_ItemFood_M_GetHealAmount
                && ObfItemFood.GET_HEAL_AMOUNT.check(state, method))
            {
                staticHookAllInsns(method, IRETURN, BEFORE, ALOAD, 1,
                                   state, HOOK_CLASS, ObfHooks.PROCESS_HEAL_AMOUNT);
            }
            else if (Conf.Enchants.enableSaturating && Conf.Asm.enable_C_ItemFood_M_GetSaturationModifier
                     && ObfItemFood.GET_SATURATION_MODIFIER.check(state, method))
            {
                staticHookAllInsns(method, FRETURN, BEFORE, ALOAD, 1,
                                   state, HOOK_CLASS, ObfHooks.PROCESS_SATURATION_AMOUNT);
            }
            else if (Conf.Enchants.enableDigestible && Conf.Asm.enable_C_ItemFood_M_GetMaxItemUseDuration
                     && ObfItemFood.GET_MAX_ITEM_USE_DURATION.check(state, method))
            {
                staticHookAllInsns(method, IRETURN, BEFORE, ALOAD, 1,
                                   state, HOOK_CLASS, ObfHooks.PROCESS_MAX_ITEM_USE_DURATION);
            }
            else if (Conf.Asm.enable_C_ItemFood_M_OnItemRightClick //TODO Add condition once the alwaysEdible enchant is in
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

    public static void staticHookAllInsns(MethodNode method, int labelInsn, HookInsertRelativePos pos, int loadParInsn,
                                          int loadParIndex,
                                          ObfState state, ObfClass hookClass, ObfMember hookMethod)
    {
        staticHookAllInsns(method, insn -> insn.getOpcode() == labelInsn, pos, loadParInsn, loadParIndex, state, hookClass, hookMethod);
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

    public static boolean isObf(String name, String nameDeobf)
    {
        return !name.equals(nameDeobf);
    }

    @Override
    public byte[] transform(String name, String nameDeobf, byte[] cls)
    {
        switch (nameDeobf)
        {
            case "net.minecraft.item.ItemFood":

                try (FileOutputStream fos = new FileOutputStream("/home/michal/tmp/original.class"))
                {
                    fos.write(cls);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                byte[] bytes = transformClass(name, nameDeobf, cls, EnchantFoodTransformer::transformItemFood);

                try (FileOutputStream fos = new FileOutputStream("/home/michal/tmp/transformed.class"))
                {
                    fos.write(bytes);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                return bytes;
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

    enum HookInsertRelativePos
    {
        BEFORE, AFTER;

        public boolean isBefore() {return this == BEFORE;}

        public boolean isAfter()  {return this == AFTER;}
    }
}
