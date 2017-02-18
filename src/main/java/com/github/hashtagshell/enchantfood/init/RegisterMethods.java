package com.github.hashtagshell.enchantfood.init;


import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import com.github.hashtagshell.enchantfood.client.render.ItemRenderRegister;

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

        public static void registerOregen(Object o)
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
