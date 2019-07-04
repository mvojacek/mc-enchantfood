package com.github.hashtagshell.enchantfood.block.lib;

import com.github.hashtagshell.enchantfood.init.ModCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockGeneric extends Block {
    public BlockGeneric(String name, Material mat) {
        super(mat);
        setCreativeTab(ModCreativeTabs.main);
        setRegistryName(name);
        //noinspection ConstantConditions - registry name is 100% not null after setRegistryName()
        setUnlocalizedName(getRegistryName().toString());
    }
}
