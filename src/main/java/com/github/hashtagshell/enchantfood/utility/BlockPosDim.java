package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class BlockPosDim extends BlockPos
{
    private final int dim;

    public BlockPosDim(int x, int y, int z, int dim)
    {
        super(x, y, z);
        this.dim = dim;
    }

    public BlockPosDim(double x, double y, double z, int dim)
    {
        super(x, y, z);
        this.dim = dim;
    }

    public BlockPosDim(Entity source)
    {
        super(source);
        dim = source.dimension;
    }

    public BlockPosDim(Vec3d vec, int dim)
    {
        super(vec);
        this.dim = dim;
    }

    public BlockPosDim(Vec3i source, int dim)
    {
        super(source);
        this.dim = dim;
    }

    public int getDim()
    {
        return dim;
    }
}
