package com.github.hashtagshell.enchantfood.essence;

public interface IEssenceConsumer extends IEssenceStorage {
    int getMaxEssencePerTick();

    boolean getImConsuming();
}
