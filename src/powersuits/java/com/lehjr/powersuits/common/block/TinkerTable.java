package com.lehjr.powersuits.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TinkerTable extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape TOP_SHAPE = Block.box(
            0.0D, // West
            14.0D, // down?
            0.0D, // north
            16.0D, // east
            16.0D, // up?
            16.0D); // South

    public TinkerTable() {
        super(Block.Properties.of(Material.WOOD)
                .strength(1.5F, 1000.0F)
                .sound(SoundType.WOOD)
                .dynamicShape()
                .lightLevel((state) -> 15));
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @SuppressWarnings( "deprecation" )
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TOP_SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }
}
