package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.function.BiConsumer;

import static org.objectweb.asm.Opcodes.*;

public class EnchantFoodTransformer implements IClassTransformer
{
    public static void transformItemFood(ClassNode node, boolean obf)
    {
        // @formatter:off
        final String HOOK_CLASS = Type.getInternalName(EnchantFoodHooks.class);

        final String HOOK_HEAL_AMOUNT                = "processItemFoodHealAmount";
        final String HOOK_HEAL_AMOUNT_DESC           = obf ? "(ILafi;)I" : "(ILnet/minecraft/item/ItemStack;)I";

        final String HOOK_SATURATION                 = "processItemFoodSaturationAmount";
        final String HOOK_SATURATION_DESC            = obf ? "(FLafi;)F" : "(FLnet/minecraft/item/ItemStack;)F";

        final String HOOK_MAX_ITEM_USE_DURATION      = "processItemFoodMaxUseDuration";
        final String HOOK_MAX_ITEM_USE_DURATION_DESC = obf ? "(ILafi;)I" : "(ILnet/minecraft/item/ItemStack;)I";


        final String SATURATION_MODIFIER        = obf ? "i"        : "getSaturationModifier";
        final String SATURATION_MODIFIER_DESC   = obf ? "(Lafi;)F" : "(Lnet/minecraft/item/ItemStack;)F";

        final String HEAL_AMOUNT                = obf ? "h"        : "getHealAmount";
        final String HEAL_AMOUNT_DESC           = obf ? "(Lafi;)I" : "(Lnet/minecraft/item/ItemStack;)I";

        final String MAX_ITEM_USE_DURATION      = obf ? "e"        : "getMaxItemUseDuration";
        final String MAX_ITEM_USE_DURATION_DESC = obf ? "(Lafi;)I" : "(Lnet/minecraft/item/ItemStack;)I";

        final String ON_ITEM_RIGHTCLICK         = obf ? "a"                    : "onItemRightClick";
        final String ON_ITEM_RIGHTCLICK_DESC    = obf ? "(Lajq;Laax;Lrh;)Lrk;" : "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/ActionResult;";

        for (MethodNode method : node.methods)
        {
            final String name = method.name;
            final String desc = method.desc;

            if (name.equals(SATURATION_MODIFIER) && desc.equals(SATURATION_MODIFIER_DESC))
            {
                staticHookLastReturn(method, FRETURN, ALOAD, 1, HOOK_CLASS,
                                     HOOK_SATURATION, HOOK_SATURATION_DESC, false);
            }
            else if (name.equals(HEAL_AMOUNT) && desc.equals(HEAL_AMOUNT_DESC))
            {
                staticHookLastReturn(method, IRETURN, ALOAD, 1, HOOK_CLASS,
                                     HOOK_HEAL_AMOUNT, HOOK_HEAL_AMOUNT_DESC, false);
            }
            else if (name.equals(MAX_ITEM_USE_DURATION) && desc.equals(MAX_ITEM_USE_DURATION_DESC))
            {
                staticHookLastReturn(method, IRETURN, ALOAD, 1, HOOK_CLASS,
                                     HOOK_MAX_ITEM_USE_DURATION, HOOK_MAX_ITEM_USE_DURATION_DESC, false);
            }
            else if (name.equals(ON_ITEM_RIGHTCLICK) && desc.equals(ON_ITEM_RIGHTCLICK_DESC))
            {

            }
        }

        // @formatter:off
    }

    public static void staticHookLastReturn(MethodNode method, int returnInsn, int loadParInsn, int loadParIndex,
                                        String hookClass, String hookMethod, String hookDesc, boolean hookIsInterface)
    {
        AbstractInsnNode insnReturn = null;
        for (AbstractInsnNode insn : method.instructions.toArray())
            if (insn.getOpcode() == returnInsn)
                insnReturn = insn;
        if (insnReturn == null) return;

        VarInsnNode loadItemStack = new VarInsnNode(loadParInsn, loadParIndex);
        MethodInsnNode hook = new MethodInsnNode(INVOKESTATIC, hookClass, hookMethod, hookDesc, hookIsInterface);
        method.instructions.insertBefore(insnReturn, loadItemStack);
        method.instructions.insertBefore(insnReturn, hook);
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
                                        BiConsumer<ClassNode, Boolean> transformer)
    {
        Log.infof("Transforming %s (%s)", nameDeobf, name);
        try
        {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(cls);
            classReader.accept(classNode, 0);

            transformer.accept(classNode, isObf(name, nameDeobf));

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return cls;
    }

    public static boolean isObf(String name, String nameDeobf)
    {
        return !name.equals(nameDeobf);
    }
}
