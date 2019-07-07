package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class TileEssenceProvider extends TileGeneric implements ITickable {

    private boolean charging = false;
    private Random random = new Random();

    public boolean enabled = false;

    @Override
    public void update() {
        if (enabled) {
            if (world.getTileEntity(getPos().add(0, 1, 0)) != null && world.getTileEntity(getPos().add(0, 1, 0)) instanceof TileFoodEnchanter) {
                TileFoodEnchanter foodEnchanter = (TileFoodEnchanter) world.getTileEntity(getPos().add(0, 1, 0));
                if (foodEnchanter.fuel < foodEnchanter.fuelMax) {
                    foodEnchanter.fuel++;
                    if (!charging) {
                        charging = true;
                    }
                    chargingParticle();
                } else if (charging) {
                    charging = false;
                }
            }
        }
    }

    private void chargingParticle() {
        BlockPos myPos = getPos();
        world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, myPos.getX() + 0.6, myPos.getY() + 0.6 + random.nextDouble() * 0.4, myPos.getZ() + 0.6, 0.0, 0.5, 0.0);
    }
}
