/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.powersuits.common.item.module.tool.blockbreaking;

import lehjr.numina.common.capabilities.module.blockbreaking.IBlockBreakingModule;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.rightclick.IRightClickModule;
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.imixin.common.item.IUseOnContextMixn;
import lehjr.powersuits.common.config.module.AxeModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;


public class AxeModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final BlockBreaker blockBreaking;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;


            this.blockBreaking = new BlockBreaker(module, getTier(module));
            powerModuleHolder = LazyOptional.of(() -> {
                blockBreaking.loadCapValues();
                return blockBreaking;
            });
        }

        public static class BlockBreaker extends ToggleableModule implements IBlockBreakingModule, IRightClickModule {
            int tier;
            public BlockBreaker(@Nonnull ItemStack module, int tier) {
                super(module, ModuleCategory.AXE, ModuleTarget.TOOLONLY);
                this.tier = tier;

                switch(tier) {
                    case 1 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.stoneAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, AxeModuleConfig.stoneAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.stoneAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, AxeModuleConfig.stoneAxeModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 2 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.ironAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, AxeModuleConfig.ironAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.ironAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, AxeModuleConfig.ironAxeModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 3 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.diamondAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, AxeModuleConfig.diamondAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.diamondAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, AxeModuleConfig.diamondAxeModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 4 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.netheriteAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, AxeModuleConfig.netheriteAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, AxeModuleConfig.netheriteAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, AxeModuleConfig.netheriteAxeModuleHarvestSpeedOverclockMultiplier);
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
                Optional<BlockState> optional = Optional.ofNullable(pState.getToolModifiedState(context, ToolActions.AXE_STRIP, false));
                if (optional.isPresent()) {
                    pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    return optional;
                } else {
                    Optional<BlockState> optional1 = Optional.ofNullable(pState.getToolModifiedState(context, ToolActions.AXE_SCRAPE, false));
                    if (optional1.isPresent()) {
                        pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
                        pLevel.levelEvent(pPlayer, 3005, pPos, 0);
                        return optional1;
                    } else {
                        Optional<BlockState> optional2 = Optional.ofNullable(pState.getToolModifiedState(context, ToolActions.AXE_WAX_OFF, false));
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
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Override
            public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
                event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(NuminaConstants.HARVEST_SPEED)));
            }
        }

        static int getTier(ItemStack stack) {
            if(stack.is(MPSItems.STONE_AXE_MODULE.get())) {
                return 1;
            }
            if(stack.is(MPSItems.IRON_AXE_MODULE.get())) {
                return 2;
            }
            if(stack.is(MPSItems.DIAMOND_AXE_MODULE.get())) {
                return 3;
            }
            if (stack.is(MPSItems.NETHERITE_AXE_MODULE.get())) {
                return 4;
            }
            return 0;
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}