package com.github.hashtagshell.enchantfood.essence.pipe;

import com.github.hashtagshell.enchantfood.essence.IEssenceConsumer;
import com.github.hashtagshell.enchantfood.essence.IEssenceProvider;

public interface IPipe extends IEssenceConsumer, IEssenceProvider {
    default boolean isBlocked() {
        return false;
    }
}
