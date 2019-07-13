package com.github.hashtagshell.enchantfood.essence.pipe;

public interface IPipe {
    default boolean isBlocked() {
        return false;
    }

    default int getTier() {
        return 1;
    }
}
