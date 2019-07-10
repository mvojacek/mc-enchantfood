package com.github.hashtagshell.enchantfood.container;

import com.github.hashtagshell.enchantfood.block.tile.TileFoodAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFoodAltar extends Container {
    private final TileFoodAltar foodAltar;

    public ContainerFoodAltar(final TileFoodAltar foodAltar) {
        IItemHandler inventory = foodAltar.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        this.foodAltar = foodAltar;

        //Add item slot
        addSlotToContainer(new SlotItemHandler(inventory, 0, 0, 0) {
            @Override
            public void onSlotChanged() {
                foodAltar.markDirty();
            }
        });
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
