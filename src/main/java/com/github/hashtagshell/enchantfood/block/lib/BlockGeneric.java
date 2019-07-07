package com.github.hashtagshell.enchantfood.block.lib;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.github.hashtagshell.enchantfood.init.ModCreativeTabs;
import com.github.hashtagshell.enchantfood.reference.Ref;

public class BlockGeneric extends Block {
    public BlockGeneric(String name, Material mat) {
        super(mat);
        setCreativeTab(ModCreativeTabs.main);
        setRegistryName(name);
        setUnlocalizedName(Ref.Mod.ID + "." + getRegistryName().getResourcePath());
    }
}
