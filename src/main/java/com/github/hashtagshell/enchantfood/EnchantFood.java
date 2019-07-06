package com.github.hashtagshell.enchantfood;

import com.github.hashtagshell.enchantfood.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

import static com.github.hashtagshell.enchantfood.reference.Ref.Config.GUI_FACTORY_CLASS;
import static com.github.hashtagshell.enchantfood.reference.Ref.Mod.*;
import static com.github.hashtagshell.enchantfood.reference.Ref.Proxy.PROXY_CLIENT_CLASS;
import static com.github.hashtagshell.enchantfood.reference.Ref.Proxy.PROXY_SERVER_CLASS;

@Mod(modid = ID, name = NAME, version = VERSION, guiFactory = GUI_FACTORY_CLASS, dependencies = DEPS)
public class EnchantFood
{
    @Instance
    public static EnchantFood instance;

    @SidedProxy(clientSide = PROXY_CLIENT_CLASS, serverSide = PROXY_SERVER_CLASS, modId = ID)
    public static IProxy proxy;

    @EventHandler
    public void construct(FMLConstructionEvent e) /*        */ { proxy.construct(e); /*      */ }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) /*     */ { proxy.preInit(e); /*        */ }

    @EventHandler
    public void init(FMLInitializationEvent e) /*           */ { proxy.init(e); /*           */ }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) /*   */ { proxy.postInit(e); /*       */ }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent e) /* */ { proxy.serverStarting(e); /* */ }
}
