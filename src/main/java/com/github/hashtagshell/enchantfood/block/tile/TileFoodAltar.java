package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.AltarMultiblock;
import com.github.hashtagshell.enchantfood.block.BlockMultiblockFoodAltar;
import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.init.ModBlocks;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileFoodAltar extends TileGeneric implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(1);

    public static final String INVENTORY_NBT = "inventory";
    public static final String REM_PROGRESS_NBT = "remainingProgress";
    public static final String WORKING_NBT = "isWorking";
    public static final String MULTIBLOCK_NBT = "isMultiblock";

    public boolean working = false;
    public int remainingProgress = 0;

    public boolean isValidMultiblock = false;

    private final int SUCCESS_MULTIBLOCK_PARTICLE_COUNT = 64;

    private BlockPos[] MB_GOLD_BLOCKS = {
            new BlockPos(2, -1, 2), new BlockPos(2, -1, -2),
            new BlockPos(-2, -1, 2), new BlockPos(-2, -1, -2),
    };

    private EnumFacing[] MB_GOLD_BLOCKS_ROTATIONS = {
            EnumFacing.NORTH, EnumFacing.NORTH,
            EnumFacing.NORTH, EnumFacing.NORTH
    };

    private BlockPos[] MB_STONE_SLABS = {
            new BlockPos(1, -1, 1), new BlockPos(1, -1, -1),
            new BlockPos(-1, -1, 1), new BlockPos(-1, -1, -1)
    };

    private EnumFacing[] MB_STONE_SLABS_ROTATIONS = {
            EnumFacing.EAST, EnumFacing.NORTH,
            EnumFacing.SOUTH, EnumFacing.WEST
    };

    private BlockPos[] MB_MAGMA_BLOCKS = {
            new BlockPos(2, -1, 1), new BlockPos(2, -1, -1),
            new BlockPos(1, -1, -2), new BlockPos(-1, -1, -2),
            new BlockPos(-2, -1, 1), new BlockPos(-2, -1, -1),
            new BlockPos(1, -1, 2), new BlockPos(-1, -1, 2),
    };

    private EnumFacing[] MB_MAGMA_BLOCKS_ROTATIONS = {
            EnumFacing.EAST, EnumFacing.EAST,
            EnumFacing.NORTH, EnumFacing.NORTH,
            EnumFacing.WEST, EnumFacing.WEST,
            EnumFacing.SOUTH, EnumFacing.SOUTH
    };

    private BlockPos[] MB_ESSENCE_FOCUSERS = {
            new BlockPos(2, -1, 0),
            new BlockPos(0, -1, 2),
            new BlockPos(-2, -1, 0),
            new BlockPos(0, -1, -2)
    };

    private BlockPos[] MB_ITEM_TABLES = {
            new BlockPos(2, 0, 0),
            new BlockPos(0, 0, 2),
            new BlockPos(-2, 0, 0),
            new BlockPos(0, 0, -2)
    };

    private BlockPos MB_ESSENCE_PROVIDER = new BlockPos(0, -1, 0);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        remainingProgress = compound.getInteger(REM_PROGRESS_NBT);
        inventory.deserializeNBT(compound.getCompoundTag(INVENTORY_NBT));
        working = compound.getBoolean(WORKING_NBT);
        isValidMultiblock = compound.getBoolean(MULTIBLOCK_NBT);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag(INVENTORY_NBT, inventory.serializeNBT());
        compound.setBoolean(WORKING_NBT, working);
        compound.setInteger(REM_PROGRESS_NBT, remainingProgress);
        compound.setBoolean(MULTIBLOCK_NBT, isValidMultiblock);
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(3, 0, 3, 11, 5, 11);
    }

    @Override
    public void update() {
        if (isValidMultiblock) {
            //Process
        }
    }

    public void wrenchClick() {
        if (checkMultiblock()) {
            if (!isValidMultiblock) {
                isValidMultiblock = true;
                spawnMultiblockSuccessParticles();
                activateMultiblock();
            }
        } else {
            int r = 2;
            for (int i = 0; i < SUCCESS_MULTIBLOCK_PARTICLE_COUNT; i++) {
                double x = Math.sin(2 * Math.PI / SUCCESS_MULTIBLOCK_PARTICLE_COUNT * i) * r + 0.5 + getPos().getX();
                double y = getPos().getY() + 0.5;
                double z = Math.cos(2 * Math.PI / SUCCESS_MULTIBLOCK_PARTICLE_COUNT * i) * r + 0.5 + getPos().getZ();
                world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, x, y, z, 0, 0.3F, 0);
            }
        }
    }

    private boolean checkMultiblock() {
        return checkBlockSet(MB_ESSENCE_FOCUSERS, ModBlocks.essenceFocuser) &&
                checkBlockSet(MB_GOLD_BLOCKS, Blocks.GOLD_BLOCK) &&
                checkBlockSet(MB_ITEM_TABLES, ModBlocks.itemTable) &&
                checkBlockSet(MB_STONE_SLABS, Blocks.STONE_SLAB) &&
                checkBlockSet(MB_MAGMA_BLOCKS, Blocks.MAGMA) &&
                checkBlock(MB_ESSENCE_PROVIDER, ModBlocks.essenceProvider);
    }

    private void placeOldBlocks() {
        BlockPos[] MB_ESSENCE_PROVIDER_ARRAY = {MB_ESSENCE_PROVIDER};
        placeOldBlockSet(MB_ESSENCE_FOCUSERS);
        placeOldBlockSet(MB_GOLD_BLOCKS);
        placeOldBlockSet(MB_ITEM_TABLES);
        placeOldBlockSet(MB_STONE_SLABS);
        placeOldBlockSet(MB_MAGMA_BLOCKS);
        placeOldBlockSet(MB_ESSENCE_PROVIDER_ARRAY);
    }

    public void destroyMultiblock() {
        placeOldBlocks();
        isValidMultiblock = false;
        NetworkWrapper.dispatchTEToNearbyPlayers(this);
    }

    private void placeOldBlockSet(BlockPos[] offsets) {
        for (int i = 0; i < offsets.length; i++) {
            if (!world.isAirBlock(getPos().add(offsets[i]))) {
                TileMultiblockFoodAltar tmfa = (TileMultiblockFoodAltar) world.getTileEntity(getPos().add(offsets[i]));
                tmfa.revertMultiblock();
            }
        }
    }

    private boolean checkBlockSet(BlockPos[] pos, Block block) {
        for (BlockPos poz : pos) {
            if (!checkBlock(poz, block)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBlock(BlockPos pos, Block block) {
        return world.getBlockState(getPos().add(pos.getX(), pos.getY(), pos.getZ())).getBlock() == block;
    }

    private void activateMultiblock() {
        replaceMultiblocks(MB_STONE_SLABS, MB_STONE_SLABS_ROTATIONS, Blocks.STONE_SLAB, AltarMultiblock.CORNER);
        replaceMultiblocks(MB_MAGMA_BLOCKS, MB_MAGMA_BLOCKS_ROTATIONS, Blocks.MAGMA, AltarMultiblock.MAGMA);
        replaceMultiblocks(MB_GOLD_BLOCKS, MB_MAGMA_BLOCKS_ROTATIONS, Blocks.GOLD_BLOCK, AltarMultiblock.GOLD);
        NetworkWrapper.dispatchTEToNearbyPlayers(this);
    }

    private void replaceMultiblocks(BlockPos[] offsets, EnumFacing[] rotations, Block baseBlock, AltarMultiblock multiBlockType) {
        for (int i = 0; i < offsets.length; i++) {
            world.setBlockState(getPos().add(offsets[i]), ModBlocks.multiblockFoodAltar.getDefaultState()
                    .withProperty(BlockMultiblockFoodAltar.FACING, rotations[i])
                    .withProperty(BlockMultiblockFoodAltar.ALTAR_PART, multiBlockType));

            TileMultiblockFoodAltar tmfa = (TileMultiblockFoodAltar) world.getTileEntity(getPos().add(offsets[i]));
            tmfa.altar = this;
            tmfa.prevBlock = baseBlock;
        }
    }

    private void sendToAroundUpdate() {

    }

    private void spawnWorkingParticles() {

    }

    private void spawnMultiblockSuccessParticles() {
        int r = 2;
        for (int i = 0; i < SUCCESS_MULTIBLOCK_PARTICLE_COUNT; i++) {
            double x = Math.sin(2 * Math.PI / SUCCESS_MULTIBLOCK_PARTICLE_COUNT * i) * r + 0.5 + getPos().getX();
            double y = getPos().getY() + 0.5;
            double z = Math.cos(2 * Math.PI / SUCCESS_MULTIBLOCK_PARTICLE_COUNT * i) * r + 0.5 + getPos().getZ();
            world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, 0, 0.3F, 0);
        }
    }
}
