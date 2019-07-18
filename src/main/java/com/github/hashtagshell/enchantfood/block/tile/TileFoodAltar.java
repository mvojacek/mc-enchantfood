package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.AltarMultiblockType;
import com.github.hashtagshell.enchantfood.block.BlockMultiblockFoodAltar;
import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceConsumer;
import com.github.hashtagshell.enchantfood.init.ModBlocks;
import com.github.hashtagshell.enchantfood.init.ModRecipes;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import com.github.hashtagshell.enchantfood.recipes.RecipeEssenceInfusion;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Random;

public class TileFoodAltar extends TileGeneric implements ITickable, IEssenceConsumer {

    public ItemStackHandler inventory = new ItemStackHandler(1);

    public static final String INVENTORY_NBT           = "inventory";
    public static final String REM_PROGRESS_NBT        = "remainingProgress";
    public static final String WORKING_NBT             = "isWorking";
    public static final String MULTIBLOCK_NBT          = "isMultiblock";
    public static final String RESULT_NBT              = "resultOfCurrentOperation";
    public static final String INFUSES_REMAINING_NBT   = "infusesRemaining";
    public static final String CURRENT_RECIPE_COST_NBT = "totalCost";


    public        ItemStack            result            = ItemStack.EMPTY;
    private       ArrayList<ItemStack> infusesRemaining  = new ArrayList<ItemStack>();
    public        boolean              working           = false;
    public        int                  remainingProgress = 0;
    public        int                  fullRecipeCost    = 0;
    private       int                  currentFuel       = 0;
    private final int                  maxFuel           = 1024;

    public boolean isValidMultiblock = false;

    private final int MAX_ESSENCE_PER_TICK              = 128;
    private final int SUCCESS_MULTIBLOCK_PARTICLE_COUNT = 64;

    private Random random = new Random();

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
        fullRecipeCost = compound.getInteger(CURRENT_RECIPE_COST_NBT);
        result.deserializeNBT(compound.getCompoundTag(RESULT_NBT));
        NBTTagList nbtlist = compound.getTagList(INFUSES_REMAINING_NBT, 0);
        infusesRemaining.clear();
        for (int i = 0; i < nbtlist.tagCount(); i++) {
            ItemStack item = ItemStack.EMPTY;
            item.deserializeNBT(nbtlist.getCompoundTagAt(i));
            infusesRemaining.add(item);
        }
        inventory.deserializeNBT(compound.getCompoundTag(INVENTORY_NBT));
        working = compound.getBoolean(WORKING_NBT);
        isValidMultiblock = compound.getBoolean(MULTIBLOCK_NBT);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(REM_PROGRESS_NBT, remainingProgress);
        compound.setInteger(CURRENT_RECIPE_COST_NBT, fullRecipeCost);
        compound.setTag(RESULT_NBT, result.serializeNBT());
        if (infusesRemaining.size() > 0) {
            NBTTagList nbtList = new NBTTagList();
            for (ItemStack item : infusesRemaining) {
                nbtList.appendTag(item.serializeNBT());
            }
            compound.setTag(INFUSES_REMAINING_NBT, nbtList);
        }
        compound.setTag(INVENTORY_NBT, inventory.serializeNBT());
        compound.setBoolean(WORKING_NBT, working);
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

    @SuppressWarnings("unchecked")
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
            if (working) {
                boolean didSomething = false;
                if (!world.isRemote && currentFuel > 0) {
                    int fuelTake = Math.min(MAX_ESSENCE_PER_TICK, Math.min(currentFuel, remainingProgress));
                    currentFuel -= fuelTake;
                    remainingProgress -= fuelTake;
                    didSomething = true;
                }

                //if checkTables contains infuse material

                if (!world.isRemote && remainingProgress <= 0) {
                    fullRecipeCost = 0;
                    remainingProgress = 0;
                    working = false;
                    inventory.setStackInSlot(0, result.copy());
                    result = ItemStack.EMPTY;
                    NetworkWrapper.dispatchTEToNearbyPlayers(this);
                }

                if (!didSomething) {
                    //Spawn missing something particles
                    if (random.nextDouble() * 100 == 1) {
                        remainingProgress = Math.min(remainingProgress + 10, fullRecipeCost);
                    }
                } else {
                    spawnWorkingParticles();
                }
            }

