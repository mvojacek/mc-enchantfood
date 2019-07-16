package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceStorage;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TileEssenceProvider extends TileGeneric implements ITickable, IEssenceStorage {
    private Random random = new Random();

    private final int maxEssence = 32;
    private int currentEssence = 0;

    @Override
    public void update() {
        if (world.getTileEntity(getPos().up()) != null && world.getTileEntity(getPos().up()) instanceof TileFoodAltar) {
            TileFoodAltar foodAltar = (TileFoodAltar) world.getTileEntity(getPos().up());
            if (foodAltar.working && foodAltar.getImConsuming() && !foodAltar.isFull() && currentEssence > 0) {
                int essenceAmount = Math.min(foodAltar.getMaxEssencePerTick(), currentEssence);
                foodAltar.setEssence(foodAltar.getCurrentEssence() + essenceAmount);
                currentEssence -= essenceAmount;
                chargingParticle();
            }
        }
    }

    private void chargingParticle() {
        BlockPos myPos = getPos();
        world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, myPos.getX() + 0.6, myPos.getY() + 0.6 + random.nextDouble() * 0.4, myPos.getZ() + 0.6, 0.0, 0.5, 0.0);
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
