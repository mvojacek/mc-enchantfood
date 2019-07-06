package com.github.hashtagshell.enchantfood.network.message;

import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageFoodEnchanter implements IMessage {
    public MessageFoodEnchanter(BlockPos pos, int fuel, int process, boolean working) {
        this.fuel = fuel;
        this.pos = pos;
        this.progress = process;
        this.working = working;
    }

    public MessageFoodEnchanter(MessageFoodEnchanter msg) {
        this(msg.pos, msg.fuel, msg.progress, msg.working);
    }

    public MessageFoodEnchanter(TileFoodEnchanter foodEnchanter) {
        this(foodEnchanter.getPos(), foodEnchanter.fuel, foodEnchanter.progress, foodEnchanter.working);
    }

    public MessageFoodEnchanter() {
    }

    private boolean working;
    private BlockPos pos;
    private int fuel;
    private int progress;

    @Override
    public void fromBytes(ByteBuf buf) {
        working = buf.readBoolean();
        pos = BlockPos.fromLong(buf.readLong());
        fuel = buf.readInt();
        progress = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(working);
        buf.writeLong(pos.toLong());
        buf.writeInt(fuel);
        buf.writeInt(progress);
    }

    public static class Handler implements IMessageHandler<MessageFoodEnchanter, IMessage> {

        @Override
        public IMessage onMessage(MessageFoodEnchanter message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                TileFoodEnchanter foodEnchanter = (TileFoodEnchanter) Minecraft.getMinecraft().world.getTileEntity(message.pos);
                foodEnchanter.working = message.working;
                foodEnchanter.fuel = message.fuel;
                foodEnchanter.progress = message.progress;
            });
            return null;
        }
    }
}
