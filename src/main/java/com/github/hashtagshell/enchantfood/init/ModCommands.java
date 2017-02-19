package com.github.hashtagshell.enchantfood.init;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import com.github.hashtagshell.enchantfood.command.CommandAddFoodPotion;
import com.github.hashtagshell.enchantfood.command.CommandFood;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Commands.registerServer;

@ObjectHolder(Mod.ID)
public class ModCommands
{
    public static void init(MinecraftServer server)
    {
        registerServer(server, new CommandFood());
        registerServer(server, new CommandAddFoodPotion());
    }
}
