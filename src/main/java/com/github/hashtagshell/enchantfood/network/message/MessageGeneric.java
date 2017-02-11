package com.github.hashtagshell.enchantfood.network.message;
/*
  This class was created by <Vazkii>. It's distributed as
  part of the Psi Mod. Get the Source Code in github:
  https://github.com/Vazkii/Psi

  Psi is Open Source and distributed under the
  CC-BY-NC-SA 3.0 License: https://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB

  File Created @ [11/01/2016, 22:00:30 (GMT)]
 */

/*
  Slightly modified by HashtagShell to match stable_29 mappings
 */


import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.github.hashtagshell.enchantfood.utility.EntityPos;
import com.github.hashtagshell.enchantfood.utility.NBT;
import com.github.hashtagshell.enchantfood.utility.tuple.Pair;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import static com.github.hashtagshell.enchantfood.network.message.MessageGeneric.SerializationHandlers.acceptField;

public abstract class MessageGeneric<REQ extends MessageGeneric> implements Serializable, IMessage, IMessageHandler<REQ, IMessage>
{
    private SerializationHandlers sH = new SerializationHandlers(this);

    public abstract IMessage handleMessage(MessageContext context);

    @Override
    public final IMessage onMessage(REQ message, MessageContext context)
    {
        return message.handleMessage(context);
    }


    @Override
    public final void fromBytes(ByteBuf buf)
    {
        try
        {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            Class<?> clazz = getClass();
            for (Field f : clazz.getDeclaredFields())
            {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    sH.readField(f, type, packetBuffer);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error at reading packet " + this, e);
        }
    }

    @Override
    public final void toBytes(ByteBuf buf)
    {
        try
        {
            PacketBuffer packetBuffer = new PacketBuffer(buf);
            Class<?> clazz = getClass();
            for (Field f : clazz.getDeclaredFields())
            {
                Class<?> type = f.getType();
                if (acceptField(f, type))
                    sH.writeField(f, type, packetBuffer);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error at writing packet " + this, e);
        }
    }

    public static class SerializationHandlers
    {
        public static final HashMap<Class, Pair<Reader, Writer>> handlers = new HashMap<>();

        static
        {
            map(byte.class, PacketBuffer::readByte, (buf, data) -> buf.writeByte((int) data));
            map(short.class, PacketBuffer::readShort, (buf, data) -> buf.writeShort((int) data));
            map(int.class, PacketBuffer::readVarInt, PacketBuffer::writeVarInt);
            map(long.class, PacketBuffer::readLong, PacketBuffer::writeLong);
            map(float.class, PacketBuffer::readFloat, PacketBuffer::writeFloat);
            map(double.class, PacketBuffer::readDouble, PacketBuffer::writeDouble);
            map(boolean.class, PacketBuffer::readBoolean, PacketBuffer::writeBoolean);
            map(char.class, PacketBuffer::readChar, (buf, data) -> buf.writeChar((int) data));
            map(String.class, (buf) -> buf.readString(32767), PacketBuffer::writeString);
            map(NBTTagCompound.class, PacketBuffer::readCompoundTag, PacketBuffer::writeCompoundTag);
            map(ItemStack.class, PacketBuffer::readItemStack, PacketBuffer::writeItemStack);
            map(EntityPos.class, buf -> NBT.deserializePublicFields(buf, new EntityPos()), NBT::serializePublicFields);
            map(Pair.class, buf -> NBT.deserializePublicFields(buf, new Pair<>(null, null)), NBT::serializePublicFields);
        }

        public Object parent;

        public SerializationHandlers(Object parent)
        {
            this.parent = parent;
        }

        public static Pair<Reader, Writer> getHandler(Class<?> clazz)
        {
            Pair<Reader, Writer> pair = handlers.get(clazz);
            if (pair == null)
                throw new RuntimeException("No R/W handler for  " + clazz);
            return pair;
        }

        public static boolean acceptField(Field f, Class<?> type)
        {
            int mods = f.getModifiers();
            return !(Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods)) && handlers.containsKey(type);

        }

        public static <T> void map(Class<T> type, Reader<T> reader, Writer<T> writer)
        {
            handlers.put(type, Pair.of(reader, writer));
        }

        @SuppressWarnings("unchecked")
        public void writeField(Field f, Class clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException
        {
            Pair<Reader, Writer> handler = getHandler(clazz);
            try
            {
                handler.getValue().write(buf, f.get(parent));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void readField(Field f, Class clazz, PacketBuffer buf) throws IllegalArgumentException, IllegalAccessException, IOException
        {
            Pair<Reader, Writer> handler = getHandler(clazz);
            f.set(parent, handler.getKey().read(buf));
        }

        public interface Writer<T>
        {
            void write(PacketBuffer buf, T t) throws IOException;
        }

        public interface Reader<T>
        {
            T read(PacketBuffer buf) throws IOException;
        }
    }
}
