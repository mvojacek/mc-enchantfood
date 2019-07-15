package com.github.hashtagshell.enchantfood.item;

import com.github.hashtagshell.enchantfood.block.IRotatable;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class ItemBeefStick extends ItemGeneric {
    public ItemBeefStick(String name) {
        super(name);
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        tooltip.add(Ref.ToolTips.MAGICAL_BEEF_STICK_TOOLTIP);
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        RayTraceResult result = rayTrace(worldIn, playerIn, false);
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK && worldIn.getBlockState(result.getBlockPos()).getBlock() instanceof IRotatable) {
            ((IRotatable) worldIn.getBlockState(result.getBlockPos()).getBlock()).rotate(worldIn, result.getBlockPos(), IRotatable.RotationDirection.CLOCKWISE);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
