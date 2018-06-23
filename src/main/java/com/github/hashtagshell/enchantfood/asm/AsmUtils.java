package com.github.hashtagshell.enchantfood.asm;

import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import net.minecraftforge.fml.relauncher.CoreModManager;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.github.hashtagshell.enchantfood.asm.obf.ObfClass;
import com.github.hashtagshell.enchantfood.asm.obf.ObfMember;
import com.github.hashtagshell.enchantfood.reference.Ref;
import com.github.hashtagshell.enchantfood.utility.LazyReference;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        return state == ObfState.SRG ? forceToDeobfClassName(obfClassName) : obfClassName;
    }

    public static String forceToDeobfClassName(String obfClassName)
    {
        return FMLDeobfuscatingRemapper.INSTANCE.map(obfClassName.replace('.', '/')).replace('/', '.');
    }

    public static String toObfClassName(ObfState state, String deobfClassName)
    {
        return state == ObfState.SRG ? forceToObfClassName(deobfClassName) : deobfClassName;
    }

    public static String forceToObfClassName(String deobfClassName)
    {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(deobfClassName.replace('.', '/')).replace('/', '.');
    }

    private static LazyReference.Weak<Map<String, Integer>> tweakSorting_REF = new LazyReference.Weak<>(
            new Supplier<Map<String, Integer>>()
            {
                private Field tweakSorting_FIELD;

                {
                    try
                    {
                        Field f = CoreModManager.class.getDeclaredField("tweakSorting");
                        f.setAccessible(true);
                        tweakSorting_FIELD = f;
                    }
                    catch (NoSuchFieldException e)
                    {
                        Log.errorex(e, "Error while getting tweakSorting map from CoreModManager");
                    }
                }

                @SuppressWarnings("unchecked")
                @Override
                public Map<String, Integer> get()
                {
                    try
                    {
                        return (Map<String, Integer>) tweakSorting_FIELD.get(null);
                    }
                    catch (IllegalAccessException e)
                    {
                        Log.errorex(e, "Error while getting tweakSorting map from CoreModManager");
                        System.exit(1);
                    }
                    return null;
                }
            });


    public static int getTweakerSortingIndex(String tweaker)
    {
        return tweakSorting_REF.get().get(tweaker);
    }

    public static ObfState getEnvStateWithoutLoadingClasses(boolean runtimeDeobfuscationEnabled)
    {
        int our = Ref.Asm.SORTING_INDEX;
        int fml = getTweakerSortingIndex("net.minecraftforge.fml.common.launcher.FMLDeobfTweaker");
        if (our == fml)
            Log.errorf("Our sortingIndex is the same as FMLDeobf's (%s), this causes undefined behaviour.", our);

        if (!runtimeDeobfuscationEnabled)
            return ObfState.DEV;

        return our < fml ? ObfState.NOTCH : ObfState.SRG;
    }
}
