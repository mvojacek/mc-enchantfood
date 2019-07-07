package com.github.hashtagshell.enchantfood.block.tile;

import com.github.hashtagshell.enchantfood.block.lib.tile.TileGeneric;
import com.github.hashtagshell.enchantfood.ench.EnchantmentFood;
import com.github.hashtagshell.enchantfood.init.ModEnchantments;
import com.github.hashtagshell.enchantfood.network.NetworkWrapper;
import com.github.hashtagshell.enchantfood.network.message.MessageFoodEnchanter;
import com.github.hashtagshell.enchantfood.network.message.MessageFoodEnchanterReq;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
            ItemFood food = (ItemFood) craftingItem.getItem();
            int itemValue = food.getHealAmount(craftingItem);
            int operationCost = (int) Math.ceil((double) itemValue * 48.0 / 200.0);
            if (fuel > operationCost) {

                if (itemValue * 24 <= fuel && !working) {
                    working = true;
                }

                if (working) {
                    progress++;
                    fuel -= operationCost;
                    spawnWorkingParticles();
                    if (progress >= progressMax) {
                        working = false;
                        ItemStack output = craftingItem.copy();
                        inventory.setStackInSlot(0, ItemStack.EMPTY);
                        int enchantCount = random.nextInt(3) + 1;

                        for (int i = 0; i < enchantCount; i++) {
                            int enchRoll = random.nextInt(ModEnchantments.foodEnchants.size());
                            EnchantmentFood ench = ModEnchantments.foodEnchants.get(enchRoll);
                            if (EnchantmentHelper.getEnchantments(output).size() > 0) {
                                    if (EnchantmentHelper.getEnchantments(output).containsKey(ench)) {
                                        output.addEnchantment(ench, ench.maxLevel);
                                    }
                            } else if (ench.canApply(output)) {
                                output.addEnchantment(ench, random.nextInt(ModEnchantments.foodEnchants.get(enchRoll).maxLevel) + 1);
                            }
                        }

                        inventory.setStackInSlot(2, output);
                    }
                }
            }
        }

        if (craftingItem == ItemStack.EMPTY && progress != 0) {
            progress = 0;
            working = false;
        }
    }

    public void spawnWorkingParticles() {
        World thisWorld = getWorld();
        BlockPos pos = getPos();
        double radius = 0.5;
        double radian = random.nextDouble() * 8.0;
        double zPos = pos.getZ() + 0.5 + radius * Math.sin(radian);
        double xPos = pos.getX() + 0.5 + radius * Math.cos(radian);
        double yPos = pos.getY() + 1.6;
        double xVel = Math.cos(radian) * (1 / (radius / 2));
        double zVel = Math.sin(radian) * (1 / (radius / 2));
        double yVel = -0.4;

        thisWorld.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, xPos, yPos, zPos, xVel, yVel, zVel);
    }

    public void reqData() {
        if (world.isRemote) {
            NetworkWrapper.network.sendToServer(new MessageFoodEnchanterReq(this));
        }
    }
}
