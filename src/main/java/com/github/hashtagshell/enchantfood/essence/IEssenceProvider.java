package com.github.hashtagshell.enchantfood.essence;

public interface IEssenceProvider extends IEssenceStorage {
    int getMaxOuputEssence();

    boolean canTakeEssence(int amount);

    void takeEssence(int value);

    boolean isGenerating();
}
