package com.github.hashtagshell.enchantfood.asm;

import com.github.hashtagshell.enchantfood.asm.obf.ObfClass;
import com.github.hashtagshell.enchantfood.asm.obf.ObfField;
import com.github.hashtagshell.enchantfood.asm.obf.ObfMethod;

public final class ObfConstants
{
    public static final class ObfItemFood
    {
        public static final ObfField F_ALWAYS_EDIBLE
                = new ObfField("field_77852_bZ",
                               "alwaysEdible",
                               "::unused::",
                               "Z",
                               "Z");

        public static final ObfMethod GET_SATURATION_MODIFIER
                = new ObfMethod("func_150906_h",
                                "getSaturationModifier",
                                "::unused::",
                                "(Lafi;)F",
                                "(Lnet/minecraft/item/ItemStack;)F");

        public static final ObfMethod GET_HEAL_AMOUNT
                = new ObfMethod("func_150905_g",
                                "getHealAmount",
                                "::unused::",
                                "(Lafi;)I",
                                "(Lnet/minecraft/item/ItemStack;)I");

        public static final ObfMethod GET_MAX_ITEM_USE_DURATION
                = new ObfMethod("func_77626_a",
                                "getMaxItemUseDuration",
                                "::unused::",
                                "(Lafi;)I",
                                "(Lnet/minecraft/item/ItemStack;)I");

        public static final ObfMethod ON_ITEM_RIGHTCLICK
                = new ObfMethod("func_77659_a",
                                "onItemRightClick",
                                "::unused::",
                                "(Lajq;Laax;Lrh;)Lrk;",
                                "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/ActionResult;");
    }

    public static final class ObfItemStack
    {
        public static final ObfMethod IS_ITEM_ENCHANTED
                = new ObfMethod("func_77948_v",
                                "isItemEnchanted",
                                "::unused::",
                                "()Z",
                                "()Z");
    }

    public static final class ObfHooks
    {
        public static final ObfClass CLS = new ObfClass(EnchantFoodHooks.class);

        public static final ObfMethod PROCESS_ITEM_FOOD_HEAL_AMOUNT
                = new ObfMethod("processItemFoodHealAmount",
                                "(ILafi;)I",
                                "(ILnet/minecraft/item/ItemStack;)I");

        public static final ObfMethod PROCESS_ITEM_FOOD_SATURATION_AMOUNT
                = new ObfMethod("processItemFoodSaturationAmount",
                                "(FLafi;)F",
                                "(FLnet/minecraft/item/ItemStack;)F");

        public static final ObfMethod PROCESS_ITEM_FOOD_MAX_ITEM_USE_DURATION
                = new ObfMethod("processItemFoodMaxUseDuration",
                                "(ILafi;)I",
                                "(ILnet/minecraft/item/ItemStack;)I");

        public static final ObfMethod PROCESS_ITEM_FOOD_CAN_ALWAYS_EAT
                = new ObfMethod("processItemFoodCanAlwaysEat",
                                "(ZLafi;)Z",
                                "(ZLnet/minecraft/item/ItemStack;)Z");

        public static final ObfMethod PROCESS_ITEM_STACK_IS_ITEM_ENCHANTED
                = new ObfMethod("processItemStackIsItemEnchanted",
                                "(ZLafi;)Z",
                                "(ZLnet/minecraft/item/ItemStack;)Z");
    }
}
