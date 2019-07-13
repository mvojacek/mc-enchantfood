package com.github.hashtagshell.enchantfood.block.testing;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockInfiniteEssence extends BlockTileGeneric<TileInfiniteEssence> {
    public BlockInfiniteEssence(String name) {
        super(name, Material.ROCK);
        setBlockUnbreakable();
    }

    @Override
    public Class<TileInfiniteEssence> tileClass() {
        return TileInfiniteEssence.class;
    }

    @Override
    public String tileId() {
        return Ref.Blocks.INFINITE_ESSENCE_HOLE;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileInfiniteEssence();
    }
}
