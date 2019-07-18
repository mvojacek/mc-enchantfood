package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.BlockEssencePumpGeneric;
import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceConsumer;
import com.github.hashtagshell.enchantfood.essence.IEssenceProvider;
import com.github.hashtagshell.enchantfood.essence.ITierable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEssencePump extends TileGeneric implements ITickable, ITierable, IEssenceProvider {
    private int tier;
    private int essenceThroughput;
    private int maxEssence;
    private int currentEssence;
    public TileEssencePump() {
        super();
    }

    public TileEssencePump(int tier, int throughtput) {
        super();
        this.tier = tier;
        this.essenceThroughput = throughtput;
        this.maxEssence = throughtput;
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
        EnumFacing inputSide = world.getBlockState(getPos()).getValue(BlockEssencePumpGeneric.FACING);
        TileEntity teOnInput = world.getTileEntity(getPos().offset(inputSide));
        TileEntity teOnOutput = world.getTileEntity(getPos().offset(inputSide.getOpposite()));
        if (teOnInput != null && teOnInput instanceof IEssenceProvider && !this.isFull()) {
            currentEssence = Math.min(((IEssenceProvider) teOnInput).getMaxOutputEssence(), essenceThroughput);
        }

        if (teOnOutput != null && teOnOutput instanceof IEssenceConsumer && !((IEssenceConsumer) teOnOutput).isFull() && currentEssence > 0) {
            int essenceAmount = Math.min(essenceThroughput, Math.min(((IEssenceConsumer) teOnOutput).getMaxEssencePerTick(), (((IEssenceConsumer) teOnOutput).getMaxEssence() - ((IEssenceConsumer) teOnOutput).getCurrentEssence())));
            ((IEssenceConsumer) teOnOutput).setEssence(((IEssenceConsumer) teOnOutput).getCurrentEssence() + essenceAmount);
            this.currentEssence -= essenceAmount;
        }
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public int getMaxOutputEssence() {
        return essenceThroughput;
    }

    @Override
    public int getMaxEssence() {
        return maxEssence;
    }

    @Override
    public int getCurrentEssence() {
        return currentEssence;
    }

    @Override
    public void setEssence(int amount) {
        currentEssence = amount;
    }
}
