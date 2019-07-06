package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.util.math.MathHelper;

public enum BlockSide
{
    NONE(-2), RAYTRACE_FULL_LENGTH(-1), BOTTOM(0), TOP(1), EAST(2), WEST(3), NORTH(4), SOUTH(5);

    final int sideID;

    BlockSide(int sideID)
    {
        this.sideID = sideID;
    }

    public static BlockSide fromInteger(int integer)
    {
        return BlockSide.values()[MathHelper.clamp(integer, 0, BlockSide.values().length - 1)];
    }

    public boolean isEqual(int side)
    {
        return this.sideID == side;
    }

    public int getSideID()
    {
        return this.sideID;
    }
}
