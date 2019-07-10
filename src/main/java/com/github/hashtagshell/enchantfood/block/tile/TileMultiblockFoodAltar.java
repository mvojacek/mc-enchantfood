package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import net.minecraft.block.Block;

public class TileMultiblockFoodAltar extends TileGeneric {

    public TileFoodAltar altar;
    public Block prevBlock;

    public void revertMultiblock() {
        world.setBlockState(getPos(), prevBlock.getDefaultState());
    }
}
