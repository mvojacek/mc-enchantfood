package com.github.hashtagshell.enchantfood.container;

import com.github.hashtagshell.enchantfood.block.tile.TileItemHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerItemHolder extends Container {
    private final TileItemHolder itemHolder;

    public ContainerItemHolder(final TileItemHolder itemHolder) {
        IItemHandler inventory = itemHolder.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        this.itemHolder = itemHolder;

        //Add item slot
        addSlotToContainer(new SlotItemHandler(inventory, 0, 0, 0) {
            @Override
            public void onSlotChanged() {
                itemHolder.markDirty();
            }
        });
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
