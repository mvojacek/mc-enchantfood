package com.github.hashtagshell.enchantfood.asm;

public final class ObfConstants
{
    public static final class ObfItemFood
    {
        public static final ObfMethod GET_SATURATION_MODIFIER
                = new ObfMethod("i",
                                "func_150906_h",
                                "getSaturationModifier",
                                "(Lafi;)F",
                                "(Lnet/minecraft/item/ItemStack;)F");

        public static final ObfMethod GET_HEAL_AMOUNT
                = new ObfMethod("h",
                                "func_150905_g",
                                "getHealAmount",
                                "(Lafi;)I",
                                "(Lnet/minecraft/item/ItemStack;)I");

        public static final ObfMethod GET_MAX_ITEM_USE_DURATION
                = new ObfMethod("e",
                                "func_77626_a",
                                "getMaxItemUseDuration",
                                "(Lafi;)I",
                                "(Lnet/minecraft/item/ItemStack;)I");

        public static final ObfMethod ON_ITEM_RIGHTCLICK
                = new ObfMethod("a",
                                "func_77659_a",
                                "onItemRightClick",
                                "(Lajq;Laax;Lrh;)Lrk;",
                                "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumHand;)Lnet/minecraft/util/ActionResult;");
    }

    public static final class ObfHooks
    {
        public static final ObfClass CLS = new ObfClass(EnchantFoodHooks.class);

        public static final ObfMethod PROCESS_HEAL_AMOUNT
                = new ObfMethod("processItemFoodHealAmount",
                                "(ILafi;)I",
                                "(ILnet/minecraft/item/ItemStack;)I");

        public static final ObfMethod PROCESS_SATURATION_AMOUNT
                = new ObfMethod("processItemFoodSaturationAmount",
                                "(FLafi;)F",
                                "(FLnet/minecraft/item/ItemStack;)F");

        public static final ObfMethod PROCESS_MAX_ITEM_USE_DURATION
                = new ObfMethod("processItemFoodMaxUseDuration",
                                "(ILafi;)I",
                                "(ILnet/minecraft/item/ItemStack;)I");
    }
}
