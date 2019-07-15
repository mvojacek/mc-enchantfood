package com.github.hashtagshell.enchantfood.essence;

import net.minecraft.util.EnumFacing;

public interface IEssencePump {
    default int getTier() {
        return 1;
    }

    boolean canConnectFromSide(EnumFacing side);
    //Throughput and stuff
}
