package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileEssenceProvider;
import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEssenceProvider extends BlockTileGeneric<TileEssenceProvider> {
    public BlockEssenceProvider(String name) {
        super(name, Material.ROCK);
        setSoundType(SoundType.STONE);
    }

    @Override
    public Class<TileEssenceProvider> tileClass() {
        return TileEssenceProvider.class;
    }

    @Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side) {
        return true;
    }


    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEssenceProvider tileEssenceProvider = (TileEssenceProvider) world.getTileEntity(pos);
        if (world.getStrongPower(pos, EnumFacing.NORTH) > 0) {
            tileEssenceProvider.enabled = false;
        } else {
            tileEssenceProvider.enabled = false;
        }
        super.onNeighborChange(world, pos, neighbor);
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public String tileId() {
        return Ref.Blocks.ESSENCE_PROVIDER;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(1.0 / 16.0 * 3.0, 0.0, 1.0 / 16.0 * 3.0, 1.0 / 16.0 * 13.0, 1.0, 1.0 / 16.0 * 13.0);
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(1.0 / 16.0 * 3.0, 0.0, 1.0 / 16.0 * 3.0, 1.0 / 16.0 * 13.0, 1.0, 1.0 / 16.0 * 13.0);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEssenceProvider();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getTileEntity(pos.add(0, 1, 0)) != null && worldIn.getTileEntity(pos.add(0, 1, 0)) instanceof TileFoodEnchanter) {
            worldIn.destroyBlock(pos.add(0, 1, 0), true);
        }
        super.breakBlock(worldIn, pos, state);
    }
}
