package com.github.hashtagshell.enchantfood.handler;

import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import com.github.hashtagshell.enchantfood.client.gui.BlockGuiFoodEnchanter;
import com.github.hashtagshell.enchantfood.container.ContainerFoodEnchanter;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.util.Objects;

public class GuiHandler implements IGuiHandler {
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case Ref.Gui.FOOD_ENCHANTER_GUI:
                return new ContainerFoodEnchanter(player.inventory, (TileFoodEnchanter) Objects.requireNonNull(world.getTileEntity(new BlockPos(x, y, z))));
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case Ref.Gui.FOOD_ENCHANTER_GUI:
                return new BlockGuiFoodEnchanter((ContainerFoodEnchanter) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}
