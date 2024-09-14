package com.lehjr.powersuits.common.item.module.tool.blockbreaking;

import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.rightclick.RightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.powersuits.common.config.module.HoeModuleConfig;
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
        int tier;
        public RightClickie(@Nonnull ItemStack module, int tier) {
            super(module, ModuleCategory.HOE, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch (tier) {
            case 1:{
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.stoneRototillerModuleEnergyConsumptionBase, "FE"); // 500
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.stoneRototillerModuleEnergyConsumptionRadiusMultiplier); // 9500
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, HoeModuleConfig.stoneRototillerModuleRadiusMultiplier,  "m", 1, 0); // 4
                addBaseProperty(MPSConstants.HARVEST_SPEED, HoeModuleConfig.stoneRototillerModuleHarvestSpeedBase, "x"); // 4, 8 , who knows :P
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.stoneRototillerModuleEnergyConsumptionOverclockMultiplier); // 9500
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, HoeModuleConfig.stoneRototillerModuleHarvestSpeedOverclockMultiplier); // 22
                break;
            }

            case 2: {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.ironRototillerModuleEnergyConsumptionBase, "FE"); // 500
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.ironRototillerModuleEnergyConsumptionRadiusMultiplier); // 9500
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, HoeModuleConfig.ironRototillerModuleRadiusMultiplier,  "m", 1, 0); // 4
                addBaseProperty(MPSConstants.HARVEST_SPEED, HoeModuleConfig.ironRototillerModuleHarvestSpeedBase, "x"); // 4, 8 , who knows :P
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.ironRototillerModuleEnergyConsumptionOverclockMultiplier); // 9500
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, HoeModuleConfig.ironRototillerModuleHarvestSpeedOverclockMultiplier); // 22
                break;
            }

            case 3: {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.diamondRototillerModuleEnergyConsumptionBase, "FE"); // 500
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.diamondRototillerModuleEnergyConsumptionRadiusMultiplier); // 9500
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, HoeModuleConfig.diamondRototillerModuleRadiusMultiplier,  "m", 1, 0); // 4
                addBaseProperty(MPSConstants.HARVEST_SPEED, HoeModuleConfig.diamondRototillerModuleHarvestSpeedBase, "x"); // 4, 8 , who knows :P
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.diamondRototillerModuleEnergyConsumptionOverclockMultiplier); // 9500
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, HoeModuleConfig.diamondRototillerModuleHarvestSpeedOverclockMultiplier); // 22
                break;
            }

            case 4: {
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.netheriteRototillerModuleEnergyConsumptionBase, "FE"); // 500
                addTradeoffProperty(MPSConstants.RADIUS, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.netheriteRototillerModuleEnergyConsumptionRadiusMultiplier); // 9500
                addIntTradeoffProperty(MPSConstants.RADIUS, MPSConstants.RADIUS, HoeModuleConfig.netheriteRototillerModuleRadiusMultiplier,  "m", 1, 0); // 4
                addBaseProperty(MPSConstants.HARVEST_SPEED, HoeModuleConfig.netheriteRototillerModuleHarvestSpeedBase, "x"); // 4, 8 , who knows :P
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, HoeModuleConfig.netheriteRototillerModuleEnergyConsumptionOverclockMultiplier); // 9500
                addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, HoeModuleConfig.netheriteRototillerModuleHarvestSpeedOverclockMultiplier); // 22
                break;
            }
            }
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
                for (int i = -Math.abs(radius); i < radius; i++) {
                    for (int j = -Math.abs(radius); j < radius; j++) {
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

        public static Consumer<UseOnContext> changeIntoState(BlockState state, BlockPos pos, Player player) {
            return (context) -> {
                context.getLevel().setBlock(pos, state, 11);
                context.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state));
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

        @Override
        public boolean isAllowed() {
            return switch (tier) {
                case 1 -> HoeModuleConfig.stoneRototillerModuleIsAllowed;
                case 2 -> HoeModuleConfig.ironRototillerModuleIsAllowed;
                case 3 -> HoeModuleConfig.diamondRototillerModuleIsAllowed;
                case 4 -> HoeModuleConfig.netheriteRototillerModuleIsAllowed;
                default -> false;
            };
        }

        @Nonnull
        @Override
        public ItemStack getEmulatedTool() {
            return switch (tier) {
                case 1 -> new ItemStack(Items.STONE_HOE);
                case 2 -> new ItemStack(Items.IRON_HOE);
                case 3 -> new ItemStack(Items.DIAMOND_HOE);
                case 4 -> new ItemStack(Items.NETHERITE_HOE);
                default -> new ItemStack(Items.STICK);
            };
        }
    }
}
