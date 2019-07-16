package com.github.hashtagshell.enchantfood.essence;

public interface ITierable {
    default int getTier() {
        return 1;
    }
}
