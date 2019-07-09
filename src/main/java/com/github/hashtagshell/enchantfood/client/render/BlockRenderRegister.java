package com.github.hashtagshell.enchantfood.client.render;

import com.github.hashtagshell.enchantfood.block.tile.TileItemHolder;
import com.github.hashtagshell.enchantfood.client.render.block.RenderItemHolder;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class BlockRenderRegister {
    public static void preinit() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemHolder.class, new RenderItemHolder());
    }
}
