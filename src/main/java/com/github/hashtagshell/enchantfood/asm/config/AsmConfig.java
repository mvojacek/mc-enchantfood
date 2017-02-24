package com.github.hashtagshell.enchantfood.asm.config;

import com.github.hashtagshell.enchantfood.reference.Ref;
import com.github.hashtagshell.enchantfood.utility.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AsmConfig
{
    private static File       file  = null;
    static         Properties props = new Properties();

    private static boolean dirty = false;

    private AsmConfig() {}

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void setFileWithModConfigDir()
    {
        try
        {
            File dModConf = Ref.Files.MOD_CONF_DIR();
            File fAsmConfig = new File(dModConf, "asm.properties");
            if (!fAsmConfig.exists())
                fAsmConfig.createNewFile();
            else if (!fAsmConfig.isFile())
                throw new IOException(String.format(
                        "ASM config file, %s, exists but is not a file!",
                        fAsmConfig.getAbsolutePath()));
            file = fAsmConfig;
        }
        catch (IOException e)
        {
            Log.errorex(e, "Could not initialize ASM config file! Defaults will be used.");
        }
        loadProps();
    }

    public static void setFile(File file)
    {
        AsmConfig.file = file;
        loadProps();
    }

    public static void loadProps()
    {
        if (file == null)
        {
            Log.error("Tried to read ASM config from file, but file was not initialized! Defaults will be used.");
            return;
        }
        props.clear();
        try (FileInputStream fis = new FileInputStream(file))
        {
            props.load(fis);
        }
        catch (IOException | IllegalArgumentException e)
        {
            Log.errorex(e, "Error while reading ASM config file. Defaults will be used.");
            return;
        }
        loadValues();
    }

    static void saveProps()
    {
        if (file == null)
        {
            Log.error("Tried to write defaults to ASM config file, but file was not initialized!");
            return;
        }
        try (FileOutputStream fos = new FileOutputStream(file))
        {
            props.store(fos, "EnchantFood ASM config. This is typical Java Properties file. "
                             + "You can use values 'true', 'yes', '1', 'one' and 'false', "
                             + "'no', '0', 'zero'. Any other values will be overwritten with "
                             + "defaults.");
        }
        catch (IOException | IllegalArgumentException e)
        {
            Log.errorex(e, "Error while writing defaults to ASM config file!");
        }
    }

    public static void loadValues()
    {
        AsmConf.loadProps();
        if (dirty) saveProps();
    }

    static void markDirty()
    {
        dirty = true;
    }
}
