package com.github.hashtagshell.enchantfood.essence;

public interface IEssenceStorage {
    int getMaxEssence();
    int getCurrentEssence();

    boolean canInsertEssence(int amount);

    boolean isFull();
}
