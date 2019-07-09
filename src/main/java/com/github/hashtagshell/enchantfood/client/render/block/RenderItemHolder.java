package com.github.hashtagshell.enchantfood.client.render.block;

import com.github.hashtagshell.enchantfood.block.tile.TileItemHolder;
import com.github.hashtagshell.enchantfood.client.render.BlockRenderRegister;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;

public class RenderItemHolder extends TileEntitySpecialRenderer<TileItemHolder> {

    @Override
    public void renderTileEntityAt(TileItemHolder itemHolder, double x, double y, double z, float partialTicks, int destroyStage) {
        if (itemHolder.inventory.getStackInSlot(0).getItem() != Items.AIR) {
            BlockRenderRegister.RenderHelper.floatingItemRenderer(x, y + 0.5, z, partialTicks, itemHolder.inventory, itemHolder.getWorld(), itemHolder.inventory.getStackInSlot(0), 8.0);
        }
    }
}
