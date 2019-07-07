package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileFoodAltar;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFoodAltar extends BlockTileGeneric<TileFoodAltar> {
    public BlockFoodAltar(String name) {
        super(name, Material.WOOD);
    }

    @Override
    public Class<TileFoodAltar> tileClass() {
        return TileFoodAltar.class;
    }

    @Override
    public String tileId() {
        return Ref.Blocks.FOOD_ALTAR;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(1.0 / 16.0 * 3.0, 0.0, 1.0 / 16.0 * 3.0, 1.0 / 16.0 * 13.0, 1.0 / 16.0 * 8.0, 1.0 / 16.0 * 13.0);
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(1.0 / 16.0 * 3.0, 0.0, 1.0 / 16.0 * 3.0, 1.0 / 16.0 * 13.0, 1.0 / 16.0 * 8.0, 1.0 / 16.0 * 13.0);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFoodAltar();
    }
}
