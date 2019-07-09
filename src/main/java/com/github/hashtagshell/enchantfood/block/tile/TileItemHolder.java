package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileItemHolder extends TileGeneric {
    public ItemStackHandler inventory = new ItemStackHandler(1);

    public static final String INVENTORY_NBT = "inventory";

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        inventory.deserializeNBT(compound.getCompoundTag(INVENTORY_NBT));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag(INVENTORY_NBT, inventory.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void writePacketNBT(NBTTagCompound cmp) {
        cmp.setTag(INVENTORY_NBT, inventory.serializeNBT());
    }

    @Override
    public void readPacketNBT(NBTTagCompound cmp) {
        inventory.deserializeNBT(cmp.getCompoundTag(INVENTORY_NBT));
    }

    @Override
    public void onLoad() {
        NetworkWrapper.dispatchTEToNearbyPlayers(this);
        super.onLoad();
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }
}
