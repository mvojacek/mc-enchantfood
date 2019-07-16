package com.github.hashtagshell.enchantfood.essence;

public interface IEssenceProvider extends IEssenceStorage {
    int getMaxOutputEssence();

    default int takeEssence(int value) {
        int output = Math.min(value, Math.min(getMaxOutputEssence(), getMaxEssence()));
        setEssence(getCurrentEssence() - output);

        return output;
    }
}
