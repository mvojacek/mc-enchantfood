package com.github.hashtagshell.enchantfood.init;


import com.github.hashtagshell.enchantfood.EnchantFood;
import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.handler.AsmFallbackHandler;
import com.github.hashtagshell.enchantfood.handler.GuiHandler;
import com.github.hashtagshell.enchantfood.handler.ItemFoodHandler;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import com.github.hashtagshell.enchantfood.network.message.MessageFoodEnchanter;
import com.github.hashtagshell.enchantfood.network.message.MessageFoodEnchanterReq;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;

import static com.github.hashtagshell.enchantfood.init.RegisterMethods.Events.register;

@ObjectHolder(Mod.ID)
public class ModEvents
{
    public static void preInit()
    {
        register(Config.class);
        register(ItemFoodHandler.class);

        NetworkRegistry.INSTANCE.registerGuiHandler(EnchantFood.instance, new GuiHandler());
        NetworkWrapper.network.registerMessage(new MessageFoodEnchanter.Handler(), MessageFoodEnchanter.class, 0, Side.CLIENT);
        NetworkWrapper.network.registerMessage(new MessageFoodEnchanterReq.Handler(), MessageFoodEnchanterReq.class, 1, Side.SERVER);

        AsmFallbackHandler.registerFallBackHandlers();
    }
}
