package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.EnchantFood;
import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileFoodEnchanter;
import com.github.hashtagshell.enchantfood.reference.RefBlocks;
import com.github.hashtagshell.enchantfood.reference.RefGui;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockFoodEnchanter extends BlockTileGeneric<TileFoodEnchanter> {
    public BlockFoodEnchanter(String name) {
        super(name, Material.ROCK);
        setSoundType(SoundType.STONE);
    }

    @Override
    public Class<TileFoodEnchanter> tileClass() {
        return TileFoodEnchanter.class;
    }


    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public String tileId() {
        return RefBlocks.FOOD_ENCHANTER;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFoodEnchanter();
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                TileFoodEnchanter tileFoodEnchanter = getTileAt(world, pos);
                tileFoodEnchanter.reqData();
                player.openGui(EnchantFood.instance, RefGui.FOOD_ENCHANTER_GUI, world, pos.getX(), pos.getY(), pos.getZ());
            } else {
                //Something when sneaking
            }
        }
        return true;

    }
}
