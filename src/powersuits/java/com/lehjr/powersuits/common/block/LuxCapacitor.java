package com.lehjr.powersuits.common.block;

import com.lehjr.numina.common.math.Color;
import com.lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LuxCapacitor extends DirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Color defaultColor = new Color(0.4F, 0.2F, 0.9F);

    protected static final VoxelShape EAST_AABB = Block.box(0, 1, 1, 4, 15, 15);
    protected static final VoxelShape WEST_AABB = Block.box(12, 1, 1, 16, 15, 15);
    protected static final VoxelShape SOUTH_AABB = Block.box(1, 1, 0.0, 15, 15, 4);
    protected static final VoxelShape NORTH_AABB = Block.box(1, 1, 12, 15, 15, 16);
    protected static final VoxelShape UP_AABB = Block.box(1, 0.0, 1, 15, 4, 15);
    protected static final VoxelShape DOWN_AABB = Block.box(1, 12, 1, 15, 16.0, 15);

    public LuxCapacitor() {
        super(Block.Properties.of(Material.METAL)
                .strength(0.05F, 10.0F)
                .sound(SoundType.METAL)
                .dynamicShape()
                .lightLevel((state) -> 15));
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite())
                .setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }

    @SuppressWarnings( "deprecation" )
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter plevel, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            default:
            case DOWN:
                return DOWN_AABB;
            case UP:
                return UP_AABB;
            case NORTH:
                return NORTH_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case WEST:
                return WEST_AABB;
            case EAST:
                return EAST_AABB;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LuxCapacitorBlockEntity(pos, state);
    }
}
