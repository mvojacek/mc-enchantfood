package com.github.hashtagshell.enchantfood.block.lib.pipe;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceStorage;
import com.github.hashtagshell.enchantfood.init.ModBlocks;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockPipeGeneric extends BlockTileGeneric<TilePipeGeneric> {
    public static final PropertyBool CONNECT_NORTH = PropertyBool.create("pipe_north");
    public static final PropertyBool CONNECT_SOUTH = PropertyBool.create("pipe_south");
    public static final PropertyBool CONNECT_EAST = PropertyBool.create("pipe_east");
    public static final PropertyBool CONNECT_WEST = PropertyBool.create("pipe_west");
    public static final PropertyBool CONNECT_UP = PropertyBool.create("pipe_up");
    public static final PropertyBool CONNECT_DOWN = PropertyBool.create("pipe_down");

    private String name;

    public BlockPipeGeneric(String name, Material mat) {
        super(name, mat);
        this.name = name;
        setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(CONNECT_NORTH, false)
                        .withProperty(CONNECT_SOUTH, false)
                        .withProperty(CONNECT_EAST, false)
                        .withProperty(CONNECT_WEST, false)
                        .withProperty(CONNECT_UP, false)
                        .withProperty(CONNECT_DOWN, false));
    }

    @Override
    public Class<TilePipeGeneric> tileClass() {
        return TilePipeGeneric.class;
    }

    @Override
    public String tileId() {
        return name;
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
        return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        checkConnections(worldIn, pos);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    private void checkConnections(World worldIn, BlockPos pos) {
        TilePipeGeneric myTE = (TilePipeGeneric) worldIn.getTileEntity(pos);

        if (myTE != null) {
            TileEntity northTEntity = null;
            TileEntity southTEntity = null;
            TileEntity eastTEntity = null;
            TileEntity westTEntity = null;
            TileEntity upTEntity = null;
            TileEntity downTEntity = null;

            if (worldIn.getTileEntity(pos.add(1, 0, 0)) != null) {
                eastTEntity = worldIn.getTileEntity(pos.add(1, 0, 0));
            }
            if (worldIn.getTileEntity(pos.add(-1, 0, 0)) != null) {
                westTEntity = worldIn.getTileEntity(pos.add(-1, 0, 0));
            }
            if (worldIn.getTileEntity(pos.add(0, 0, 1)) != null) {
                southTEntity = worldIn.getTileEntity(pos.add(0, 0, 1));
            }
            if (worldIn.getTileEntity(pos.add(0, 0, -1)) != null) {
                northTEntity = worldIn.getTileEntity(pos.add(0, 0, -1));
            }
            if (worldIn.getTileEntity(pos.add(0, 1, 0)) != null) {
                upTEntity = worldIn.getTileEntity(pos.add(0, 1, 0));
            }
            if (worldIn.getTileEntity(pos.add(0, -1, 0)) != null) {
                downTEntity = worldIn.getTileEntity(pos.add(0, -1, 0));
            }

            myTE.eastConnected = worldIn.getBlockState(pos.add(1, 0, 0)).getBlock() == ModBlocks.advanced_pipe || eastTEntity instanceof IEssenceStorage;
            myTE.westConnected = worldIn.getBlockState(pos.add(-1, 0, 0)).getBlock() == ModBlocks.advanced_pipe || westTEntity instanceof IEssenceStorage;
            myTE.southConnected = worldIn.getBlockState(pos.add(0, 0, 1)).getBlock() == ModBlocks.advanced_pipe || southTEntity instanceof IEssenceStorage;
            myTE.northConnected = worldIn.getBlockState(pos.add(0, 0, -1)).getBlock() == ModBlocks.advanced_pipe || northTEntity instanceof IEssenceStorage;
            myTE.upConnected = worldIn.getBlockState(pos.add(0, 1, 0)).getBlock() == ModBlocks.advanced_pipe || upTEntity instanceof IEssenceStorage;
            myTE.downConnected = worldIn.getBlockState(pos.add(0, -1, 0)).getBlock() == ModBlocks.advanced_pipe || downTEntity instanceof IEssenceStorage;
            NetworkWrapper.dispatchTEToNearbyPlayers(myTE);
            if (worldIn.isRemote) {
                worldIn.setBlockState(pos, this.getActualState(this.getDefaultState(), worldIn, pos));
            }
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
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.removeTileEntity(pos);
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState beforeState = this.getDefaultState();

        TilePipeGeneric myTE = (TilePipeGeneric) worldIn.getTileEntity(pos);

        if (myTE != null) {
            if (myTE.eastConnected) {
                beforeState = beforeState.withProperty(CONNECT_EAST, true);
            }
            if (myTE.westConnected) {
                beforeState = beforeState.withProperty(CONNECT_WEST, true);
            }
            if (myTE.southConnected) {
                beforeState = beforeState.withProperty(CONNECT_SOUTH, true);
            }
            if (myTE.northConnected) {
                beforeState = beforeState.withProperty(CONNECT_NORTH, true);
            }
            if (myTE.upConnected) {
                beforeState = beforeState.withProperty(CONNECT_UP, true);
            }
            if (myTE.downConnected) {
                beforeState = beforeState.withProperty(CONNECT_DOWN, true);
            }
        }

        return beforeState;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
/*

        boolean north =    ( meta & 0b1)    == 1;
        boolean south = ((meta >> 1) & 0b1) == 1;
        boolean east =  ((meta >> 2) & 0b1) == 1;
        boolean west =  ((meta >> 3) & 0b1) == 1;
        boolean up =    ((meta >> 4) & 0b1) == 1;
        boolean down =  ((meta >> 5) & 0b1) == 1;

        return this.getDefaultState()
                .withProperty(CONNECT_NORTH, north)
                .withProperty(CONNECT_SOUTH, south)
                .withProperty(CONNECT_WEST, west)
                .withProperty(CONNECT_EAST, east);
        //        .withProperty(CONNECT_UP, up)
        //        .withProperty(CONNECT_DOWN, down);
*/
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

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePipeGeneric();
    }
}
