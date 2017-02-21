package com.github.hashtagshell.enchantfood.init;


import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import com.github.hashtagshell.enchantfood.asm.AsmFallbackHandler;
import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.handler.ItemFoodHandler;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Events.register;

@ObjectHolder(Mod.ID)
public class ModEvents
{
    public static void preInit()
    {
        register(Config.class);
        register(ItemFoodHandler.class);

        AsmFallbackHandler.registerFallBackHandlers();
    }
}
