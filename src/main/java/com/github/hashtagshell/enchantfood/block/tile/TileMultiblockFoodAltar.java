package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileMultiblockFoodAltar extends TileGeneric {

    public BlockPos altarPos;

    public static final String ALTAR_POS_NBT = "altarPos";

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        altarPos = BlockPos.fromLong(compound.getLong(ALTAR_POS_NBT));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setLong(ALTAR_POS_NBT, altarPos.toLong());
        return super.writeToNBT(compound);
    }


    @Override
    public void writePacketNBT(NBTTagCompound cmp) {
        cmp.setLong(ALTAR_POS_NBT, altarPos.toLong());
    }

    @Override
    public void readPacketNBT(NBTTagCompound cmp) {
        altarPos = BlockPos.fromLong(cmp.getLong(ALTAR_POS_NBT));
    }

}
