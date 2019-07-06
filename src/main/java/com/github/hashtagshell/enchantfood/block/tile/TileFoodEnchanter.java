package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import com.github.hashtagshell.enchantfood.network.message.MessageFoodEnchanter;
import com.github.hashtagshell.enchantfood.network.message.MessageFoodEnchanterReq;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class TileFoodEnchanter extends TileGeneric implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(3);


    public static final String INVENTORY_NBT = "inventory";
    public static final String PROGRESS_NBT = "progress";
    public static final String FUEL_NBT = "storedFuel";
    public static final String WORKING_NBT = "isWorking";

    public boolean working = false;
    public int fuel = 0;
    public final int fuelMax = 640;
    public int progress = 0;
    public final int progressMax = 200;

    private int lastSendFuel = 0;
    private Random random = new Random();

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        fuel = compound.getInteger(FUEL_NBT);
        progress = compound.getInteger(PROGRESS_NBT);
        inventory.deserializeNBT(compound.getCompoundTag(INVENTORY_NBT));
        working = compound.getBoolean(WORKING_NBT);
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag(INVENTORY_NBT, inventory.serializeNBT());
        compound.setInteger(PROGRESS_NBT, progress);
        compound.setInteger(FUEL_NBT, fuel);
        compound.setBoolean(WORKING_NBT, working);
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
    public void onLoad() {
        if (world.isRemote) {
            NetworkWrapper.network.sendToServer(new MessageFoodEnchanterReq(this));
        }
        super.onLoad();
    }

    @Override
    public void update() {
        ItemStack fuelItem = inventory.getStackInSlot(1);

        if (fuelItem != ItemStack.EMPTY && fuelItem.getItem() instanceof ItemFood) {
            ItemFood food = (ItemFood) fuelItem.getItem();
            int itemFuelValue = food.getHealAmount(fuelItem);

            if (fuel + itemFuelValue < fuelMax) {
                if (fuelItem.getCount() > 1) {
                    inventory.extractItem(1, 1, false);
                } else {
                    inventory.setStackInSlot(1, ItemStack.EMPTY);
                }
                fuel += itemFuelValue;

            }
        }

        if (fuel != lastSendFuel && world.isRemote) {
            BlockPos mypos = getPos();
            double x = mypos.getX();
            double y = mypos.getY();
            double z = mypos.getZ();
            double range = 16;
            NetworkWrapper.network.sendToAllAround(new MessageFoodEnchanter(getPos(), fuel, progress, working), new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, range));
        }

        ItemStack craftingItem = inventory.getStackInSlot(0);

        if (craftingItem != ItemStack.EMPTY && craftingItem.getCount() == 1 && craftingItem.getItem() instanceof ItemFood) {
            ItemFood food = (ItemFood) fuelItem.getItem();
            int itemValue = food.getHealAmount(fuelItem);
            int operationCost = (int) Math.ceil((double) itemValue * 24.0 / 200.0);
            if (fuel > operationCost) {

                if (itemValue * 24 >= fuel && working == false) {
                    working = true;
                }

                if (working) {
                    progress++;
                    fuel -= operationCost;

                    if (progress == progressMax) {
                        working = false;
                        ItemStack output = craftingItem.copy();
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        int enchantCount = random.nextInt(3) + 1;

                        for (int i = 0; i < enchantCount; i++) {

                        }
                    }
                }
            }
        }
    }

    public void reqData() {
        if (world.isRemote) {
            NetworkWrapper.network.sendToServer(new MessageFoodEnchanterReq(this));
        }
    }
}
