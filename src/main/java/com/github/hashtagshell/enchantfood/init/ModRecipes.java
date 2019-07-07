package com.github.hashtagshell.enchantfood.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{
    public static void init() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.foodEnchanter, 1), "D D", "CEC", "CSC", 'D', Items.DIAMOND, 'C', Blocks.COBBLESTONE, 'E', Blocks.ENCHANTING_TABLE, 'S', Items.EMERALD);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.essenceProvider, 1), "W W", "WSW", "W W", 'W', Blocks.PLANKS, 'S', ModItems.essenceShard);
    }

}
