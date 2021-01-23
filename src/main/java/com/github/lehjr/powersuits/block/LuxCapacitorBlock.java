package com.github.lehjr.powersuits.block;

import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.tile_entity.LuxCapacitorTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class LuxCapacitorBlock extends DirectionalBlock implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Colour defaultColor = new Colour(0.4F, 0.2F, 0.9F);

    protected static final VoxelShape LUXCAPACITOR_EAST_AABB = Block.makeCuboidShape(0, 1, 1, 4, 15, 15);
    protected static final VoxelShape LUXCAPACITOR_WEST_AABB = Block.makeCuboidShape(12, 1, 1, 16, 15, 15);
    protected static final VoxelShape LUXCAPACITOR_SOUTH_AABB = Block.makeCuboidShape(1, 1, 0.0, 15, 15, 4);
    protected static final VoxelShape LUXCAPACITOR_NORTH_AABB = Block.makeCuboidShape(1, 1, 12, 15, 15, 16);
    protected static final VoxelShape LUXCAPACITOR_UP_AABB = Block.makeCuboidShape(1, 0.0, 1, 15, 4, 15);
    protected static final VoxelShape LUXCAPACITOR_DOWN_AABB = Block.makeCuboidShape(1, 12, 1, 15, 16.0, 15);

    public LuxCapacitorBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(0.05F, 10.0F)
                .sound(SoundType.METAL)
                .variableOpacity()
                .setLightLevel((state) -> 15));
        setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
    }

    @Override
    public boolean canContainFluid(IBlockReader worldIn, BlockPos pos, BlockState state, Fluid fluidIn) {
        return true;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean receiveFluid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return IWaterLoggable.super.receiveFluid(worldIn, pos, state, fluidStateIn);
    }

    @Override
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
        return Fluids.EMPTY;
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 0;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite())
                .with(WATERLOGGED, Boolean.valueOf(ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8));
    }

    @SuppressWarnings( "deprecation" )
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction facing = state.hasProperty(FACING) ? state.get(FACING) : Direction.UP;
        return hasEnoughSolidSide(worldIn, pos.offset(facing.getOpposite()), facing);
    }

    @SuppressWarnings( "deprecation" )
    @Deprecated
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)) {
            default:
            case DOWN:
                return LUXCAPACITOR_DOWN_AABB;
            case UP:
                return LUXCAPACITOR_UP_AABB;
            case NORTH:
                return LUXCAPACITOR_NORTH_AABB;
            case SOUTH:
                return LUXCAPACITOR_SOUTH_AABB;
            case WEST:
                return LUXCAPACITOR_WEST_AABB;
            case EAST:
                return LUXCAPACITOR_EAST_AABB;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LuxCapacitorTileEntity();
    }
}
