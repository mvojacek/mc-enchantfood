package com.github.hashtagshell.enchantfood.client.gui;

import com.github.hashtagshell.enchantfood.container.ContainerFoodEnchanter;
import com.github.hashtagshell.enchantfood.init.ModBlocks;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class BlockGuiFoodEnchanter extends GuiContainer {
    private InventoryPlayer playerInv;

    private static final ResourceLocation texture = new ResourceLocation(Ref.Mod.ID, Ref.Resources.GUI_RESOURCE_FOLDER + "foodenchanter.png");

    private ContainerFoodEnchanter foodEnchanterContainer;

    public BlockGuiFoodEnchanter(ContainerFoodEnchanter container, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;
        this.foodEnchanterContainer = container;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(texture);
        int xs = (width - xSize) / 2;
        int ys = (height - ySize) / 2;
        drawTexturedModalRect(xs, ys, 0, 0, xSize, ySize);

        if (foodEnchanterContainer.fuel > 0) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(texture);
            int x = xs + 9;
            double y = ys + 48.0 - Math.ceil(38.0 / foodEnchanterContainer.foodEnchanter.fuelMax * foodEnchanterContainer.fuel);
            int textureX = 176;
            double textureY = 36 - Math.ceil(36.0 / foodEnchanterContainer.foodEnchanter.fuelMax * foodEnchanterContainer.fuel);
            int width = 14;
            double height = Math.ceil(38.0 / foodEnchanterContainer.foodEnchanter.fuelMax * foodEnchanterContainer.fuel);
            drawTexturedModalRect(x, (int) (y), textureX, (int) textureY, width, (int) height);
        }

        if (foodEnchanterContainer.progress > 0) {
            GlStateManager.color(1, 1, 1, 1);
            mc.getTextureManager().bindTexture(texture);
            int x = xs + 97;
            int y = ys + 32;
            int textureX = 176;
            int textureY = 37;
            double width = 34.0 / foodEnchanterContainer.foodEnchanter.progressMax * foodEnchanterContainer.progress;
            int height = 21;
            drawTexturedModalRect(x, y, textureX, textureY, (int) width, height);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(ModBlocks.foodEnchanter.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
        //fontRenderer.drawString(foodEnchanterContainer.fuel + " Fuel", 8, ySize - 180, 0x404040);
    }
}
