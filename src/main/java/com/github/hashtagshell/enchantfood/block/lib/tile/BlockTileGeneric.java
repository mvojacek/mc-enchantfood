package com.github.hashtagshell.enchantfood.block.lib.tile;

import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BlockTileGeneric<T extends TileEntity> extends BlockGeneric implements ITileEntityProvider {
    public BlockTileGeneric(String name, Material mat) {
        super(name, mat);
        isBlockContainer = true;
    }

    @SuppressWarnings("deprecation") // Why is this deprecated and what is the replacement?
    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    // Abstract Tile getters

    public abstract Class<T> tileClass();

    public abstract String tileId();

    // Helpers

    @SuppressWarnings("unchecked") // - Checked with isAssignableFrom
    @Nullable
    public T getTileAt(IBlockAccess w, BlockPos pos) {
        TileEntity tile = w.getTileEntity(pos);
        return tile != null && tileClass().isAssignableFrom(tile.getClass()) ? (T) tile : null;
    }

    public boolean runTile(IBlockAccess w, BlockPos pos, Consumer<T> consumer) {
        T tile = getTileAt(w, pos);
        if (tile == null)
            return false;
        consumer.accept(tile);
        return true;
    }

    // Lambda constructors

    public static <T extends TileEntity> BlockTileGeneric<T> lambda(String name, Material mat, String tileId,
                                                                    Class<T> tileClass, Supplier<T> createNewTile) {
        return lambda(name, mat, tileId, tileClass, (world, meta) -> createNewTile.get());
    }

    public static <T extends TileEntity> BlockTileGeneric<T> lambda(String name, Material mat, String tileId,
                                                                    Class<T> tileClass,
                                                                    BiFunction<World, Integer, T> createNewTile) {
        return new BlockTileGeneric<T>(name, mat) {
            @Override
            public TileEntity createNewTileEntity(World worldIn, int meta) {
                return createNewTile.apply(worldIn, meta);
            }

            @Override
            public Class<T> tileClass() {
                return tileClass;
            }

            @Override
            public String tileId() {
                return tileId;
            }
        };
    }
}
