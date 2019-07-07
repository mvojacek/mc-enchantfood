package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import net.minecraft.util.ITickable;

public class TileEssenceFocuser extends TileGeneric implements ITickable {
    private int rotation = 0;

    @Override
    public void update() {

    }

    public void rotateNext() {
        rotation++;
        int rightRotation = rotation % 6;
        switch (rightRotation) {
            case 0: {
            }
            case 1: {

            }
            case 2: {

            }
            case 3: {

            }
            case 4: {

            }
            case 5: {

            }
            default: {

            }
        }
    }
}
