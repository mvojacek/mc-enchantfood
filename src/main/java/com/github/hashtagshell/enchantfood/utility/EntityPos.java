package com.github.hashtagshell.enchantfood.utility;


import com.github.hashtagshell.enchantfood.utility.tuple.Quadruplet;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class EntityPos extends Quadruplet<Double, Double, Double, Integer>
        implements INBTSerializer<EntityPos, NBTTagCompound>
{
    public EntityPos()
    {
        super(0D, 0D, 0D, 0);
    }

    public EntityPos(double x, double y, double z, int dimension)
    {
        super(x, y, z, dimension);
    }

    public double getX()
    {
        return getKey();
    }

    public EntityPos setX(double x)
    {
        setKey(x);
        return this;
    }

    public double getY()
    {
        return getNode();
    }

    public EntityPos setY(double y)
    {
        setNode(y);
        return this;
    }

    public double getZ()
    {
        return getQuark();
    }

    public EntityPos setZ(double z)
    {
        setQuark(z);
        return this;
    }

    public int getDim()
    {
        return getValue();
    }

    public EntityPos setDim(int dim)
    {
        setValue(dim);
        return this;
    }

    @Override
    public NBTTagCompound serialize()
    {
        NBTTagCompound out = new NBTTagCompound();
        out.setDouble("x", getX());
        out.setDouble("y", getY());
        out.setDouble("z", getZ());
        out.setInteger("dim", getDim());
        return out;
    }

    @Override
    public EntityPos deserialize(NBTTagCompound nbt)
    {
        setX(nbt.getDouble("x"));
        setY(nbt.getDouble("y"));
        setZ(nbt.getDouble("z"));
        setDim(nbt.getInteger("dim"));
        return this;
    }

    public double distanceSquared(EntityPos pos)
    {
        double x = pos.getX() - getX();
        double y = pos.getY() - getY();
        double z = pos.getZ() - getZ();
        x *= x;
        y *= y;
        z *= z;
        double distanceSquared = x + y + z;
        return pos.getDim() == getDim() ? distanceSquared : -distanceSquared;
    }

    public double distance(EntityPos pos)
    {
        double distanceSquared = distanceSquared(pos);
        double sgn = Math.signum(distanceSquared);
        return Math.copySign(Math.sqrt(Math.abs(distanceSquared)), sgn);
    }

    public boolean inRangeSquared(EntityPos pos, double rangeSquared)
    {
        double distanceSquared = distanceSquared(pos);
        return distanceSquared >= 0 && distanceSquared < rangeSquared;
    }

    public boolean inRange(EntityPos pos, double range)
    {
        return inRangeSquared(pos, range * range);
    }

    public BlockPos toBlockPos()
    {
        return new BlockPos((int) getX(), (int) getY(), (int) getZ());
    }

    public EntityPos fromEntity(Entity entity)
    {
        setX(entity.posX);
        setY(entity.posY);
        setZ(entity.posZ);
        setDim(entity.dimension);
        return this;
    }

    public EntityPos add(EntityPos pos)
    {
        setX(getX() + pos.getX());
        setY(getY() + pos.getY());
        setZ(getZ() + pos.getZ());
        return this;
    }
}