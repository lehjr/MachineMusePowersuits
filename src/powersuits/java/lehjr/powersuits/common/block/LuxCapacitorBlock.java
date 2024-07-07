package lehjr.powersuits.common.block;

import com.mojang.serialization.MapCodec;
import lehjr.numina.common.math.Color;
import lehjr.powersuits.common.blockentity.LuxCapacitorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LuxCapacitorBlock extends DirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final MapCodec<LuxCapacitorBlock> CODEC = simpleCodec(LuxCapacitorBlock::new);

    protected LuxCapacitorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends DirectionalBlock> codec() {
        return CODEC;
    }

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final Color defaultColor = new Color(0.4F, 0.2F, 0.9F);

    protected static final VoxelShape EAST_AABB = Block.box(0, 1, 1, 4, 15, 15);
    protected static final VoxelShape WEST_AABB = Block.box(12, 1, 1, 16, 15, 15);
    protected static final VoxelShape SOUTH_AABB = Block.box(1, 1, 0.0, 15, 15, 4);
    protected static final VoxelShape NORTH_AABB = Block.box(1, 1, 12, 15, 15, 16);
    protected static final VoxelShape UP_AABB = Block.box(1, 0.0, 1, 15, 4, 15);
    protected static final VoxelShape DOWN_AABB = Block.box(1, 12, 1, 15, 16.0, 15);

    public LuxCapacitorBlock() {
        super(Block.Properties.of()
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
                .setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
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

    private boolean canAttachTo(BlockGetter blockGetter, BlockPos pos, Direction direction) {
        BlockState blockstate = blockGetter.getBlockState(pos);
        return blockstate.isFaceSturdy(blockGetter, pos, direction);
    }
    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canAttachTo(levelReader, pos.relative(direction.getOpposite()), direction);
    }
}