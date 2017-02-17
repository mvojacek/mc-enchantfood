package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.function.BiConsumer;

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
            if (Conf.Enchants.enableNutritious
                && Conf.Asm.enable_C_ItemFood_M_GetHealAmount
                && ObfItemFood.GET_HEAL_AMOUNT.check(state, method))
            {
                staticHookAllReturns(method, IRETURN, ALOAD, 1,
                                     state, HOOK_CLASS, ObfHooks.PROCESS_HEAL_AMOUNT);
            }
            else if (Conf.Enchants.enableSaturating
                     && Conf.Asm.enable_C_ItemFood_M_GetSaturationModifier
                     && ObfItemFood.GET_SATURATION_MODIFIER.check(state, method))
            {
                staticHookAllReturns(method, FRETURN, ALOAD, 1,
                                     state, HOOK_CLASS, ObfHooks.PROCESS_SATURATION_AMOUNT);
            }
            else if (Conf.Enchants.enableDigestible
                     && Conf.Asm.enable_C_ItemFood_M_GetMaxItemUseDuration
                     && ObfItemFood.GET_MAX_ITEM_USE_DURATION.check(state, method))
            {
                staticHookAllReturns(method, IRETURN, ALOAD, 1,
                                     state, HOOK_CLASS, ObfHooks.PROCESS_MAX_ITEM_USE_DURATION);
            }
            else if (Conf.Asm.enable_C_ItemFood_M_OnItemRightClick
                     && ObfItemFood.ON_ITEM_RIGHTCLICK.check(state, method))
            {
                //TODO canAlwaysEat hook
            }
        }
    }

    public static void staticHookAllReturns(MethodNode method, int returnInsn, int loadParInsn, int loadParIndex,
                                            ObfState state, ObfClass hookClass, ObfMethod hookMethod)
    {
        staticHookAllReturns(method, returnInsn, loadParInsn, loadParIndex,
                             hookClass.name(state), hookMethod.name(state),
                             hookMethod.desc(state), hookClass.isInterface());
    }

    public static void staticHookAllReturns(MethodNode method, int returnInsn, int loadParInsn, int loadParIndex,
                                            String hookClass, String hookMethod, String hookDesc,
                                            boolean hookIsInterface)
    {
        Log.infof("  - Adding call to static hook %s#%s%s before all opcodes %s in method %s%s",
                  hookClass, hookMethod, hookDesc, returnInsn, method.name, method.desc);

        InsnList hook = new InsnList();
        hook.add(new VarInsnNode(loadParInsn, loadParIndex)); // Loads the hook's second par onto the stack
        hook.add(new MethodInsnNode(INVOKESTATIC, hookClass, hookMethod, hookDesc, hookIsInterface)); // Executes the hook

        for (AbstractInsnNode insn : method.instructions.toArray())
            if (insn.getOpcode() == returnInsn)
                method.instructions.insertBefore(insn, hook);
    }

    @Override
    public byte[] transform(String name, String nameDeobf, byte[] cls)
    {
        switch (nameDeobf)
        {
            case "net.minecraft.item.ItemFood":
                return transformClass(name, nameDeobf, cls, EnchantFoodTransformer::transformItemFood);
        }

        return cls;
    }

    public static byte[] transformClass(String name, String nameDeobf, byte[] cls,
                                        BiConsumer<ClassNode, ObfState> transformer)
    {
        ObfState.setClassesObfuscated(isObf(name, nameDeobf));
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

    public static boolean isObf(String name, String nameDeobf)
    {
        return !name.equals(nameDeobf);
    }
}
