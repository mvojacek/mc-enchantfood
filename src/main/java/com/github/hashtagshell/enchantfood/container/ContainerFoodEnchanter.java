package com.github.hashtagshell.enchantfood.container;

import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import com.github.hashtagshell.enchantfood.utility.GUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ContainerFoodEnchanter extends Container {
    public ContainerFoodEnchanter(InventoryPlayer playerInv, final TileFoodEnchanter foodEnchanter) {
        IItemHandler inventory = foodEnchanter.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        this.foodEnchanter = foodEnchanter;

        //Add food to enchant slot
        addSlotToContainer(new SlotItemHandler(inventory, 0, GUI.INVENTORY_WIDTH / 2 - 12, 35) {
            @Override
            public void onSlotChanged() {
                foodEnchanter.markDirty();
            }
        });

        //Add Fuel slot
        addSlotToContainer(new SlotItemHandler(inventory, 1, GUI.SIDE_SPACING, 51) {
            @Override
            public void onSlotChanged() {
                foodEnchanter.markDirty();
            }
        });

        //Add output slot
        addSlotToContainer(new SlotItemHandler(inventory, 2, GUI.INVENTORY_WIDTH / 2 + 53, 35) {
            @Override
            public void onSlotChanged() {
                foodEnchanter.markDirty();
            }

            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return false;
            }
        });


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, GUI.SIDE_SPACING + j * GUI.SLOT_SIZE, GUI.PLAYER_INVENTORY_TOP_Y_POS + i * GUI.SLOT_SIZE));
            }
        }

        for (int k = 0; k < 9; k++) {
            addSlotToContainer(new Slot(playerInv, k, GUI.SIDE_SPACING + k * GUI.SLOT_SIZE, GUI.HOTBAR_SLOTS_Y_POS));
        }
    }

    public TileFoodEnchanter foodEnchanter;

    public final int FUEL_ID = 0;
    public final int PROGRESS_ID = 1;

    public int progress;
    public int fuel;

    @Override
    public void updateProgressBar(int id, int data) {
        if (id == FUEL_ID) {
            this.fuel = data;
        }
        if (id == PROGRESS_ID) {
            this.progress = data;
        }
        super.updateProgressBar(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        for (int i = 0; i < this.listeners.size(); i++) {
            IContainerListener listener = this.listeners.get(i);

            if (this.fuel != this.foodEnchanter.fuel) {
                this.fuel = this.foodEnchanter.fuel;
                listener.sendProgressBarUpdate(this, FUEL_ID, fuel);
            }

            if (this.progress != this.foodEnchanter.progress) {
                this.progress = this.foodEnchanter.progress;
                listener.sendProgressBarUpdate(this, PROGRESS_ID, progress);
            }
        }
        super.detectAndSendChanges();
    }

    //Transfer stack with shiftclick
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
