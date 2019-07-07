package com.github.hashtagshell.enchantfood.init;


import net.minecraft.block.Block;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.client.render.ItemRenderRegister;
import com.github.hashtagshell.enchantfood.reference.Ref;

public class RegisterMethods
{
    public static class Commands
    {
        public static <T extends ICommand> T registerServer(MinecraftServer server, T command, boolean enable)
        {
            if (enable) registerServer(server, command);
            return command;
        }

        public static <T extends ICommand> T registerServer(MinecraftServer server, T command)
        {
            ((CommandHandler) server.getCommandManager()).registerCommand(command);
            return command;
        }

        public static <T extends ICommand> T registerClient(T command, boolean enable)
        {
            if (enable) registerClient(command);
            return command;
        }

        public static <T extends ICommand> T registerClient(T command)
        {
            ClientCommandHandler.instance.registerCommand(command);
            return command;
        }

        public static <T extends ICommand> T register(MinecraftServer server, T command, boolean enable)
        {
            if (enable) register(server, command);
            return command;
        }

        public static <T extends ICommand> T register(MinecraftServer server, T command)
        {
            registerServer(server, command);
            registerClient(command);
            return command;
        }
    }

    public static class Events
    {
        public static void register(Object o)
        {
            MinecraftForge.EVENT_BUS.register(o);
        }

        public static void lok(Object o)
        {
            MinecraftForge.ORE_GEN_BUS.register(o);
        }

        public static void registerTerrain(Object o)
        {
            MinecraftForge.TERRAIN_GEN_BUS.register(o);
        }
    }

    public static class Items
    {
        public static <T extends Item> T register(T item, boolean enable)
        {
            if (enable) return register(item);
            return item;
        }

        public static <T extends Item> T register(T item)
        {
            ItemRenderRegister.schedule(item);
            return GameRegistry.register(item);
        }
    }

    public static class Blocks {
        public static <T extends Block> T register(T block, boolean enable) {
            return enable ? register(block) : block;
        }

        public static <T extends Block> T register(T block) {
            ItemBlock item = new ItemBlock(block);
            item.setRegistryName(block.getRegistryName());
            return register(block, item);
        }

        public static <T extends Block, I extends ItemBlock> T register(T block, I item, boolean enable) {
            if (enable) register(block, item);
            return block;
        }

        public static <T extends Block, I extends ItemBlock> T register(T block, I item) {
            if (item != null) Items.register(item);
            return RegistryObjects.register(block);
        }

        public static <T extends BlockTileGeneric, I extends ItemBlock> T registerTile(T block, I item, boolean enable) {
            return enable ? registerTile(block, item) : block;
        }

        @SuppressWarnings("unchecked")
        // - Class<T> from BlockTileGeneric<T extends TileEntity> -> Class<T extends TileEntity>
        public static <T extends BlockTileGeneric, I extends ItemBlock> T registerTile(T block, I item) {
            Tiles.register(block.tileClass(), block.tileId());
            return register(block, item);
        }

        public static <T extends BlockTileGeneric> T registerTile(T block, boolean enable) {
            return enable ? register(block) : block;
        }

        @SuppressWarnings("unchecked")
        // - Class<T> from BlockTileGeneric<T extends TileEntity> -> Class<T extends TileEntity>
        public static <T extends BlockTileGeneric> T registerTile(T block) {
            Tiles.register(block.tileClass(), block.tileId());
            return register(block);
        }
    }

    public static class Tiles {
        public static void register(Class<? extends TileEntity> tile, String id) {
            GameRegistry.registerTileEntity(tile, Ref.Mod.ID + "_" + id);
        }
    }

    public static class Recipe
    {
        public static void craft(IRecipe recipe)
        {
            GameRegistry.addRecipe(recipe);
        }
    }

    public static class RegistryObjects
    {
        public static <T extends IForgeRegistryEntry<?>> T register(T t, boolean enable)
        {
            if (enable) return register(t);
            return t;
        }

        public static <T extends IForgeRegistryEntry<?>> T register(T t)
        {
            return GameRegistry.register(t);
        }
    }
}
