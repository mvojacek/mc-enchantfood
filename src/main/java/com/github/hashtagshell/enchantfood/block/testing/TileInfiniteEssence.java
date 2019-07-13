package com.github.hashtagshell.enchantfood.block.testing;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.essence.IEssenceProvider;

public class TileInfiniteEssence extends TileGeneric implements IEssenceProvider {
    public static final int INFINITE_POWER = Integer.MAX_VALUE;

    @Override
    public int getMaxOuputEssence() {
        return INFINITE_POWER;
    }

    @Override
    public boolean canTakeEssence(int amount) {
        return true;
    }

    @Override
    public void takeEssence(int value) {
    }

    @Override
    public boolean isGenerating() {
        return true;
    }

    @Override
    public int getMaxEssence() {
        return INFINITE_POWER;
    }

    @Override
    public int getCurrentEssence() {
        return INFINITE_POWER;
    }

    @Override
    public boolean canInsertEssence(int amount) {
        return false;
    }

    @Override
    public boolean isFull() {
        return true;
    }
}
