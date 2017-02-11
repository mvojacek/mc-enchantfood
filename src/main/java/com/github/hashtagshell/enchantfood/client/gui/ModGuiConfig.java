package com.github.hashtagshell.enchantfood.client.gui;


import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;

public class ModGuiConfig extends GuiConfig
{
    @SuppressWarnings("unchecked")
    public ModGuiConfig(GuiScreen guiScreen)
    {
        //noinspection unchecked
        super(guiScreen,
              new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
              Mod.ID,
              false,
              false,
              GuiConfig.getAbridgedConfigPath(Config.config.toString()));
    }
}
