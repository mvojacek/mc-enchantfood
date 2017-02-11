package com.github.hashtagshell.enchantfood.utility;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.InputStream;

public class Schematic
{
    public NBTTagList tileentities;
    public int        width;
    public int        height;
    public int        length;
    public byte[]     blocks;
    public byte[]     data;

    public Schematic(NBTTagList tileentities, int width, int height, int length, byte[] blocks, byte[] data)
    {
        this.tileentities = tileentities;
        this.width = width;
        this.height = height;
        this.length = length;
        this.blocks = blocks;
        this.data = data;
    }

    public static Schematic get(String path)
    {
        try
        {
            InputStream is = Schematic.class.getClassLoader().getResourceAsStream(path);
            NBTTagCompound nbtdata = CompressedStreamTools.readCompressed(is);
            int width = nbtdata.getShort("Width");
            int height = nbtdata.getShort("Height");
            int length = nbtdata.getShort("Length");

            byte[] blocks = nbtdata.getByteArray("Blocks");
            byte[] data = nbtdata.getByteArray("Data");

            NBTTagList tileentities = nbtdata.getTagList("TileEntities", 10);
            is.close();

            return new Schematic(tileentities, width, height, length, blocks, data);
        } catch (Exception e)
        {
            Log.error(String.format("Couldn't load schematic at %s because %s", path, e.toString()));
            e.printStackTrace(System.err);
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean place(World w, BlockPos pos, Schematic schematic)
    {
        if (schematic == null) { return false; }

        int i = 0;
        for (int sy = 0; sy < schematic.height; sy++)
            for (int sz = 0; sz < schematic.length; sz++)
                for (int sx = 0; sx < schematic.width; sx++)
                {

                    Block b = Block.getBlockById(schematic.blocks[i]);
                    if (b != Blocks.AIR)
                    {
                        BlockPos toPlace = pos.add(sx, sy, sz);
                        w.setBlockToAir(toPlace);
                        // Compatibility with 1.7.10 schems, don't know how 1.10 schematics store their blockstates
                        w.setBlockState(pos, b.getStateFromMeta(schematic.data[i]), 2);
                    }
                    i++;
                }

        if (schematic.tileentities != null)
        {
            for (int i1 = 0; i1 < schematic.tileentities.tagCount(); ++i1)
            {
                NBTTagCompound nbttagcompound = schematic.tileentities.getCompoundTagAt(i1);
                TileEntity tileentity = TileEntity.create(w, nbttagcompound);

                //noinspection ConstantConditions - IntelliJ doesn't know TE.create(W,NBT) can return null
                if (tileentity != null)
                {
                    BlockPos tePos = tileentity.getPos();
                    tePos.add(pos);
                    tileentity.setPos(tePos);
                    w.setTileEntity(tePos, tileentity);
                }
            }
        }
        return true;
    }
}