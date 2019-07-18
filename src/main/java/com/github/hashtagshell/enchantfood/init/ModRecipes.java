package com.github.hashtagshell.enchantfood.init;

import com.github.hashtagshell.enchantfood.recipes.RecipeEssenceInfusion;
import com.github.hashtagshell.enchantfood.recipes.RecipeFoodInfusion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;

public class ModRecipes
{
    public static ArrayList<RecipeFoodInfusion> foodInfusions = new ArrayList<RecipeFoodInfusion>();
    public static ArrayList<RecipeEssenceInfusion> essenceInfusions = new ArrayList<RecipeEssenceInfusion>();

    public static void init() {
        GameRegistry.addRecipe(new ItemStack(ModBlocks.foodEnchanter, 1), "D D", "CEC", "CSC", 'D', Items.DIAMOND, 'C', Blocks.COBBLESTONE, 'E', Blocks.ENCHANTING_TABLE, 'S', Items.EMERALD);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.essenceProvider, 1), "W W", "WSW", "W W", 'W', Blocks.PLANKS, 'S', ModItems.essenceShard);
        GameRegistry.addRecipe(new ItemStack(ModBlocks.essenceFocuser, 1), "WWW", " SW", "WWW", 'W', Blocks.PLANKS, 'S', ModItems.essenceShard);
        GameRegistry.addRecipe(new ItemStack(ModItems.meteorStaff, 1), "  W", " S ", "W  ", 'W', ModItems.overloadedEssenceShard, 'S', ModItems.essenceStick);
        foodInfusions.add(new RecipeFoodInfusion(new ItemStack(Items.EMERALD, 1), new ItemStack(ModItems.essenceShard, 2), 600));
        foodInfusions.add(new RecipeFoodInfusion(new ItemStack(Items.STICK, 1), new ItemStack(ModItems.beefStick, 1), 1200));
        essenceInfusions.add(new RecipeEssenceInfusion(new ItemStack(ModItems.essenceShard, 1), new ItemStack(ModItems.overloadedEssenceShard, 1), 64000));
        essenceInfusions.add(new RecipeEssenceInfusion(new ItemStack(Items.STICK, 1), new ItemStack(ModItems.essenceStick, 1), 8000));
        }

}
