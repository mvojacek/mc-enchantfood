package com.github.hashtagshell.enchantfood.asm;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

import com.github.hashtagshell.enchantfood.reference.Ref;

import java.util.Map;

@MCVersion(Ref.Mod.MC_VERSION)
@SortingIndex(Ref.Asm.SORTING_INDEX)
@TransformerExclusions({"com.github.hashtagshell.enchantfood.asm", "com.github.hashtagshell.enchantfood.reference"})
public class EnchantFoodPlugin implements IFMLLoadingPlugin
{
    public static LaunchClassLoader launchClassLoader;

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[]{EnchantFoodTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {return null;}

    @Override
    public String getSetupClass() {return null;}

    @Override
    public void injectData(Map<String, Object> data)
    {
        ObfState.setObfuscatedEnvironment((Boolean) data.get("runtimeDeobfuscationEnabled"));
    }

    @Override
    public String getAccessTransformerClass() {return null;}
}
