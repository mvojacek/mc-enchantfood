package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceConsumer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEssenceFocuser extends TileGeneric implements ITickable, IEssenceConsumer {
    public EnumFacing rotation = EnumFacing.WEST;

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void update() {
    }

    @Override
    public int getMaxEssencePerTick() {
        return 0;
    }

    @Override
    public boolean getImConsuming() {
        return false;
    }

    @Override
    public int getMaxEssence() {
        return 0;
    }

    @Override
    public int getCurrentEssence() {
        return 0;
    }

    @Override
    public boolean canInsertEssence(int amount) {
        return true;
    }

    @Override
    public boolean isFull() {
        return false;
    }
}
