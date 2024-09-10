package com.lehjr.powersuits.common.item.module.tool.blockbreaking;

import com.lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.powermodule.PowerModule;
import com.lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.imixin.common.item.IUseOnContextMixn;
import com.lehjr.powersuits.common.config.module.tool.blockbreaking.AxeModuleConfig;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;


public class AxeModule extends AbstractPowerModule {

    public static class BlockBreaker extends PowerModule implements IBlockBreakingModule, IRightClickModule {
        int tier;
        public BlockBreaker(@Nonnull ItemStack module, int tier) {
            super(module, ModuleCategory.AXE, ModuleTarget.TOOLONLY);
            this.tier = tier;

            switch(tier) {
                case 1 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.stoneAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, AxeModuleConfig.stoneAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.stoneAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, AxeModuleConfig.stoneAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 2 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.ironAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, AxeModuleConfig.ironAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.ironAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, AxeModuleConfig.ironAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 3 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.diamondAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, AxeModuleConfig.diamondAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.diamondAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, AxeModuleConfig.diamondAxeModuleHarvestSpeedOverclockMultiplier);
                }
                case 4 -> {
                    addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.netheriteAxeModuleEnergyConsumptionBase, "FE");
                    addBaseProperty(MPSConstants.HARVEST_SPEED, AxeModuleConfig.netheriteAxeModuleHarvestSpeedBase, "x");
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.netheriteAxeModuleEnergyConsumptionOverclockMultiplier);
                    addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.HARVEST_SPEED, AxeModuleConfig.netheriteAxeModuleHarvestSpeedOverclockMultiplier);
                }
            }
        }

        @Override
        public InteractionResult useOn(UseOnContext context) {
            Level level = context.getLevel();
            BlockPos blockpos = context.getClickedPos();
            Player player = context.getPlayer();
            if (player == null) {
                return InteractionResult.PASS;
            }

            double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
            if(playerEnergy < getEnergyUsage()) {
                return InteractionResult.PASS;
            }

            UseOnContext alteredContext = new UseOnContext(context.getLevel(), context.getPlayer(), context.getHand(), getEmulatedTool(), ((IUseOnContextMixn)context).machineMusePowersuits$getBlockHitResult());
            if(getEmulatedTool().getItem() instanceof AxeItem) {
                Optional<BlockState> optional = evaluateNewBlockState(level, blockpos, player, level.getBlockState(blockpos), alteredContext);
                if (optional.isEmpty()) {
                    return InteractionResult.PASS;
                } else {
                    ItemStack itemstack = getEmulatedTool();
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                    }

                    level.setBlock(blockpos, optional.get(), 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, optional.get()));
                    ElectricItemUtils.drainPlayerEnergy(player, getEnergyUsage(), false);
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
            return InteractionResult.PASS;
        }

        private Optional<BlockState> evaluateNewBlockState(Level pLevel, BlockPos pPos, @Nullable Player pPlayer, BlockState pState, UseOnContext context) {
            Optional<BlockState> optional = Optional.ofNullable(pState.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false));
            if (optional.isPresent()) {
                pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                return optional;
            } else {
                Optional<BlockState> optional1 = Optional.ofNullable(pState.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false));
                if (optional1.isPresent()) {
                    pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    pLevel.levelEvent(pPlayer, 3005, pPos, 0);
                    return optional1;
                } else {
                    Optional<BlockState> optional2 = Optional.ofNullable(pState.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false));
                    if (optional2.isPresent()) {
                        pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
                        pLevel.levelEvent(pPlayer, 3004, pPos, 0);
                        return optional2;
                    } else {
                        return Optional.empty();
                    }
                }
            }
        }


        @Override
        public boolean mineBlock(@Nonnull ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
            if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                ElectricItemUtils.drainPlayerEnergy((Player) entityLiving, getEnergyUsage(), false);
                return true;
            }
            return false;
        }

        @Nonnull
        @Override
        public ItemStack getEmulatedTool() {
            return switch (tier) {
                case 1 -> new ItemStack(Items.STONE_AXE);
                case 2 -> new ItemStack(Items.IRON_AXE);
                case 3 -> new ItemStack(Items.DIAMOND_AXE);
                case 4 -> new ItemStack(Items.NETHERITE_AXE);
                default -> new ItemStack(Items.STICK);
            };
        }

        @Override
        public boolean isAllowed() {
            return switch (tier) {
                case 1 -> AxeModuleConfig.stoneAxeModuleIsAllowed;
                case 2 -> AxeModuleConfig.ironAxeModuleIsAllowed;
                case 3 -> AxeModuleConfig.diamondAxeModuleIsAllowed;
                case 4 -> AxeModuleConfig.netheriteAxeModuleIsAllowed;
                default -> false;
            };
        }

        @Override
        public int getTier() {
            return tier;
        }

        @Override
        public String getModuleGroup() {
            return "Axe";
        }

        @Override
        public int getEnergyUsage() {
            return (int) applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
        }

        @Override
        public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
            event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(MPSConstants.HARVEST_SPEED)));
        }
    }
}
