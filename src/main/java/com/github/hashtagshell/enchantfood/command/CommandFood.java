package com.github.hashtagshell.enchantfood.command;


import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.FoodStats;
import net.minecraft.util.text.TextComponentString;

import com.github.hashtagshell.enchantfood.reference.Ref.Command;
import com.github.hashtagshell.enchantfood.utility.tuple.Pair;

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
        Pair<Boolean, Integer> foodChange = new Pair<>(false, food.getFoodLevel());
        Pair<Boolean, Float> saturationChange = new Pair<>(false, food.getSaturationLevel());
        switch (args.length)
        {
            case 2:
                food.setFoodSaturationLevel(Float.parseFloat(args[1]));
                saturationChange.setKey(true);
            case 1:
                food.setFoodLevel(Integer.parseInt(args[0]));
                foodChange.setKey(true);
            case 0:
                if (foodChange.getKey() || saturationChange.getKey())
                    sender.sendMessage(new TextComponentString(
                            "Food: " + foodChange.getValue() + " | Saturation: " + saturationChange.getValue()
                            + "  ==>  Food: " + food.getFoodLevel() + " | Saturation: " + food.getSaturationLevel()
                    ));
                else
                    sender.sendMessage(new TextComponentString("Food: " + food.getFoodLevel() + " | Saturation: " + food.getSaturationLevel()));
        }
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public List<String> getAliases()
    {
        return new ArrayList<>();
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
    {
        return false;
    }
}
