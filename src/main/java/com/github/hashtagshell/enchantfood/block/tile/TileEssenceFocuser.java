package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.BlockEssenceFocuser;
import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceConsumer;
import com.github.hashtagshell.enchantfood.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEssenceFocuser extends TileGeneric implements ITickable, IEssenceConsumer {
    public EnumFacing rotation = EnumFacing.WEST;

    public static final String CURRENT_ESSENCE_NBT = "currentEssence";
    private final int range = 5;
    private int currentEssence = 0;
    private final int maxEssence = 32;
    public boolean connected = false;
    private TileEssenceProvider myProvider;
    private int distanceBetweenProvider = 0;
    IBlockState myState;
    private final int checkFrequency = 100;
    private int tickCounter = 0;

    private static final int PARTICLE_COUNT = 8;

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        currentEssence = compound.getInteger(CURRENT_ESSENCE_NBT);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger(CURRENT_ESSENCE_NBT, currentEssence);
        return super.writeToNBT(compound);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    private void checkConnection() {
        myState = world.getBlockState(getPos());
        for (int i = 1; i <= range; i++) {
            if (world.getBlockState(getPos().offset(myState.getValue(BlockEssenceFocuser.FACING), i)).getBlock() == ModBlocks.essenceProvider) {
                myProvider = (TileEssenceProvider) world.getTileEntity(getPos().offset(myState.getValue(BlockEssenceFocuser.FACING), i));
                distanceBetweenProvider = i;
                connected = true;
                break;
            }
            if (world.getBlockState(getPos().offset(myState.getValue(BlockEssenceFocuser.FACING), i)).getBlock() != Blocks.AIR) {
                connected = false;
                break;
            }
            connected = false;

        }
    }

    @Override
    public void update() {
        tickCounter++;
        if (tickCounter >= checkFrequency) {
            checkConnection();
            tickCounter = 0;
        }

        if (connected) {
            if (!myProvider.isFull() && currentEssence > 0) {
                myProvider.setEssence(myProvider.getCurrentEssence() + currentEssence);
                currentEssence = 0;
                spawnTransferParticles();
            }
        }
    }

    private void spawnTransferParticles() {
        if (distanceBetweenProvider > 0) {
            BlockPos particleDirection = new BlockPos(0, 0, 0).offset(myState.getValue(BlockEssenceFocuser.FACING));
            double particleSpawnSpacing = (double) distanceBetweenProvider / (double) PARTICLE_COUNT;
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                double x = getPos().getX() + (particleDirection.getX() * (particleSpawnSpacing * i) + 0.5);
                double y = getPos().getY() + (particleDirection.getY() * (particleSpawnSpacing * i) + 0.5);
                double z = getPos().getZ() + (particleDirection.getZ() * (particleSpawnSpacing * i) + 0.5);
                double xVel = particleDirection.getX() * 0.2;
                double yVel = particleDirection.getY() * 0.2;
                double zVel = particleDirection.getZ() * 0.2;
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, xVel, yVel, zVel);
            }
        }
    }

    @Override
    public int getMaxEssencePerTick() {
        return maxEssence;
    }

    @Override
    public boolean getImConsuming() {
        return true;
    }

    @Override
    public int getMaxEssence() {
        return maxEssence;
    }

    @Override
    public int getCurrentEssence() {
        return currentEssence;
    }

    @Override
    public void setEssence(int amount) {
        this.currentEssence = amount;
    }
}
