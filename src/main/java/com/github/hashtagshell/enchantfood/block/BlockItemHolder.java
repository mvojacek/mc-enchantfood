package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileItemHolder;
import com.github.hashtagshell.enchantfood.init.ModItems;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.github.hashtagshell.enchantfood.network.NetworkWrapper.dispatchTEToNearbyPlayers;

public class BlockItemHolder extends BlockTileGeneric<TileItemHolder> {
    public BlockItemHolder(String name) {
        super(name, Material.WOOD);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public Class<TileItemHolder> tileClass() {
        return TileItemHolder.class;
    }

    @Override
    public String tileId() {
        return Ref.Blocks.ITEM_HOLDER;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(1.0 / 16.0 * 2.0, 0.0, 1.0 / 16.0 * 2.0, 1.0 / 16.0 * 14.0, 1.0 / 16.0 * 11.0, 1.0 / 16.0 * 14.0);
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(1.0 / 16.0 * 2.0, 0.0, 1.0 / 16.0 * 2.0, 1.0 / 16.0 * 14.0, 1.0 / 16.0 * 11.0, 1.0 / 16.0 * 14.0);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileItemHolder();
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileItemHolder itemHolder = (TileItemHolder) world.getTileEntity(pos);
            if (!player.isSneaking()) {
                if (player.inventory.getStackInSlot(player.inventory.currentItem).getItem() == ModItems.beefStick) {
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Mystical Item Holder Contains: " + itemHolder.inventory.getStackInSlot(0).getItem().getUnlocalizedName()));
                    return true;
                }

                if (player.inventory.getStackInSlot(player.inventory.currentItem).getItem() == Items.AIR && itemHolder.inventory.getStackInSlot(0) != ItemStack.EMPTY) {
                    player.inventory.addItemStackToInventory(itemHolder.inventory.getStackInSlot(0));
                    itemHolder.inventory.setStackInSlot(0, ItemStack.EMPTY);
                    dispatchTEToNearbyPlayers(itemHolder);
                    return true;
                }

                if (player.inventory.getStackInSlot(player.inventory.currentItem) != ItemStack.EMPTY && itemHolder.inventory.getStackInSlot(0).getItem() == Items.AIR) {
                    ItemStack item = player.inventory.getStackInSlot(player.inventory.currentItem).copy();
                    item.setCount(1);
                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
                    itemHolder.inventory.setStackInSlot(0, item);
                    dispatchTEToNearbyPlayers(itemHolder);
                    return true;
                }


            } else {

            }
        }
        return true;

    }

}
