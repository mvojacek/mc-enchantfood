package com.github.hashtagshell.enchantfood.client.render.block;

import com.github.hashtagshell.enchantfood.block.tile.TileFoodAltar;
import com.github.hashtagshell.enchantfood.client.render.BlockRenderRegister;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Items;

public class RenderFoodAltar extends TileEntitySpecialRenderer<TileFoodAltar> {

    @Override
    public void renderTileEntityAt(TileFoodAltar foodAltar, double x, double y, double z, float partialTicks, int destroyStage) {
        if (foodAltar.inventory.getStackInSlot(0).getItem() != Items.AIR) {
            BlockRenderRegister.RenderHelper.floatingItemRenderer(x, y + 0.25, z, partialTicks, foodAltar.inventory, foodAltar.getWorld(), foodAltar.inventory.getStackInSlot(0), 8.0);
        }
    }
}
