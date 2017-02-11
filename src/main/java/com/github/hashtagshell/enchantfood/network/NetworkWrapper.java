package com.github.hashtagshell.enchantfood.network;


import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import com.github.hashtagshell.enchantfood.reference.Ref.Network;

public class NetworkWrapper
{
    public static SimpleNetworkWrapper network;
    public static int id = 0;

    public static void preInit()
    {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Network.NETWORK_CHANNEL);
//      registerClient(SecondMessage.Handler.class, SecondMessage.class);
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void register(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType, Side side)
    {
        network.registerMessage(messageHandler, requestMessageType, id, side);
        id++;
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerServer(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType)
    {
        register(messageHandler, requestMessageType, Side.SERVER);
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerClient(Class<? extends IMessageHandler<REQ, REPLY>> messageHandler, Class<REQ> requestMessageType)
    {
        register(messageHandler, requestMessageType, Side.CLIENT);
    }

}

