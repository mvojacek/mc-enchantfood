package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterExtended extends Teleporter
{
    private final WorldServer world;
    private       BlockPos    pos;


    public TeleporterExtended(WorldServer world, BlockPos pos)
    {
        super(world);
        this.world = world;
        this.pos = pos;
    }

    public static void teleport(EntityPlayer entityPlayer, BlockPosDim pos)
    {
        if (!(entityPlayer instanceof EntityPlayerMP)) return;

        EntityPlayerMP player = ((EntityPlayerMP) entityPlayer);
        MinecraftServer server = player.mcServer;
        WorldServer world = server.worldServerForDimension(pos.getDim());

        server.getPlayerList().transferPlayerToDimension(player, pos.getDim(), new TeleporterExtended(world, pos));
    }

    @Override
    public boolean makePortal(Entity p_85188_1_)
    {
        return true;
    }

    @Override
    public void placeInPortal(Entity entity, float yaw)
    {
        this.world.getBlockState(pos);   //dummy load to maybe gen chunk
        entity.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
    }


}