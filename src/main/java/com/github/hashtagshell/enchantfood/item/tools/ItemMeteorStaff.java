package com.github.hashtagshell.enchantfood.item.tools;

import com.github.hashtagshell.enchantfood.item.ItemGeneric;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMeteorStaff extends ItemGeneric {
    public ItemMeteorStaff(String name) {
        super(name);
    }

    public final int DEFAULT_DAMAGE = 24;
    public final int RANGE = 6;


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
