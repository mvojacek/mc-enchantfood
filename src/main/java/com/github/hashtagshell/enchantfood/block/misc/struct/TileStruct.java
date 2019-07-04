package com.github.hashtagshell.enchantfood.block.misc.struct;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class TileStruct extends TileGeneric {
    private static final String MASTER = "master";

    private BlockPos master = BlockPos.ORIGIN;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        compound.setLong(MASTER, master.toLong());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        master = BlockPos.fromLong(compound.getLong(MASTER));
        return compound;
    }

    public BlockPos getMaster() {
        return master;
    }

    public void setMaster(BlockPos master) {
        this.master = master.toImmutable();
        markDirty();
    }
}
