package com.github.hashtagshell.enchantfood.init;


import com.github.hashtagshell.enchantfood.EnchantFood;
import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.handler.AsmFallbackHandler;
import com.github.hashtagshell.enchantfood.handler.GuiHandler;
import com.github.hashtagshell.enchantfood.handler.ItemFoodHandler;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Events.register;

@ObjectHolder(Mod.ID)
public class ModEvents
{
    public static void preInit()
    {
        register(Config.class);
        register(ItemFoodHandler.class);

        NetworkRegistry.INSTANCE.registerGuiHandler(EnchantFood.instance, new GuiHandler());

        AsmFallbackHandler.registerFallBackHandlers();
    }
}
