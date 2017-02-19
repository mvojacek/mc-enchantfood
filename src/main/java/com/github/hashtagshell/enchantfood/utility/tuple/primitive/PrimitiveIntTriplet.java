package com.github.hashtagshell.enchantfood.utility.tuple.primitive;

import java.awt.*;

public class PrimitiveIntTriplet
{
    public int x;
    public int y;
    public int z;

    public PrimitiveIntTriplet(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PrimitiveIntTriplet(Color c)
    {
        this(c.getRed(), c.getGreen(), c.getBlue());
    }

    public int distSq(PrimitiveIntTriplet v)
    {
        int dx = x - v.x;
        int dy = y - v.y;
        int dz = z - v.z;

        return dx * dx + dy * dy + dz * dz;
    }
}
