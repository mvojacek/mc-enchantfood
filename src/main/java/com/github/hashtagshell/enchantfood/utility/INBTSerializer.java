package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.nbt.NBTBase;
import net.minecraftforge.common.util.INBTSerializable;

public interface INBTSerializer<D, S extends NBTBase> extends INBTSerializable<S>
{
    S serialize();

    D deserialize(S nbt);

    @Override
    @Deprecated
    default S serializeNBT()
    {
        return serialize();
    }

    @Override
    @Deprecated
    default void deserializeNBT(S nbt)
    {
        deserialize(nbt);
    }


}
