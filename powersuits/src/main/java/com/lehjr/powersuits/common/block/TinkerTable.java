package com.lehjr.powersuits.common.block;

import com.lehjr.numina.client.sound.Musique;
import com.lehjr.numina.client.sound.SoundDictionary;
import com.lehjr.powersuits.client.gui.module.tweak.ModuleTweakGui;
import com.lehjr.powersuits.common.blockentity.TinkerTableBlockEntity;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.container.InstallSalvageMenu;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TinkerTable extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final MapCodec<TinkerTable> CODEC = simpleCodec(TinkerTable::new);

    protected TinkerTable(Block.Properties properties) {
        super(properties);
    }


//    protected static final VoxelShape TOP_SHAPE = Block.box(
//            0.0D, // West
//            14.0D, // down?
//            0.0D, // north
//            16.0D, // east
//            16.0D, // up?
//            16.0D); // South

    public TinkerTable() {
        super(Block.Properties.of()
                .strength(1.5F, 1000.0F)
                .sound(SoundType.WOOD)
                .dynamicShape()
                .lightLevel((state) -> 15));
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

//    @Override
//    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
//        if (level.isClientSide()) {
//            openGui(level);
//        }
//        return InteractionResult.CONSUME;
//    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(Objects.requireNonNull(state.getMenuProvider(level, pos)), buf -> {
                buf.writeEnum(EquipmentSlot.MAINHAND);
                buf.writeBoolean(false);
            } );
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        EquipmentSlot slotType = EquipmentSlot.MAINHAND;
        return new SimpleMenuProvider(
                (id, inventory, player) ->
                        new InstallSalvageMenu(id, inventory, slotType),
                Component.translatable(MPSConstants.GUI_INSTALL_SALVAGE));

    }


    @OnlyIn(Dist.CLIENT)
    public void openGui(Level world) {
        if (world.isClientSide) {
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(Component.translatable("gui.tinkertable"))));
        }
    }

    static final VoxelShape makeRotationZeroShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0.625, -0.0625, 1.0625, 0.8125, 1.0625), BooleanOp.OR); // middletable
        shape = Shapes.join(shape, Shapes.box(0.25, 0.5625, 0.0, 1.0, 0.875, 1.0), BooleanOp.OR); // uppertable
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.3125, 0.6875, 0.5625, 0.625), BooleanOp.OR); // particles
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0, 0.125, 0.875, 0.125, 0.8125), BooleanOp.OR); // footbase
        shape = Shapes.join(shape, Shapes.box(0.6875, -0.006250000000000089, 0.375, 0.9375, 0.1812499999999999, 0.5625), BooleanOp.OR); // foot1
        shape = Shapes.join(shape, Shapes.box(0.375, -0.006250000000000089, 0.0625, 0.625, 0.1812499999999999, 0.3125), BooleanOp.OR); // fatfoot2
        shape = Shapes.join(shape, Shapes.box(0.375, -0.006250000000000089, 0.625, 0.625, 0.1812499999999999, 0.875), BooleanOp.OR); // fatfoot1
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.25, 0.3125, 0.625, 0.6875), BooleanOp.OR); // backsupport
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.5625, 0.6875, 0.125, 0.875, 0.875), BooleanOp.OR); // tank3
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.5625, 0.375, 0.125, 0.875, 0.5625), BooleanOp.OR); // tank2
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.5625, 0.0625, 0.125, 0.875, 0.25), BooleanOp.OR); // tank1
        shape = Shapes.join(shape, Shapes.box(0.0, 0.4375, 0.4375, 0.0625, 0.5625, 0.5), BooleanOp.OR); // wireshort4
        shape = Shapes.join(shape, Shapes.box(0.0, 0.4375, 0.125, 0.0625, 0.5625, 0.1875), BooleanOp.OR); // wireshort3
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.375, 0.4375, 0.1875, 0.4375, 0.5), BooleanOp.OR); // wireshort2
        shape = Shapes.join(shape, Shapes.box(0.0, 0.4375, 0.75, 0.0625, 0.5625, 0.8125), BooleanOp.OR); // wireshort1
        shape = Shapes.join(shape, Shapes.box(0.0, 0.375, 0.125, 0.0625, 0.4375, 0.8125), BooleanOp.OR); // wirelong1
        return shape.optimize();
    }

    static final VoxelShape makeRotation90Shape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.625, 0.0, 1.0625, 0.8125, 1.0625), BooleanOp.OR);  // middletable
        shape = Shapes.join(shape, Shapes.box(0.0, 0.5625, 0.25, 1.0, 0.875, 1.0), BooleanOp.OR);  // uppertable
        shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.3125, 0.6875, 0.5625, 0.6875), BooleanOp.OR); // particles
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.0, 0.125, 0.875, 0.125, 0.875), BooleanOp.OR); // footbase
        shape = Shapes.join(shape, Shapes.box(0.4375, -0.006250000000000089, 0.6875, 0.625, 0.1812499999999999, 0.9375), BooleanOp.OR); // foot1
        shape = Shapes.join(shape, Shapes.box(0.6875, -0.006250000000000089, 0.375, 0.9375, 0.1812499999999999, 0.625), BooleanOp.OR); // fatfoot2
        shape = Shapes.join(shape, Shapes.box(0.125, -0.006250000000000089, 0.375, 0.375, 0.1812499999999999, 0.625), BooleanOp.OR); // fatfoot1
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.1875, 0.75, 0.625, 0.3125), BooleanOp.OR); // backsupport
        shape = Shapes.join(shape, Shapes.box(0.125, 0.5625, -0.0625, 0.3125, 0.875, 0.125), BooleanOp.OR); // tank3
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.5625, -0.0625, 0.625, 0.875, 0.125), BooleanOp.OR); // tank2
        shape = Shapes.join(shape, Shapes.box(0.75, 0.5625, -0.0625, 0.9375, 0.875, 0.125), BooleanOp.OR); // tank1
        shape = Shapes.join(shape, Shapes.box(0.5, 0.4375, 0.0, 0.5625, 0.5625, 0.0625), BooleanOp.OR); // wireshort4
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.4375, 0.0, 0.875, 0.5625, 0.0625), BooleanOp.OR); // wireshort3
        shape = Shapes.join(shape, Shapes.box(0.5, 0.375, 0.0625, 0.5625, 0.4375, 0.1875), BooleanOp.OR); // wireshort2
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.4375, 0.0, 0.25, 0.5625, 0.0625), BooleanOp.OR); // wireshort1
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.375, 0.0, 0.875, 0.4375, 0.0625), BooleanOp.OR); // wirelong1
        return shape.optimize();
    }

    static final VoxelShape makeRotation180Shape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.625, -0.0625, 1.0, 0.8125, 1.0625), BooleanOp.OR);  // middletable
        shape = Shapes.join(shape, Shapes.box(0.0, 0.5625, 0.0, 0.75, 0.875, 1.0), BooleanOp.OR);  // uppertable
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.375, 0.6875, 0.5625, 0.6875), BooleanOp.OR); // particles
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0, 0.1875, 0.875, 0.125, 0.875), BooleanOp.OR); // footbase
        shape = Shapes.join(shape, Shapes.box(0.0625, -0.006250000000000089, 0.4375, 0.3125, 0.1812499999999999, 0.625), BooleanOp.OR); // foot1
        shape = Shapes.join(shape, Shapes.box(0.375, -0.006250000000000089, 0.6875, 0.625, 0.1812499999999999, 0.9375), BooleanOp.OR); // fatfoot2
        shape = Shapes.join(shape, Shapes.box(0.375, -0.006250000000000089, 0.125, 0.625, 0.1812499999999999, 0.375), BooleanOp.OR); // fatfoot1
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.125, 0.3125, 0.8125, 0.625, 0.75), BooleanOp.OR); // backsupport
        shape = Shapes.join(shape, Shapes.box(0.875, 0.5625, 0.125, 1.0625, 0.875, 0.3125), BooleanOp.OR); // tank3
        shape = Shapes.join(shape, Shapes.box(0.875, 0.5625, 0.4375, 1.0625, 0.875, 0.625), BooleanOp.OR); // tank2
        shape = Shapes.join(shape, Shapes.box(0.875, 0.5625, 0.75, 1.0625, 0.875, 0.9375), BooleanOp.OR); // tank1
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.4375, 0.5, 1.0, 0.5625, 0.5625), BooleanOp.OR); // wireshort4
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.4375, 0.8125, 1.0, 0.5625, 0.875), BooleanOp.OR); // wireshort3
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.375, 0.5, 0.9375, 0.4375, 0.5625), BooleanOp.OR); // wireshort2
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.4375, 0.1875, 1.0, 0.5625, 0.25), BooleanOp.OR); // wireshort1
        shape = Shapes.join(shape, Shapes.box(0.9375, 0.375, 0.1875, 1.0, 0.4375, 0.875), BooleanOp.OR); // wirelong1
        return shape.optimize();
    }

    static final VoxelShape makeRotation270Shape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.625, -0.0625, 1.0625, 0.8125, 1.0), BooleanOp.OR); // middletable
        shape = Shapes.join(shape, Shapes.box(0.0, 0.5625, 0.0, 1.0, 0.875, 0.75), BooleanOp.OR); // uppertable
        shape = Shapes.join(shape, Shapes.box(0.3125, 0.125, 0.3125, 0.625, 0.5625, 0.6875), BooleanOp.OR); // particles
        shape = Shapes.join(shape, Shapes.box(0.125, 0.0, 0.125, 0.8125, 0.125, 0.875), BooleanOp.OR); // footbase
        shape = Shapes.join(shape, Shapes.box(0.375, -0.006250000000000089, 0.0625, 0.5625, 0.1812499999999999, 0.3125), BooleanOp.OR); // foot1
        shape = Shapes.join(shape, Shapes.box(0.0625, -0.006250000000000089, 0.375, 0.3125, 0.1812499999999999, 0.625), BooleanOp.OR); // fatfoot2
        shape = Shapes.join(shape, Shapes.box(0.625, -0.006250000000000089, 0.375, 0.875, 0.1812499999999999, 0.625), BooleanOp.OR); // fatfoot1
        shape = Shapes.join(shape, Shapes.box(0.25, 0.125, 0.6875, 0.6875, 0.625, 0.8125), BooleanOp.OR); // backsupport
        shape = Shapes.join(shape, Shapes.box(0.6875, 0.5625, 0.875, 0.875, 0.875, 1.0625), BooleanOp.OR); // tank3
        shape = Shapes.join(shape, Shapes.box(0.375, 0.5625, 0.875, 0.5625, 0.875, 1.0625), BooleanOp.OR); // tank2
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.5625, 0.875, 0.25, 0.875, 1.0625), BooleanOp.OR); // tank1
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.4375, 0.9375, 0.5, 0.5625, 1.0), BooleanOp.OR); // wireshort4
        shape = Shapes.join(shape, Shapes.box(0.125, 0.4375, 0.9375, 0.1875, 0.5625, 1.0), BooleanOp.OR); // wireshort3
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.375, 0.8125, 0.5, 0.4375, 0.9375), BooleanOp.OR); // wireshort2
        shape = Shapes.join(shape, Shapes.box(0.75, 0.4375, 0.9375, 0.8125, 0.5625, 1.0), BooleanOp.OR); // wireshort1
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.9375, 0.8125, 0.4375, 1.0), BooleanOp.OR); // wirelong1
        return shape.optimize();
    }

    public static final VoxelShape SHAPE_ROTATION0 = makeRotationZeroShape();
    public static final VoxelShape SHAPE_ROTATION90 = makeRotation90Shape();
    public static final VoxelShape SHAPE_ROTATION180 = makeRotation180Shape();
    public static final VoxelShape SHAPE_ROTATION270 = makeRotation270Shape();


    @SuppressWarnings( "deprecation" )
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(state.hasProperty(FACING)) {
            switch(state.getValue(FACING)) {
                case NORTH:
                    return SHAPE_ROTATION270;

                case SOUTH:
                    return SHAPE_ROTATION90;

                case WEST:
                    return SHAPE_ROTATION180;
            }
        }
        return SHAPE_ROTATION0;
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        final FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());

        return defaultBlockState()
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER)
                .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TinkerTableBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}