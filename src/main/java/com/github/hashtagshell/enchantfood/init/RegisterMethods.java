package com.github.hashtagshell.enchantfood.init;


import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

public class RegisterMethods
{
    public static class Commands
    {
        protected static void registerServer(MinecraftServer server, ICommand command)
        {
            ((CommandHandler) server.getCommandManager()).registerCommand(command);
        }

        protected static void registerClient(ICommand command)
        {
            ClientCommandHandler.instance.registerCommand(command);
        }

        protected static void register(MinecraftServer server, ICommand command)
        {
            registerServer(server, command);
            registerClient(command);
        }
    }

    public static class Events
    {
        protected static void register(Object o)
        {
            MinecraftForge.EVENT_BUS.register(o);
        }

        protected static void registerOregen(Object o)
        {
            MinecraftForge.ORE_GEN_BUS.register(o);
        }

        protected static void registerTerrain(Object o)
        {
            MinecraftForge.TERRAIN_GEN_BUS.register(o);
        }
    }
}
