package lehjr.numina.common.block;

import lehjr.numina.common.base.NuminaObjects;
import lehjr.numina.common.blockentity.ChargingBaseBlockEntity;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.container.ChargingBaseMenu;
import lehjr.numina.common.entity.NuminaArmorStand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChargingBase extends Block implements EntityBlock, SimpleWaterloggedBlock {
    private static final Component title = Component.translatable("gui.numina.chargingbase");

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape BASE_SHAPE = Block.box(
            0.0D, // West
            0.0D, // down?
            0.0D, // north
            16.0D, // east
            1.0D, // up?
            16.0D);

    public ChargingBase() {
        super(Block.Properties.of()
                .strength(0.5F, 4.0F)
                .sound(SoundType.ANVIL)
                .requiresCorrectToolForDrops());
        registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.POWERED, false));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable(NuminaConstants.TOOLTIP_CHARGING_BASE, Integer.toString(1000)));
    }

    // temporary fix for armor stand spawned below the block
    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!worldIn.isClientSide && entityIn instanceof NuminaArmorStand && pos.getY() > (int)entityIn.position().y) {
            entityIn.teleportTo(pos.getX() + 0.5, entityIn.position().y + 1, pos.getZ() + 0.5);
        }
        super.entityInside(state, worldIn, pos, entityIn);
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(BlockStateProperties.POWERED) ? super.getLightEmission(state, level, pos) : 0;
    }

    @Override
    public boolean hasDynamicShape() {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BASE_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, BlockStateProperties.POWERED);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider containerProvider =
                    new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return title;
                        }

                        @Nullable
                        @Override
                        public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player pPlayer) {
                            return new ChargingBaseMenu(containerId, player, pos);
                        }
                    };
            player.openMenu(containerProvider, buf -> buf.writeBlockPos(pos));
            return InteractionResult.CONSUME;
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHitResult) {
        // FIXME!!!
        return super.useItemOn(stack, state, level, pos, player, hand, pHitResult);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(BlockStateProperties.POWERED, false) // fixme: should indicate if item placed has stored power
                .setValue(WATERLOGGED, Boolean.valueOf(ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChargingBaseBlockEntity(pos, state);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
//        return type == NuminaObjects.CHARGING_BASE_BLOCK_ENTITY.get() ? ChargingBaseBlockEntity::tick : null;

        if (level.isClientSide) {
            return null;
        } else {
            return (lvl, pos, st, be) -> {
                if (be instanceof ChargingBaseBlockEntity generator) {
                    generator.tickServer();
                }
            };
        }


    }
}
