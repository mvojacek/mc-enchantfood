package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssencePump;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEssencePump extends TileGeneric implements ITickable, IEssencePump {
    private int tier;

    public TileEssencePump(int tier) {
        super();
        this.tier = tier;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public void writePacketNBT(NBTTagCompound cmp) {
        super.writePacketNBT(cmp);
    }

    @Override
    public void readPacketNBT(NBTTagCompound cmp) {
        super.readPacketNBT(cmp);
    }

    @Override
    public void update() {

    }

    @Override
    public int getTier() {
        return tier;
    }
}
