package com.github.hashtagshell.enchantfood.client.render;

import com.github.hashtagshell.enchantfood.block.tile.TileItemHolder;
import com.github.hashtagshell.enchantfood.client.render.block.RenderItemHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.opengl.GL11;

public class BlockRenderRegister {
    public static void preinit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemHolder.class, new RenderItemHolder());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileFoodAltar.class, new RenderFoodAltar());
    }

    public static class RenderHelper {
        public static void floatingItemRenderer(double x, double y, double z, float partialTicks, ItemStackHandler inventory, World world, ItemStack item, double amplitudeDivider) {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();
            double offset = Math.sin((world.getTotalWorldTime() + partialTicks) / 8) / amplitudeDivider;
            GlStateManager.translate(x + 0.5, y + offset, z + 0.5);
            GlStateManager.rotate((world.getTotalWorldTime() + partialTicks) * 4, 0, 1, 0);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(item, world, null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(item, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }
}
