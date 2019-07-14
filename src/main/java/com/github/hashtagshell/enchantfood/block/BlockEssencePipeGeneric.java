package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileEssencePump;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEssencePipeGeneric extends BlockTileGeneric<TileEssencePump> {
    public BlockEssencePipeGeneric(String name, Material mat) {
        super(name, mat);
    }

    @Override
    public Class<TileEssencePump> tileClass() {
        return TileEssencePump.class;
    }

    @Override
    public String tileId() {
        return Ref.Blocks.PUMP_ESSENCE_BASIC;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEssencePump();
    }
}
