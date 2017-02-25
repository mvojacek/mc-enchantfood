package com.github.hashtagshell.enchantfood.command;


import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import com.github.hashtagshell.enchantfood.config.Conf;
import com.github.hashtagshell.enchantfood.ench.PropertyPotionEffect;
import com.github.hashtagshell.enchantfood.reference.Ref.Command;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.util.ArrayList;
import java.util.List;

public class CommandAddFoodPotion extends CommandBase
{
    @Override
    public String getName()
    {
        return Command.FOOD_POTION;
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "command.foodPotion.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 2
            || (args[1].equals("remove") && args.length < 3)
            || (args[1].equals("add") && args.length < 5))
            throw new WrongUsageException("command.foodPotion.usage");

        EntityPlayer player = getEntity(server, sender, args[0], EntityPlayer.class);

        ItemStack stack = player.inventory.getCurrentItem();

        if (stack.isEmpty())
            throw new CommandException("command.foodPotion.fail.emptyStack", player.getName());

        PropertyPotionEffect prop = PropertyPotionEffect.fromStack(stack);

        if (prop.isEmpty() && !args[1].equals("add"))
            throw new CommandException("command.foodPotion.fail.noEffects");

        if (args[1].equals("clear"))
        {
            prop.clearEffects().writeToStack(stack);
            notifyCommandListener(sender, this, "command.foodPotion.success.clear");
            return;
        }

        if (!args[1].equals("add") && !args[1].equals("remove"))
            throw new WrongUsageException("commands.foodPotion.wrong.invalidAction", args[1]);

        Potion potion;
        try
        {
            potion = Potion.getPotionById(parseInt(args[2], 1));
        }
        catch (NumberInvalidException var11)
        {
            potion = Potion.getPotionFromResourceLocation(args[2]);
        }
        if (potion == null)
            throw new NumberInvalidException("command.foodPotion.wrong.invalidPotion", args[2]);

        if (args[1].equals("remove"))
        {
            if (prop.hasEffect(potion))
            {
                prop.removeEffect(potion).writeToStack(stack);
                notifyCommandListener(sender, this, "command.foodPotion.success.remove", Log.translate(potion.getName()));
                return;
            }
            throw new CommandException("command.foodPotion.fail.effectNotPresent", Log.translate(potion.getName()));
        }

        boolean hideParticles = args.length > 5 && parseBoolean(args[5]);
        int duration = parseInt(args[3], 0);
        int amplifier = parseInt(args[4], 0, 255);

        PotionEffect effect = new PotionEffect(potion, duration, amplifier, false, !hideParticles);

        if (!Conf.FoodPotions.enforceRestrictions.includes(Conf.Enums.EnforceRestrictions.PREVENT_COMMAND) || PropertyPotionEffect.isEffectEnabled(effect))
        {
            prop.addEffect(effect).writeToStack(stack);
            notifyCommandListener(sender, this, "command.foodPotion.success.add", Log.translate(potion.getName()));
        }
        else {throw new CommandException("command.foodPotion.fail.effectDisabled");}
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender,
                                          String[] args, BlockPos targetPos)
    {
        switch (args.length)
        {
            case 1:
                return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
            case 2:
                return getListOfStringsMatchingLastWord(args, "add", "remove", "clear");
            case 3:
                return getListOfStringsMatchingLastWord(args, Potion.REGISTRY.getKeys());
            case 6:
                return getListOfStringsMatchingLastWord(args, "true", "false");
            default:
                return super.getTabCompletions(server, sender, args, targetPos);
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
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
