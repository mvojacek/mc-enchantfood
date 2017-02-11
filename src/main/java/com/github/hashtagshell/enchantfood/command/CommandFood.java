package com.github.hashtagshell.enchantfood.command;


import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextComponentString;

import com.github.hashtagshell.enchantfood.reference.Ref.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandFood extends CommandBase
{

    @Override
    public String getName()
    {
        return Command.FOOD;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
    {
        return false;
    }

    @Override
    public List<String> getAliases()
    {
        return new ArrayList<>();
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "/food [<hunger> <saturation>]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args)
    {
        if (!(sender instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) sender;
        FoodStats food = player.getFoodStats();
        switch (args.length)
        {
            case 0:
                sender.sendMessage(new TextComponentString("Food: " + food.getFoodLevel() + " | Saturation: " + food.getSaturationLevel()));
                break;
            case 2:
                food.setFoodSaturationLevel(Float.parseFloat(args[1]));
            case 1:
                food.setFoodLevel(Integer.parseInt(args[0]));
        }
    }
}
