package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.nbt.NBTBase;

public interface INBTSerializable<D, S extends NBTBase>
{
    S serialize();

    D deserialize(S nbt);
}
