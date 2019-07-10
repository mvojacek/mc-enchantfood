package com.github.hashtagshell.enchantfood.block;

import net.minecraft.util.IStringSerializable;

public enum AltarMultiblock implements IStringSerializable {
    CORNER("corner", 0),
    MAGMA("magma", 1),
    GOLD("gold", 2);

    String name;
    int index;

    AltarMultiblock(String name, int index) {
        this.name = name;
        this.index = index;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int getIndex() {
        return this.index;
    }
}
