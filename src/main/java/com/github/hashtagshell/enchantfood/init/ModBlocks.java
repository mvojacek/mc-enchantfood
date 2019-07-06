package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.block.BlockFoodEnchanter;
import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import com.github.hashtagshell.enchantfood.reference.RefBlocks;
import net.minecraft.block.material.Material;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.register;
import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.registerTile;


public class ModBlocks {
    public static BlockGeneric blankStone;
    public static BlockTileGeneric<TileFoodEnchanter> foodEnchanter;
    public static void preInit() {
        blankStone = register(new BlockGeneric(RefBlocks.BLANK, Material.ROCK));
        foodEnchanter = registerTile(new BlockFoodEnchanter(RefBlocks.FOOD_ENCHANTER));
    }
}