package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.block.BlockFoodEnchanter;
import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import com.github.hashtagshell.enchantfood.reference.RefBlocks;
import net.minecraft.block.material.Material;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.register;


public class ModBlocks {
    public static BlockGeneric blankStone;
    public static BlockFoodEnchanter foodEnchanter;

    public static void preInit() {
        blankStone = register(new BlockGeneric(RefBlocks.BLANK, Material.ROCK));
        foodEnchanter = register(new BlockFoodEnchanter(RefBlocks.FOOD_ENCHANTER));
    }
}