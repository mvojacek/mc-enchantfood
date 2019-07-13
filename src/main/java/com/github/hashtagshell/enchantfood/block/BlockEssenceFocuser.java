package com.github.hashtagshell.enchantfood.block;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileEssenceFocuser;
import com.github.hashtagshell.enchantfood.init.ModItems;
import com.github.hashtagshell.enchantfood.reference.Ref;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEssenceFocuser extends BlockTileGeneric<TileEssenceFocuser> {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public BlockEssenceFocuser(String name) {
        super(name, Material.WOOD);
        setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }


    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                TileEssenceFocuser tileEssenceFocuser = (TileEssenceFocuser) world.getTileEntity(pos);
                if (player.inventory.getStackInSlot(player.inventory.currentItem).getItem() == ModItems.beefStick) {
                    world.setBlockState(pos, state.withProperty(FACING, tileEssenceFocuser.getRotation()));
                }
            } else {
                //Something when sneaking
            }
        }
        return true;

    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {

        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {

        EnumFacing facing = state.getValue(FACING);

        int meta = state.getValue(FACING).getIndex();

        return meta;
    }


    @Override
    public Class<TileEssenceFocuser> tileClass() {
        return TileEssenceFocuser.class;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public String tileId() {
        return Ref.Blocks.ESSENCE_FOCUSER;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEssenceFocuser();
    }
}
