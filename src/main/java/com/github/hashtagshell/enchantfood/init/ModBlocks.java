package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.block.*;
import com.github.hashtagshell.enchantfood.block.lib.BlockGeneric;
import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.testing.BlockInfiniteEssence;
import com.github.hashtagshell.enchantfood.block.testing.TileInfiniteEssence;
import com.github.hashtagshell.enchantfood.block.tile.*;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.material.Material;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.register;
import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Blocks.registerTile;


public class ModBlocks {
    public static BlockGeneric blankStone;
    public static BlockTileGeneric<TileInfiniteEssence> infiniteEssenceHole;
    public static BlockTileGeneric<TileFoodEnchanter> foodEnchanter;
    public static BlockTileGeneric<TileEssenceProvider> essenceProvider;
    public static BlockTileGeneric<TileEssenceFocuser> essenceFocuser;
    public static BlockTileGeneric<TileItemHolder> itemTable;
    public static BlockTileGeneric<TileFoodAltar> foodAltar;
    public static BlockTileGeneric<TileEssencePump> basic_pump;
    public static BlockMultiblockFoodAltar multiblockFoodAltar;
    public static BlockPipeGeneric advanced_pipe;
    public static BlockPipeGeneric basic_pipe;

    public static void preInit() {
        blankStone = register(new BlockGeneric(Ref.Blocks.BLANK, Material.ROCK));
        foodEnchanter = registerTile(new BlockFoodEnchanter(Ref.Blocks.FOOD_ENCHANTER));
        essenceProvider = registerTile(new BlockEssenceProvider(Ref.Blocks.ESSENCE_PROVIDER));
        essenceFocuser = registerTile(new BlockEssenceFocuser(Ref.Blocks.ESSENCE_FOCUSER));
        itemTable = registerTile(new BlockItemHolder(Ref.Blocks.ITEM_HOLDER));
        foodAltar = registerTile(new BlockFoodAltar(Ref.Blocks.FOOD_ALTAR));
        multiblockFoodAltar = registerTile(new BlockMultiblockFoodAltar(Ref.Blocks.MULTIBLOCK_FOOD_ALTAR));
        //Pipes
        basic_pipe = register(new BlockPipeGeneric(Ref.Blocks.PIPE_BASIC, Material.WOOD, 1, 1.5D));
        advanced_pipe = register(new BlockPipeGeneric(Ref.Blocks.PIPE_ADVANCED, Material.IRON, 2, 2.0D));
        //Pumps
        basic_pump = registerTile(new BlockEssencePumpGeneric(Ref.Blocks.PUMP_ESSENCE_BASIC, Material.WOOD, 1));
        //Essence Sources
        infiniteEssenceHole = registerTile(new BlockInfiniteEssence(Ref.Blocks.INFINITE_ESSENCE_HOLE));
    }
}