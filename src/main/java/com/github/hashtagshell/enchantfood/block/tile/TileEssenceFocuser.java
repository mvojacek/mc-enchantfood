package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEssenceFocuser extends TileGeneric implements ITickable {
    public EnumFacing rotation = EnumFacing.WEST;

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void update() {
    }

    public EnumFacing getRotation() {
        if (rotation.getHorizontalIndex() == 3) {
            rotation = EnumFacing.HORIZONTALS[0];
        } else {
            rotation = EnumFacing.HORIZONTALS[rotation.getHorizontalIndex() + 1];
        }
        return rotation;
    }
}
