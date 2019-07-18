package com.github.hashtagshell.enchantfood.reference;

import java.io.File;
import java.io.IOException;

public class Ref
{
    public static class Mod
    {
        public static final String ID         = "enchantfood";
        public static final String NAME       = "EnchantFood";
        public static final String VERSION    = "${version}";
        public static final String MC_VERSION = "1.11";
        public static final String DEPS       = "";
    }

    public static class Blocks {
        public static final String BLANK = "blankstone";
        public static final String ITEM_HOLDER = "itemholder";
        public static final String FOOD_ENCHANTER = "foodenchanter";
        public static final String FOOD_ALTAR = "foodaltar";
        public static final String ESSENCE_PROVIDER = "essenceprovider";
        public static final String ESSENCE_FOCUSER = "essencefocuser";
        public static final String MULTIBLOCK_FOOD_ALTAR = "multiblock_food_altar";
        //Pipes
        public static final String PIPE_ADVANCED = "pipe_advanced";
        public static final String PIPE_BASIC = "pipe_basic";
        //Essence Pumps
        public static final String PUMP_ESSENCE_BASIC = "essencepump_basic";
        //Essence Sources
        public static final String INFINITE_ESSENCE_HOLE = "infinite_essence_hole";
    }

    public static class Gui {
        public static final int FOOD_ENCHANTER_GUI = 0;
    }

    public static class Items {
        public static final String BLANK = "blank";
        public static final String ESSENCE_SHARD = "essenceshard";
        public static final String MAGICAL_BEEF_STICK = "magicalbeefonstick";
        public static final String ESSENCE_STICK = "essencestick";
        public static final String OVERLOADED_ESSENCE_SHARD = "overloadedessenceshard";
        public static final String BASIC_PRESSURE_CHAMBER = "basic_pressure_chamber";
        public static final String METEOR_STAFF = "meteorstaff";
    }

    public static class ToolTips {
        public static final String MAGICAL_BEEF_STICK_TOOLTIP = "Oh shiny!";
    }

    public static class Files
    {
        public static  File MC_LOCATION  = null;
        private static File MOD_CONF_DIR = null;

        @SuppressWarnings("ResultOfMethodCallIgnored")
        public static File MOD_CONF_DIR() throws IOException
        {
            if (MOD_CONF_DIR == null)
            {
                if (MC_LOCATION == null)
                    throw new IOException("Ref.Files.MC_LOCATION has not been initialized yet!");
                else if (!MC_LOCATION.exists() || !MC_LOCATION.isDirectory())
                    throw new IOException(String.format(
                            "The minecraft root, %s either does not exist or is not a directory!",
                            MC_LOCATION.getAbsolutePath()));
                File dConfig = new File(MC_LOCATION, "config");
                if (!dConfig.exists())
                    dConfig.mkdir();
                else if (!dConfig.isDirectory())
                    throw new IOException(String.format(
                            "This instance's minecraft config directory, %s, exists but is not a directory!",
                            dConfig.getAbsolutePath()));
                File dModConfig = new File(dConfig, Mod.ID);
                if (!dModConfig.exists())
                    dModConfig.mkdir();
                else if (!dModConfig.isDirectory())
                    throw new IOException(String.format(
                            "This mod's config directory, %s, exists but is not a directory!",
                            dModConfig.getAbsolutePath()));
                MOD_CONF_DIR = dModConfig;
            }
            return MOD_CONF_DIR;
        }
    }

    public static class Resources {
        public static final String GUI_RESOURCE_FOLDER = "textures/gui/";
        public static final String MODEL_RESOURCE_FOLDER = "models/";
    }

    public static class Asm
    {
        public static final int SORTING_INDEX = 1000000; // Very high because I want my thing to run last
    }

    public static class Proxy
    {
        public static final String PROXY_CLIENT_CLASS = "com.github.hashtagshell.enchantfood.proxy.ClientProxy";
        public static final String PROXY_SERVER_CLASS = "com.github.hashtagshell.enchantfood.proxy.ServerProxy";
    }

    public static class Config
    {
        public static final String GUI_FACTORY_CLASS = "com.github.hashtagshell.enchantfood.client.gui.ModGuiFactory";
    }

    public static class Network
    {
        public static final String NETWORK_CHANNEL = Mod.ID;

        public static class Packet
        {

        }
    }

    public static class Nbt
    {
        public static final String COMP_MOD                              = Mod.ID;
        public static final String LIST_COMP_FOOD_PROPERTY_POTION_EFFECT = "foodPropertyPotionEffect";

        public enum TagType
        {
            END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTE_ARR, STRING, LIST, COMPOUND, INT_ARR;

            public byte id() {return (byte) ordinal();}
        }
    }


    public static class Command
    {
        public static final String FOOD        = "food";
        public static final String FOOD_POTION = "foodpotion";
    }

    public static class Expression
    {
        public static final char PARAGRAPH = '\u00a7';
    }
}
