package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.network.PacketBuffer;

import com.github.hashtagshell.enchantfood.network.message.MessageGeneric.SerializationHandlers;
import com.github.hashtagshell.enchantfood.network.message.MessageGeneric.SerializationHandlers.Reader;
import com.github.hashtagshell.enchantfood.network.message.MessageGeneric.SerializationHandlers.Writer;
import com.github.hashtagshell.enchantfood.utility.tuple.Pair;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.github.hashtagshell.enchantfood.reference.Ref.Nbt.COMP_MOD;
import static com.github.hashtagshell.enchantfood.reference.Ref.Nbt.TagType.COMPOUND;

@SuppressWarnings("ConstantConditions")
public class NBT
{
    public static boolean hasTag(ItemStack itemStack, String keyName)
    {
        return itemStack != null && itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey(keyName);
    }

    public static void removeTag(ItemStack itemStack, String keyName)
    {
        if (itemStack.getTagCompound() != null)
        {
            itemStack.getTagCompound().removeTag(keyName);
        }
    }

    /**
     * Initializes the NBT Tag Compound for the given ItemStack if it is null
     *
     * @param itemStack The ItemStack for which its NBT Tag Compound is being checked for initialization
     */
    public static NBTTagCompound initNBTTagCompound(ItemStack itemStack)
    {
        if (itemStack.getTagCompound() == null)
        {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        return itemStack.getTagCompound();
    }

    public static void setLong(ItemStack itemStack, String keyName, long keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setLong(keyName, keyValue);
    }

    // String
    public static String getString(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setString(itemStack, keyName, "");
        }

        return itemStack.getTagCompound().getString(keyName);
    }

    public static void setString(ItemStack itemStack, String keyName, String keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setString(keyName, keyValue);
    }

    // boolean
    public static boolean getBoolean(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setBoolean(itemStack, keyName, false);
        }

        return itemStack.getTagCompound().getBoolean(keyName);
    }

    public static void setBoolean(ItemStack itemStack, String keyName, boolean keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setBoolean(keyName, keyValue);
    }

    // byte
    public static byte getByte(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setByte(itemStack, keyName, (byte) 0);
        }

        return itemStack.getTagCompound().getByte(keyName);
    }

    public static void setByte(ItemStack itemStack, String keyName, byte keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setByte(keyName, keyValue);
    }

    // short
    public static short getShort(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setShort(itemStack, keyName, (short) 0);
        }

        return itemStack.getTagCompound().getShort(keyName);
    }

    public static void setShort(ItemStack itemStack, String keyName, short keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setShort(keyName, keyValue);
    }

    // int
    public static int getInt(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setInteger(itemStack, keyName, 0);
        }

        return itemStack.getTagCompound().getInteger(keyName);
    }

    public static void setInteger(ItemStack itemStack, String keyName, int keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setInteger(keyName, keyValue);
    }

    // long
    public static long getLong(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setLong(itemStack, keyName, 0);
        }

        return itemStack.getTagCompound().getLong(keyName);
    }

    // float
    public static float getFloat(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setFloat(itemStack, keyName, 0);
        }

        return itemStack.getTagCompound().getFloat(keyName);
    }

    public static void setFloat(ItemStack itemStack, String keyName, float keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setFloat(keyName, keyValue);
    }

    // double
    public static double getDouble(ItemStack itemStack, String keyName)
    {
        initNBTTagCompound(itemStack);

        if (!itemStack.getTagCompound().hasKey(keyName))
        {
            setDouble(itemStack, keyName, 0);
        }

        return itemStack.getTagCompound().getDouble(keyName);
    }

    public static void setDouble(ItemStack itemStack, String keyName, double keyValue)
    {
        initNBTTagCompound(itemStack);

        itemStack.getTagCompound().setDouble(keyName, keyValue);
    }

    /*
     * Player NBT Part
     */

    public static NBTTagCompound getDataTag(EntityPlayer player)
    {
        NBTTagCompound forgeData = player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        NBTTagCompound beaconData = forgeData.getCompoundTag(COMP_MOD);

        if (!forgeData.hasKey(COMP_MOD)) forgeData.setTag(COMP_MOD, beaconData);

        return beaconData;
    }

    public static NBTTagCompound fromIntArray(NBTTagIntArray intArray)
    {
        NBTTagCompound c = new NBTTagCompound();
        int index = -1;
        for (int i : intArray.getIntArray())
        {
            i++;
            c.setInteger(String.valueOf(index), i);
        }
        c.setInteger("maxIndex", index);
        return c;
    }

    public static NBTTagIntArray toIntArray(NBTTagCompound compound)
    {
        int maxIndex = compound.getInteger("maxIndex");
        int[] array = new int[maxIndex + 1];
        for (int i = 0; i < maxIndex; i++)
            array[i] = compound.getInteger(String.valueOf(i));
        return new NBTTagIntArray(array);
    }

    @SuppressWarnings("unchecked")
    public static PacketBuffer serializePublicFields(PacketBuffer buf, Object o)
    {
        for (Field f : o.getClass().getFields())
        {
            Class<?> type = f.getType();
            if (!SerializationHandlers.acceptField(f, type)) continue;
            Pair<Reader, Writer> handler = SerializationHandlers.getHandler(type);
            try
            {
                handler.getValue().write(buf, f.get(o));
            }
            catch (IOException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return buf;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserializePublicFields(PacketBuffer buf, T instance)
    {
        for (Field f : instance.getClass().getFields())
        {
            Class<?> type = f.getType();
            if (!SerializationHandlers.acceptField(f, type)) continue;
            Pair<Reader, Writer> handler = SerializationHandlers.getHandler(type);
            try
            {
                f.set(instance, handler.getKey().read(buf));
            }
            catch (IOException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public static NBTTagCompound getModTag(ItemStack stack)
    {
        NBTTagCompound tag = initNBTTagCompound(stack);
        NBTTagCompound mod = tag.getCompoundTag(COMP_MOD);
        tag.setTag(COMP_MOD, mod);
        return mod;
    }

    public static boolean hasModTag(ItemStack stack)
    {
        NBTTagCompound tag = initNBTTagCompound(stack);
        return tag.hasKey(COMP_MOD, COMPOUND.id());
    }

    public static void removeModTagIfEmpty(ItemStack stack)
    {
        NBTTagCompound tag = initNBTTagCompound(stack);
        if (tag.hasKey(COMP_MOD, COMPOUND.id()) && tag.getTag(COMP_MOD).hasNoTags())
            tag.removeTag(COMP_MOD);
    }
}
