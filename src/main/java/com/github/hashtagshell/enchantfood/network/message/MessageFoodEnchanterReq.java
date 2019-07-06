package com.github.hashtagshell.enchantfood.network.message;

import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageFoodEnchanterReq implements IMessage {
    public MessageFoodEnchanterReq(BlockPos pos, int dim) {
        this.pos = pos;
        this.dim = dim;
    }

    public MessageFoodEnchanterReq(MessageFoodEnchanterReq msg) {
        this(msg.pos, msg.dim);
    }

    public MessageFoodEnchanterReq(TileFoodEnchanter tileFoodEnchanter) {
        this(tileFoodEnchanter.getPos(), tileFoodEnchanter.getWorld().provider.getDimension());
    }

    public MessageFoodEnchanterReq() {
    }

    private BlockPos pos;
    private int dim;

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    public static class Handler implements IMessageHandler<MessageFoodEnchanterReq, IMessage> {

        @Override
        public IMessage onMessage(MessageFoodEnchanterReq message, MessageContext ctx) {

            World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(message.dim);
            TileFoodEnchanter foodEnchanter = (TileFoodEnchanter) world.getTileEntity(message.pos);
            if (foodEnchanter != null) {
                return new MessageFoodEnchanter(foodEnchanter);
            } else {
                return null;
            }
        }
    }
}
