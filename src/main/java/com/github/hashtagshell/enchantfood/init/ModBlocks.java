package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import net.minecraft.block.material.Material;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.register;


public class ModBlocks {
    public static BlockGeneric blankStone;

    public static void preInit() {
        blankStone = register(new BlockGeneric("blankStone", Material.ROCK));
    }
}