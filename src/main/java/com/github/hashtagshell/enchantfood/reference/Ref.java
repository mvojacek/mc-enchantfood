package com.github.hashtagshell.enchantfood.reference;

public class Ref
{
    public static class Mod
    {
        public static final String ID      = "enchantfood";
        public static final String NAME    = "EnchantFood";
        public static final String VERSION = "${version}";
        public static final String DEPS    = "";
    }

    public static class Proxy
    {
        public static final String PROXY_CLIENT_CLASS = "com.github.hashtagshell.enchantfood.proxy.ClientProxy";
        public static final String PROXY_SERVER_CLASS = "com.github.hashtagshell.enchantfood.proxy.ServerProxy";
    }

    public static class Config
    {
        public static final String GUI_FACTORY_CLASS       = "com.github.hashtagshell.enchantfood.client.gui.ModGuiFactory";
        public static final String CONFIG_CATEGORY_GENERAL = "General";
    }

    public static class Network
    {
        public static final String NETWORK_CHANNEL = Mod.ID;

        public static class Packet
        {

        }
    }

    public static class NBT
    {
        public static final String MOD_COMPOUND = "securepm";
    }


    public static class Command
    {
        public static final String FOOD = "food";
    }

    public static class Expression
    {
        public static final char PARAGRAPH = '\u00a7';
    }
}
