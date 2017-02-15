package com.github.hashtagshell.enchantfood.client.gui;


import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import com.github.hashtagshell.enchantfood.config.Config;
import com.github.hashtagshell.enchantfood.reference.Ref.Mod;

import java.util.ArrayList;
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
        List<IConfigElement> list = null;
        for (String cat : Config.config.getCategoryNames())
        {
            List<IConfigElement> categoryElements = new ConfigElement(Config.config.getCategory(cat)).getChildElements();
            if (list == null)
                list = categoryElements;
            else
                list.addAll(categoryElements);
        }
        return list == null ? new ArrayList<>() : list;
    }
}
