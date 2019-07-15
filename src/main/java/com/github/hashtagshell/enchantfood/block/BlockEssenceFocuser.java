package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileEssenceFocuser;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEssenceFocuser extends BlockTileGeneric<TileEssenceFocuser> implements IRotatable {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public BlockEssenceFocuser(String name) {
        super(name, Material.WOOD);
        setSoundType(SoundType.WOOD);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing enumfacing = EnumFacing.HORIZONTALS[meta];

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        int meta = state.getValue(FACING).getHorizontalIndex();

        return meta;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        EnumFacing placerFacing = placer.getHorizontalFacing();
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placerFacing.getOpposite()));
    }

    @Override
    public Class<TileEssenceFocuser> tileClass() {
        return TileEssenceFocuser.class;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public String tileId() {
        return Ref.Blocks.ESSENCE_FOCUSER;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEssenceFocuser();
    }

    @Override
    public FacingType getFacingType() {
        return FacingType.HORIZONTAL;
    }

    @Override
    public EnumFacing getRotation(World w, BlockPos pos) {
        return w.getBlockState(pos).getValue(FACING);
    }

    @Override
    public void setFacing(World w, BlockPos pos, EnumFacing facing) {
        w.setBlockState(pos, this.getDefaultState().withProperty(FACING, facing));
    }
}