            if (working && inventory.getStackInSlot(0).isEmpty()) {
                remainingProgress = 0;
                fullRecipeCost = 0;
                result = ItemStack.EMPTY;
                working = false;
            }
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
                getWorld().spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, x, y, z, 0, 0.3D, 0);
            }
        }

        if (isValidMultiblock && !working) {
            for (RecipeEssenceInfusion recipe : ModRecipes.essenceInfusions) {
                if (inventory.getStackInSlot(0).getItem() == recipe.craftingReagment.getItem()) {
                    remainingProgress = recipe.essenceCost;
                    fullRecipeCost = recipe.essenceCost;
                    result = recipe.result.copy();
                    if (recipe.isInfusing) {
                        infusesRemaining.addAll(recipe.infuses);
                    }
                    working = true;
                    NetworkWrapper.dispatchTEToNearbyPlayers(this);
                }
            }
        }
    }

    private boolean checkMultiblock() {
        if (isValidMultiblock) {
            return checkBlockSet(MB_GOLD_BLOCKS, ModBlocks.multiblockFoodAltar.getDefaultState(), false) &&
                    checkBlockSet(MB_STONE_SLABS, ModBlocks.multiblockFoodAltar.getDefaultState(), false) &&
                    checkBlockSet(MB_MAGMA_BLOCKS, ModBlocks.multiblockFoodAltar.getDefaultState(), false);
        }
        return checkBlockSet(MB_ESSENCE_FOCUSERS, ModBlocks.essenceFocuser.getDefaultState(), false) &&
                checkBlockSet(MB_GOLD_BLOCKS, Blocks.GOLD_BLOCK.getDefaultState(), false) &&
                checkBlockSet(MB_ITEM_TABLES, ModBlocks.itemTable.getDefaultState(), false) &&
                checkBlockSet(MB_STONE_SLABS, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK), true) &&
                checkBlockSet(MB_MAGMA_BLOCKS, Blocks.MAGMA.getDefaultState(), false) &&
                checkBlock(MB_ESSENCE_PROVIDER, ModBlocks.essenceProvider.getDefaultState(), false);
    }

    private void placeOldBlocks() {
        placeOldBlockSet(MB_GOLD_BLOCKS, Blocks.GOLD_BLOCK.getDefaultState());
        placeOldBlockSet(MB_STONE_SLABS, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK));
        placeOldBlockSet(MB_MAGMA_BLOCKS, Blocks.MAGMA.getDefaultState());
    }

    public void destroyMultiblock() {
        placeOldBlocks();
        isValidMultiblock = false;
        NetworkWrapper.dispatchTEToNearbyPlayers(this);
    }

    private void placeOldBlockSet(BlockPos[] offsets, IBlockState block) {
        for (BlockPos offset : offsets) {
            if (!world.isAirBlock(getPos().add(offset))) {
                world.setBlockState(getPos().add(offset), block);
            }
        }
    }

    private boolean checkBlockSet(BlockPos[] pos, IBlockState block, boolean exactBlockState) {
        for (BlockPos poz : pos) {
            if (!checkBlock(poz, block, exactBlockState)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBlock(BlockPos pos, IBlockState block, boolean exactBlockState) {
        if (exactBlockState) {
            return world.getBlockState(getPos().add(pos.getX(), pos.getY(), pos.getZ())) == block;

        }
        return world.getBlockState(getPos().add(pos.getX(), pos.getY(), pos.getZ())).getBlock() == block.getBlock();

    }

    private void activateMultiblock() {
        replaceMultiblocks(MB_STONE_SLABS, MB_STONE_SLABS_ROTATIONS, Blocks.STONE_SLAB.getDefaultState().withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.NETHERBRICK), AltarMultiblockType.CORNER);
        replaceMultiblocks(MB_MAGMA_BLOCKS, MB_MAGMA_BLOCKS_ROTATIONS, Blocks.MAGMA.getDefaultState(), AltarMultiblockType.MAGMA);
        replaceMultiblocks(MB_GOLD_BLOCKS, MB_MAGMA_BLOCKS_ROTATIONS, Blocks.GOLD_BLOCK.getDefaultState(), AltarMultiblockType.GOLD);
        NetworkWrapper.dispatchTEToNearbyPlayers(this);
    }

    private void replaceMultiblocks(BlockPos[] offsets, EnumFacing[] rotations, IBlockState baseBlock,
                                    AltarMultiblockType multiBlockType)
    {
        for (int i = 0; i < offsets.length; i++) {
            world.setBlockState(getPos().add(offsets[i]), ModBlocks.multiblockFoodAltar.getDefaultState()
                    .withProperty(BlockMultiblockFoodAltar.FACING, rotations[i])
                    .withProperty(BlockMultiblockFoodAltar.ALTAR_PART, multiBlockType));

            TileMultiblockFoodAltar tmfa = (TileMultiblockFoodAltar) world.getTileEntity(getPos().add(offsets[i]));
            assert tmfa != null;
            tmfa.altarPos = this.getPos();
        }
    }

    private void spawnWorkingParticles() {
        double radius      = 0.6;
        double totalHeight = 1.2;
        double density     = 4;
        double step        = Math.PI * (totalHeight / density);
        double x           = getPos().getX() + 0.5;
        double y           = getPos().getY() + 0.5;
        double z           = getPos().getZ() + 0.5;

        for (int i = 0; i < density; i++) {
            world.spawnParticle(
                    EnumParticleTypes.ENCHANTMENT_TABLE,
                    x + Math.sin(world.getTotalWorldTime() + i * step) * radius,
                    y + totalHeight / density * i,
                    z + Math.cos(world.getTotalWorldTime() + i * step) * radius,
                    Math.sin(world.getTotalWorldTime() + i * step) * 0.8,
                    totalHeight / density * i * 0.8,
                    Math.cos(world.getTotalWorldTime() + i * step) * 0.8);
        }

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

    @Override
    public void writePacketNBT(NBTTagCompound cmp) {
        cmp.setInteger(REM_PROGRESS_NBT, remainingProgress);
        cmp.setInteger(CURRENT_RECIPE_COST_NBT, fullRecipeCost);
        cmp.setTag(RESULT_NBT, result.serializeNBT());
        if (infusesRemaining.size() > 0) {
            NBTTagList nbtList = new NBTTagList();
            for (ItemStack item : infusesRemaining) {
                nbtList.appendTag(item.serializeNBT());
            }
            cmp.setTag(INFUSES_REMAINING_NBT, nbtList);
        }
        cmp.setTag(INVENTORY_NBT, inventory.serializeNBT());
        cmp.setBoolean(WORKING_NBT, working);
        cmp.setBoolean(MULTIBLOCK_NBT, isValidMultiblock);
    }

    @Override
    public void readPacketNBT(NBTTagCompound cmp) {
        remainingProgress = cmp.getInteger(REM_PROGRESS_NBT);
        fullRecipeCost = cmp.getInteger(CURRENT_RECIPE_COST_NBT);
        result.deserializeNBT(cmp.getCompoundTag(RESULT_NBT));
        NBTTagList nbtlist = cmp.getTagList(INFUSES_REMAINING_NBT, 0);
        infusesRemaining.clear();
        for (int i = 0; i < nbtlist.tagCount(); i++) {
            ItemStack item = ItemStack.EMPTY;
            item.deserializeNBT(nbtlist.getCompoundTagAt(i));
            infusesRemaining.add(item);
        }
        inventory.deserializeNBT(cmp.getCompoundTag(INVENTORY_NBT));
        working = cmp.getBoolean(WORKING_NBT);
        isValidMultiblock = cmp.getBoolean(MULTIBLOCK_NBT);
    }

    @Override
    public int getMaxEssencePerTick() {
        return MAX_ESSENCE_PER_TICK;
    }

    @Override
    public boolean getImConsuming() {
        return !isFull();
    }

    @Override
    public int getMaxEssence() {
        return maxFuel;
    }

    @Override
    public int getCurrentEssence() {
        return currentFuel;
    }

    @Override
    public void setEssence(int amount) {
        currentFuel = amount;
    }
}
