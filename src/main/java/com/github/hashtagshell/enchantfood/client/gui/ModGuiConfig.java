package com.github.hashtagshell.enchantfood.client.gui;


import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;
import com.github.hashtagshell.enchantfood.utility.Array;

import java.util.List;

public class ModGuiConfig extends GuiConfig
{
    @SuppressWarnings("unchecked")
    public ModGuiConfig(GuiScreen guiScreen)
    {
        //noinspection unchecked
        super(guiScreen,
              getConfigElements(),
              Mod.ID,
              false,
              false,
              GuiConfig.getAbridgedConfigPath(Config.config.toString()));
    }

    public static List<IConfigElement> getConfigElements()
    {
        return Array.processToList(Config.config.getCategoryNames(),
                                   c -> new DummyCategoryElement(c, "config_category." + c,
                                                                 new ConfigElement(Config.config.getCategory(c))
                                                                         .getChildElements()));

    }
}
