package com.github.hashtagshell.enchantfood.block;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.github.hashtagshell.enchantfood.block.lib.tile.BlockTileGeneric;
import com.github.hashtagshell.enchantfood.block.tile.TileFoodAltar;
import com.github.hashtagshell.enchantfood.block.tile.TileMultiblockFoodAltar;
import com.github.hashtagshell.enchantfood.reference.Ref;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockMultiblockFoodAltar extends BlockTileGeneric<TileMultiblockFoodAltar>
{
    public static final PropertyEnum<AltarMultiblockType> ALTAR_PART = PropertyEnum.create("altarpart", AltarMultiblockType.class);
    public static final PropertyDirection                 FACING     = BlockHorizontal.FACING;


    public BlockMultiblockFoodAltar(String name)
    {
        super(name, Material.ANVIL);
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ALTAR_PART, AltarMultiblockType.MAGMA));
    }

    @Override
    public Class<TileMultiblockFoodAltar> tileClass()
    {
        return TileMultiblockFoodAltar.class;
    }

    @Override
    public String tileId()
    {
        return Ref.Blocks.MULTIBLOCK_FOOD_ALTAR;
    }

    @SuppressWarnings("deprecation") //Just Mojangs message that they will remove it, it still exists in 1.12
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileMultiblockFoodAltar();
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileMultiblockFoodAltar tmfa = (TileMultiblockFoodAltar) worldIn.getTileEntity(pos);
        assert tmfa != null;
        if (tmfa.altarPos != null)
        {
            TileFoodAltar altar = (TileFoodAltar) worldIn.getTileEntity(tmfa.altarPos);
            assert altar != null;
            altar.destroyMultiblock();
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return super.getBoundingBox(state, source, pos);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        if(blockState.getValue(ALTAR_PART) == AltarMultiblockType.CORNER)
        {
            return new AxisAlignedBB(0.0,0.0,0.0,1.0,0.5,1.0);
        }
        return new AxisAlignedBB(0.0,0.0,0.0,1.0,1.0,1.0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        IProperty[] propertyArray = {FACING, ALTAR_PART};
        return new BlockStateContainer(this, propertyArray);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {

        int rotationMeta = meta & 0b11;
        int type = (meta >> 2) & 0b11;
        EnumFacing rotation = EnumFacing.HORIZONTALS[rotationMeta];
        AltarMultiblockType multiblock = AltarMultiblockType.values()[type];

        return this.getDefaultState().withProperty(ALTAR_PART, multiblock).withProperty(FACING, rotation);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {

        int rotation = state.getValue(FACING).getHorizontalIndex();
        int altarPart = state.getValue(ALTAR_PART).getIndex();

        return (rotation & 0b11) | ((altarPart & 0b11) << 2);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return super.getItemDropped(state, rand, fortune);
    }
}
