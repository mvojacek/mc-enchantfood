package com.github.hashtagshell.enchantfood.block.misc.struct;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTileStruct extends BlockTileGeneric<TileStruct> {
    public static final PropertyBool TILE = PropertyBool.create("tile");

    public BlockTileStruct(String name) {
        super(name, Material.BARRIER);
        setDefaultState(getBlockState().getBaseState().withProperty(TILE, false));
    }


    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TILE, (meta & 1) == 1);
    }


    @Override
    public int getMetaFromState(IBlockState state) {
        // (TILE: 1 | 0)
        return state.getValue(TILE) ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TILE);
    }

    @Override
    public Class<TileStruct> tileClass() {
        return TileStruct.class;
    }

    @Override
    public String tileId() {
        return "struct";
    }


    @Override
    public boolean hasTileEntity(IBlockState state) {
        return state.getValue(TILE);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return (meta & 1) == 1 ? new TileStruct() : null;
    }
}
