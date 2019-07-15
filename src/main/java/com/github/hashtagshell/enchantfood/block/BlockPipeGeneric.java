package com.github.hashtagshell.enchantfood.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceConsumer;
import com.github.hashtagshell.enchantfood.essence.IEssencePump;
import com.github.hashtagshell.enchantfood.essence.pipe.IPipe;

import javax.annotation.Nullable;

public class BlockPipeGeneric extends BlockGeneric implements IPipe
{
    public static final PropertyBool CONNECT_NORTH = PropertyBool.create("pipe_north");
    public static final PropertyBool CONNECT_SOUTH = PropertyBool.create("pipe_south");
    public static final PropertyBool CONNECT_EAST  = PropertyBool.create("pipe_east");
    public static final PropertyBool CONNECT_WEST  = PropertyBool.create("pipe_west");
    public static final PropertyBool CONNECT_UP    = PropertyBool.create("pipe_up");
    public static final PropertyBool CONNECT_DOWN  = PropertyBool.create("pipe_down");

    private int    tier;
    private double pipeRadius;

    public BlockPipeGeneric(String name, Material mat, int tier, double pipeRadius)
    {
        super(name, mat);
        this.tier = tier;
        setDefaultState(
                this.blockState.getBaseState()
                               .withProperty(CONNECT_NORTH, false)
                               .withProperty(CONNECT_SOUTH, false)
                               .withProperty(CONNECT_EAST, false)
                               .withProperty(CONNECT_WEST, false)
                               .withProperty(CONNECT_UP, false)
                               .withProperty(CONNECT_DOWN, false));
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        AxisAlignedBB[] hitboxes = new AxisAlignedBB[6];


        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        checkConnections(worldIn, pos);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    private void checkConnections(World worldIn, BlockPos pos)
    {
        if (worldIn.isRemote)
        {
            worldIn.setBlockState(pos, this.getActualState(this.getDefaultState(), worldIn, pos));
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack)
    {
        checkConnections(worldIn, pos);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        IProperty[] propertyArray = {CONNECT_NORTH, CONNECT_SOUTH, CONNECT_WEST, CONNECT_EAST, CONNECT_UP, CONNECT_DOWN};
        return new BlockStateContainer(this, propertyArray);
    }

    /**
     * Can return IExtendedBlockState
     */
    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        IBlockState beforeState = this.getDefaultState();

        return beforeState
                .withProperty(CONNECT_EAST, canConnectTo(world, pos, EnumFacing.EAST))
                .withProperty(CONNECT_WEST, canConnectTo(world, pos, EnumFacing.WEST))
                .withProperty(CONNECT_SOUTH, canConnectTo(world, pos, EnumFacing.SOUTH))
                .withProperty(CONNECT_NORTH, canConnectTo(world, pos, EnumFacing.NORTH))
                .withProperty(CONNECT_UP, canConnectTo(world, pos, EnumFacing.UP))
                .withProperty(CONNECT_DOWN, canConnectTo(world, pos, EnumFacing.DOWN));

    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        /*
        boolean north = state.getValue(CONNECT_NORTH);
        boolean south = state.getValue(CONNECT_SOUTH);
        boolean east = state.getValue(CONNECT_EAST);
        boolean west = state.getValue(CONNECT_WEST);
        boolean up = state.getValue(CONNECT_UP);
        boolean down = state.getValue(CONNECT_DOWN);

        int northPipe = north ? 1 : 0;
        int southPipe = south ? 1 : 0;
        int eastPipe = east ? 1 : 0;
        int westPipe = west ? 1 : 0;
        int upPipe = up ? 1 : 0;
        int downPipe = down ? 1 : 0;

        return (northPipe & 0b1) | ((southPipe & 0b11) << 1) | ((eastPipe & 0b11) << 2) | ((westPipe & 0b11) << 3);// | ((upPipe & 0b11) << 4) | ((downPipe & 0b11) << 5);
        */
        return 1;
    }

    @Override
    public int getTier()
    {
        return this.tier;
    }

    private boolean canConnectTo(IBlockAccess world, BlockPos mypos, EnumFacing direction)
    {
        BlockPos otherPos = mypos.offset(direction);
        TileEntity otherTE = world.getTileEntity(otherPos);
        IBlockState otherState = world.getBlockState(otherPos);
        Block otherBlock = otherState.getBlock();

        return otherBlock instanceof IPipe && (((IPipe) otherBlock).getTier() == this.getTier())
               || otherTE instanceof IEssencePump && ((IEssencePump) otherTE).getTier() == this.getTier() && ((IEssencePump) otherTE).canConnectFromSide(direction.getOpposite())
               || otherTE instanceof IEssenceConsumer;
    }
}
