package com.lehjr.powersuits.common.item.module.tool.blockbreaking.farming;

import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RototillerModule extends AbstractPowerModule {
    //    protected static final Map<Block, BlockState> HOE_LOOKUP = Maps.newHashMap(ImmutableMap.of(Blocks.field_196658_i, Blocks.field_150458_ak.defaultBlockState(), Blocks.field_185774_da, Blocks.field_150458_ak.defaultBlockState(), Blocks.field_150346_d, Blocks.field_150458_ak.defaultBlockState(), Blocks.field_196660_k, Blocks.field_150346_d.defaultBlockState()));
    public static class RightClickie extends RightClickModule implements IBlockBreakingModule {
        public RightClickie(@Nonnull ItemStack module) {
            super(module, ModuleCategory.HOE, ModuleTarget.TOOLONLY);

                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, 500, "FE");
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, 9500);
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS,8,  "m", 1, 0);

                addBaseProperty(MPSConstants.HARVEST_SPEED, 8, "x");
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, 9500);
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, 22);
        }

        @Override
        public InteractionResult useOn(UseOnContext context) {
            int energyConsumed = this.getEnergyUsage();
            Player player = context.getPlayer();
            Level level = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            Direction facing = context.getClickedFace();
            ItemStack itemStack = context.getItemInHand();
            BlockState toolModifiedState1;
            InteractionResult ret = InteractionResult.PASS;
            if(player == null) {
                return ret;
            }
            if (!player.mayUseItemAt(blockPos, facing, itemStack) || ElectricItemUtils.getPlayerEnergy(player) < energyConsumed) {
                return InteractionResult.PASS;
            } else {
                int radius = (int)applyPropertyModifiers(MPSConstants.RADIUS);
                for (int i = Math.abs(radius); i < radius; i++) {
                    for (int j = Math.abs(radius); j < radius; j++) {
                        if (i * i + j * j < radius * radius) {
                            BlockPos newPos = blockPos.offset(i, 0, j);
                            if (level.getBlockState(newPos.above()).isAir() && !level.getBlockState(newPos).isAir()) {
                                BlockHitResult hitResult = new BlockHitResult(Vec3.atCenterOf(newPos), Direction.DOWN, newPos, true);
                                UseOnContext ctx1 = new UseOnContext(level, player, context.getHand(), getEmulatedTool(), hitResult);
                                toolModifiedState1 = level.getBlockState(newPos).getToolModifiedState(ctx1, ItemAbilities.HOE_TILL, false);
                                Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair1 = toolModifiedState1 == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState1, newPos, player));
                                if (pair1 != null) {
                                    Predicate<UseOnContext> predicate1 = pair1.getFirst();
                                    Consumer<UseOnContext> consumer1 = pair1.getSecond();
                                    if (predicate1.test(ctx1)) {
                                        level.playSound(player, blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                                        if (!level.isClientSide) {
                                            consumer1.accept(context);
                                            ElectricItemUtils.drainPlayerEnergy(player, energyConsumed, false);
                                        }
                                        if(ret == InteractionResult.FAIL || ret == InteractionResult.PASS) {
                                            ret = InteractionResult.sidedSuccess(level.isClientSide);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return ret;
        }

        public static Consumer<UseOnContext> changeIntoState(BlockState pState, BlockPos pos, Player player) {
            return (context) -> {
                context.getLevel().setBlock(pos, pState, 11);
                context.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, pState));
            };
        }

        @Override
        public boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
                return true;
            }
            return false;
        }

        @Override
        public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED)));
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Nonnull
        @Override
        public ItemStack getEmulatedTool() {
            return new ItemStack(Items.IRON_HOE);
        }
    }
}
