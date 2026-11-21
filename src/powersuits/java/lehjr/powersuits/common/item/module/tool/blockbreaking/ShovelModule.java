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
import lehjr.powersuits.common.config.module.ShovelModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;

public class ShovelModule extends AbstractPowerModule {

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public static class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final BlockBreaker blockBreaking;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(ItemStack module) {
            this.module = module;
            this.blockBreaking = new BlockBreaker(module, getTier(module));
            powerModuleHolder = LazyOptional.of(() -> blockBreaking);
        }

        public static class BlockBreaker extends ToggleableModule implements IBlockBreakingModule, IRightClickModule {
            int tier;

            public BlockBreaker(ItemStack module, int tier) {
                super(module, ModuleCategory.SHOVEL, ModuleTarget.TOOLONLY);
                this.tier = tier;

                switch(tier) {
                    case 1 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.stoneShovelModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.stoneShovelModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.stoneShovelModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.stoneShovelModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 2-> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.ironShovelModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.ironShovelModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.ironShovelModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.ironShovelModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 3-> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.diamondShovelModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.diamondShovelModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.diamondShovelModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.diamondShovelModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 4-> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.netheriteShovelModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.netheriteShovelModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, ShovelModuleConfig.netheriteShovelModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, ShovelModuleConfig.netheriteShovelModuleHarvestSpeedOverclockMultiplier);
                    }
                }
            }

            @Override
            public boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
                if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                    ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
                    return true;
                }
                return false;
            }

            @Override
            public int getTier() {
                return tier;
            }

            @Override
            public String getModuleGroup() {
                return "Shovel";
            }

            @Override
            public ItemStack getEmulatedTool() {
                return switch (tier) {
                    case 1 -> new ItemStack(Items.STONE_SHOVEL);
                    case 2 -> new ItemStack(Items.IRON_SHOVEL);
                    case 3 -> new ItemStack(Items.DIAMOND_SHOVEL);
                    case 4 -> new ItemStack(Items.NETHERITE_SHOVEL);
                    default -> new ItemStack(Items.STICK);
                };
            }

            @Override
            public boolean isAllowed() {
                return switch (tier) {
                    case 1 -> ShovelModuleConfig.stoneShovelModuleIsAllowed;
                    case 2 -> ShovelModuleConfig.ironShovelModuleIsAllowed;
                    case 3 -> ShovelModuleConfig.diamondShovelModuleIsAllowed;
                    case 4 -> ShovelModuleConfig.netheriteShovelModuleIsAllowed;
                    default -> false;
                };
            }

            @Override
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Override
            public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
                event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(NuminaConstants.HARVEST_SPEED)));
            }

            @Override
            public InteractionResult useOn(UseOnContext context) {
                double energyUsage = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION, 0); // Base energy usage without overclock since no overclock is used
                Player player = context.getPlayer();
                if(player == null) return InteractionResult.PASS;

                double playerEnergy = ElectricItemUtils.getPlayerEnergy(player);
                if(playerEnergy >= energyUsage) {
                    Level level = context.getLevel();
                    BlockPos blockpos = context.getClickedPos();
                    BlockState blockstate = level.getBlockState(blockpos);

                    if (!(context.getClickedFace() == Direction.DOWN)) {
                        BlockState blockstate1 = blockstate.getToolModifiedState(context, ToolActions.SHOVEL_FLATTEN, false);
                        BlockState blockstate2 = null;
                        if (blockstate1 != null && level.getBlockState(blockpos.above()).isAir()) {
                            level.playSound(player, blockpos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                            blockstate2 = blockstate1;
                        } else if (blockstate.getBlock() instanceof CampfireBlock && blockstate.getValue(CampfireBlock.LIT)) {
                                if (!level.isClientSide()) {
                                    level.levelEvent(null, 1009, blockpos, 0);
                                }

                                CampfireBlock.dowse(context.getPlayer(), level, blockpos, blockstate);
                                blockstate2 = blockstate.setValue(CampfireBlock.LIT, Boolean.valueOf(false));
                            }

                        if (blockstate2 != null) {
                            if (!level.isClientSide) {
                                level.setBlock(blockpos, blockstate2, 11);
                                level.gameEvent(GameEvent.BLOCK_CHANGE, blockpos, GameEvent.Context.of(player, blockstate2));
                                ElectricItemUtils.drainPlayerEnergy(player, energyUsage, false);
                            }
                            return InteractionResult.sidedSuccess(level.isClientSide);
                        }
                    }
                }
                return InteractionResult.PASS;
            }
        }

        static int getTier(ItemStack stack) {
            if(stack.is(MPSItems.STONE_SHOVEL_MODULE.get())) {
                return 1;
            }
            if(stack.is(MPSItems.IRON_SHOVEL_MODULE.get())) {
                return 2;
            }
            if(stack.is(MPSItems.DIAMOND_SHOVEL_MODULE.get())) {
                return 3;
            }
            if (stack.is(MPSItems.NETHERITE_PLATING_MODULE.get())) {
                return 4;
            }
            return 0;
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        public <T> LazyOptional<T> getCapability(final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> powerModuleCapability = NuminaCapabilities.Module.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (powerModuleCapability.isPresent()) {
                return powerModuleCapability;
            }
            return LazyOptional.empty();
        }
    }
}