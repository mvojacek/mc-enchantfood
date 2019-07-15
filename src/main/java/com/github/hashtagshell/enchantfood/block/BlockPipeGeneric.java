package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceConsumer;
import com.github.hashtagshell.enchantfood.essence.IEssencePump;
import com.github.hashtagshell.enchantfood.essence.pipe.IPipe;
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

import javax.annotation.Nullable;

public class BlockPipeGeneric extends BlockGeneric implements IPipe {
    public static final PropertyBool CONNECT_NORTH = PropertyBool.create("pipe_north");
    public static final PropertyBool CONNECT_SOUTH = PropertyBool.create("pipe_south");
    public static final PropertyBool CONNECT_EAST = PropertyBool.create("pipe_east");
    public static final PropertyBool CONNECT_WEST = PropertyBool.create("pipe_west");
    public static final PropertyBool CONNECT_UP = PropertyBool.create("pipe_up");
    public static final PropertyBool CONNECT_DOWN = PropertyBool.create("pipe_down");

    private int tier;
    private double pipeRadius;

    public BlockPipeGeneric(String name, Material mat, int tier, double pipeRadius) {
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
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        AxisAlignedBB[] hitboxes = new AxisAlignedBB[6];



        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        checkConnections(worldIn, pos);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    private void checkConnections(World worldIn, BlockPos pos) {
        if (worldIn.isRemote) {
            worldIn.setBlockState(pos, this.getActualState(this.getDefaultState(), worldIn, pos));
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        checkConnections(worldIn, pos);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        IProperty[] propertyArray = {CONNECT_NORTH, CONNECT_SOUTH, CONNECT_WEST, CONNECT_EAST, CONNECT_UP, CONNECT_DOWN};
        return new BlockStateContainer(this, propertyArray);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState beforeState = this.getDefaultState();


        return beforeState
                .withProperty(CONNECT_EAST, canConnectTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(CONNECT_WEST, canConnectTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(CONNECT_SOUTH, canConnectTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(CONNECT_NORTH, canConnectTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(CONNECT_UP, canConnectTo(worldIn, pos, EnumFacing.UP))
                .withProperty(CONNECT_DOWN, canConnectTo(worldIn, pos, EnumFacing.DOWN));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
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
    public int getTier() {
        return this.tier;
    }

    private boolean canConnectTo(IBlockAccess world, BlockPos mypos, EnumFacing facing) {
        TileEntity te;
        Block pipe;
        switch (facing) {
            case EAST:
                BlockPos eastOffset = new BlockPos(1, 0, 0);
                te = world.getTileEntity(mypos.add(eastOffset));
                pipe = world.getBlockState(mypos.add(eastOffset)).getBlock();
                return pipe instanceof IPipe && (((IPipe) pipe).getTier() == this.getTier())
                        || te instanceof IEssencePump && ((IEssencePump) te).getTier() == this.getTier() && ((IEssencePump) te).canConnectFromSide(EnumFacing.EAST)
                        || te instanceof IEssenceConsumer;
            case WEST:
                BlockPos westOffset = new BlockPos(-1, 0, 0);
                te = world.getTileEntity(mypos.add(westOffset));
                pipe = world.getBlockState(mypos.add(westOffset)).getBlock();
                return pipe instanceof IPipe && (((IPipe) pipe).getTier() == this.getTier())
                        || te instanceof IEssencePump && ((IEssencePump) te).getTier() == this.getTier() && ((IEssencePump) te).canConnectFromSide(EnumFacing.WEST)
                        || te instanceof IEssenceConsumer;
            case SOUTH:
                BlockPos southOffset = new BlockPos(0, 0, -1);
                te = world.getTileEntity(mypos.add(southOffset));
                pipe = world.getBlockState(mypos.add(southOffset)).getBlock();
                return pipe instanceof IPipe && (((IPipe) pipe).getTier() == this.getTier())
                        || te instanceof IEssencePump && ((IEssencePump) te).getTier() == this.getTier() && ((IEssencePump) te).canConnectFromSide(EnumFacing.SOUTH)
                        || te instanceof IEssenceConsumer;
            case NORTH:
                BlockPos northOffset = new BlockPos(0, 0, 1);
                te = world.getTileEntity(mypos.add(northOffset));
                pipe = world.getBlockState(mypos.add(northOffset)).getBlock();
                return pipe instanceof IPipe && (((IPipe) pipe).getTier() == this.getTier())
                        || te instanceof IEssencePump && ((IEssencePump) te).getTier() == this.getTier() && ((IEssencePump) te).canConnectFromSide(EnumFacing.NORTH)
                        || te instanceof IEssenceConsumer;
            case UP:
                BlockPos upOffset = new BlockPos(0, 1, 0);
                te = world.getTileEntity(mypos.add(upOffset));
                pipe = world.getBlockState(mypos.add(upOffset)).getBlock();
                return pipe instanceof IPipe && (((IPipe) pipe).getTier() == this.getTier())
                        || te instanceof IEssencePump && ((IEssencePump) te).getTier() == this.getTier() && ((IEssencePump) te).canConnectFromSide(EnumFacing.UP)
                        || te instanceof IEssenceConsumer;
            case DOWN:
                BlockPos downOffset = new BlockPos(0, -1, 0);
                te = world.getTileEntity(mypos.add(downOffset));
                pipe = world.getBlockState(mypos.add(downOffset)).getBlock();
                return pipe instanceof IPipe && (((IPipe) pipe).getTier() == this.getTier())
                        || te instanceof IEssencePump && ((IEssencePump) te).getTier() == this.getTier() && ((IEssencePump) te).canConnectFromSide(EnumFacing.DOWN)
                        || te instanceof IEssenceConsumer;
            default:
                return false;
        }
    }
}
