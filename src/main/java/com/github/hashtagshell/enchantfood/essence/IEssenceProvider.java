package com.github.hashtagshell.enchantfood.essence;

public interface IEssenceProvider extends IEssenceStorage {
    int getMaxOuputEssence();

    boolean getIsGenerating();
}
