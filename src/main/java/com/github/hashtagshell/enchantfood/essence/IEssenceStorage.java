package com.github.hashtagshell.enchantfood.essence;

public interface IEssenceStorage {
    int getMaxEssence();
    int getCurrentEssence();

    void setEssence(int amount);

    default boolean isFull() {
        return getCurrentEssence() >= getMaxEssence();
    }
}
