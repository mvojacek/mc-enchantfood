package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.block.*;
import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.*;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.material.Material;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.register;
import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.registerTile;


public class ModBlocks {
    public static BlockGeneric blankStone;
    public static BlockTileGeneric<TileFoodEnchanter> foodEnchanter;
    public static BlockTileGeneric<TileEssenceProvider> essenceProvider;
    public static BlockTileGeneric<TileEssenceFocuser> essenceFocuser;
    public static BlockTileGeneric<TileItemHolder> itemTable;
    public static BlockTileGeneric<TileFoodAltar> foodAltar;
    public static BlockMultiblockFoodAltar multiblockFoodAltar;

    public static void preInit() {
        blankStone = register(new BlockGeneric(Ref.Blocks.BLANK, Material.ROCK));
        foodEnchanter = registerTile(new BlockFoodEnchanter(Ref.Blocks.FOOD_ENCHANTER));
        essenceProvider = registerTile(new BlockEssenceProvider(Ref.Blocks.ESSENCE_PROVIDER));
        essenceFocuser = registerTile(new BlockEssenceFocuser(Ref.Blocks.ESSENCE_FOCUSER));
        itemTable = registerTile(new BlockItemHolder(Ref.Blocks.ITEM_HOLDER));
        foodAltar = registerTile(new BlockFoodAltar(Ref.Blocks.FOOD_ALTAR));
        multiblockFoodAltar = registerTile(new BlockMultiblockFoodAltar(Ref.Blocks.MULTIBLOCK_FOOD_ALTAR));
    }
}