package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import com.github.hashtagshell.enchantfood.reference.RefBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFoodEnchanter extends BlockTileGeneric<TileFoodEnchanter> {
    public BlockFoodEnchanter(String name) {
        super(name, Material.ROCK);
        setSoundType(SoundType.STONE);
    }

    @Override
    public Class<TileFoodEnchanter> tileClass() {
        return TileFoodEnchanter.class;
    }

    @Override
    public String tileId() {
        return RefBlocks.FOOD_ENCHANTER;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFoodEnchanter();
    }
}
