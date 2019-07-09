package com.github.hashtagshell.enchantfood.network;


import com.github.hashtagshell.enchantfood.reference.Ref.Network;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

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


    //Credits:: Vazkii : https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/api/internal/VanillaPacketDispatcher.java
    public static void dispatchTEToNearbyPlayers(TileEntity tile) {
        SPacketUpdateTileEntity packet = tile.getUpdatePacket();

        if (packet != null && tile.getWorld() instanceof WorldServer) {
            PlayerChunkMapEntry chunk = ((WorldServer) tile.getWorld()).getPlayerChunkMap().getEntry(tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4);
            if (chunk != null) {
                chunk.sendPacket(packet);
            }
        }
    }
}

