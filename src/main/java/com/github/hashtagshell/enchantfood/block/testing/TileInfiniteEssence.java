package com.github.hashtagshell.enchantfood.block.testing;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceProvider;

public class TileInfiniteEssence extends TileGeneric implements IEssenceProvider {
    public static final int INFINITE_POWER = Integer.MAX_VALUE;

    @Override
    public int getMaxEssence() {
        return INFINITE_POWER;
    }

    @Override
    public int getCurrentEssence() {
        return INFINITE_POWER;
    }

    @Override
    public void setEssence(int amount) {
    }

    @Override
    public int getMaxOutputEssence() {
        return INFINITE_POWER;
    }
}
