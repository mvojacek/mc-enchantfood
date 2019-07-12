package com.github.hashtagshell.enchantfood.block.lib.pipe;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TilePipeGeneric extends TileGeneric {

    private final String CONNECTION_NORTH_NBT = "northConnected";
    private final String CONNECTION_SOUTH_NBT = "southConnected";
    private final String CONNECTION_EAST_NBT = "eastConnected";
    private final String CONNECTION_WEST_NBT = "westConnected";
    private final String CONNECTION_UP_NBT = "upConnected";
    private final String CONNECTION_DOWN_NBT = "downConnected";

    boolean northConnected = false;
    boolean southConnected = false;
    boolean eastConnected = false;
    boolean westConnected = false;
    boolean upConnected = false;
    boolean downConnected = false;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        southConnected = compound.getBoolean(CONNECTION_NORTH_NBT);
        northConnected = compound.getBoolean(CONNECTION_SOUTH_NBT);
        eastConnected = compound.getBoolean(CONNECTION_EAST_NBT);
        westConnected = compound.getBoolean(CONNECTION_WEST_NBT);
        upConnected = compound.getBoolean(CONNECTION_UP_NBT);
        downConnected = compound.getBoolean(CONNECTION_DOWN_NBT);
        super.readFromNBT(compound);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return (oldState.getBlock() != newState.getBlock());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean(CONNECTION_NORTH_NBT, northConnected);
        compound.setBoolean(CONNECTION_SOUTH_NBT, southConnected);
        compound.setBoolean(CONNECTION_EAST_NBT, eastConnected);
        compound.setBoolean(CONNECTION_WEST_NBT, westConnected);
        compound.setBoolean(CONNECTION_UP_NBT, upConnected);
        compound.setBoolean(CONNECTION_DOWN_NBT, downConnected);
        return super.writeToNBT(compound);
    }

    @Override
    public void writePacketNBT(NBTTagCompound cmp) {
        cmp.setBoolean(CONNECTION_NORTH_NBT, northConnected);
        cmp.setBoolean(CONNECTION_SOUTH_NBT, southConnected);
        cmp.setBoolean(CONNECTION_EAST_NBT, eastConnected);
        cmp.setBoolean(CONNECTION_WEST_NBT, westConnected);
        cmp.setBoolean(CONNECTION_UP_NBT, upConnected);
        cmp.setBoolean(CONNECTION_DOWN_NBT, downConnected);
    }

    @Override
    public void readPacketNBT(NBTTagCompound cmp) {
        southConnected = cmp.getBoolean(CONNECTION_NORTH_NBT);
        northConnected = cmp.getBoolean(CONNECTION_SOUTH_NBT);
        eastConnected = cmp.getBoolean(CONNECTION_EAST_NBT);
        westConnected = cmp.getBoolean(CONNECTION_WEST_NBT);
        upConnected = cmp.getBoolean(CONNECTION_UP_NBT);
        downConnected = cmp.getBoolean(CONNECTION_DOWN_NBT);
        super.readPacketNBT(cmp);
    }

}
