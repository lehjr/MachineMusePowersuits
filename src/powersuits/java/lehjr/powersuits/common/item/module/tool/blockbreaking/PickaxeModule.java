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
import lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.powersuits.common.config.module.PickaxeModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import lehjr.powersuits.common.registration.MPSItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class PickaxeModule extends AbstractPowerModule {

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

        public static class BlockBreaker extends ToggleableModule implements IBlockBreakingModule {
            int tier;

            public BlockBreaker(ItemStack module, int tier) {
                super(module, ModuleCategory.PICKAXE, ModuleTarget.TOOLONLY);
                this.tier = tier;
                //            NuminaLogger.logDebug("module: " + module + ", tier: " + tier + ", isAllowed: " + isAllowed());

                switch(tier) {
                    case 1 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.stonePickAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.stonePickAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.stonePickAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.stonePickAxeModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 2 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.ironPickAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.ironPickAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.ironPickAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.ironPickAxeModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 3 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.diamondPickAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.diamondPickAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.diamondPickAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.diamondPickAxeModuleHarvestSpeedOverclockMultiplier);
                    }
                    case 4 -> {
                        addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.netheritePickAxeModuleEnergyConsumptionBase, "FE");
                        addBaseProperty(NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.netheritePickAxeModuleHarvestSpeedBase, "x");
                        addTradeoffProperty(MPSConstants.OVERCLOCK, MPSConstants.ENERGY_CONSUMPTION, PickaxeModuleConfig.netheritePickAxeModuleEnergyConsumptionOverclockMultiplier);
                        addTradeoffProperty(MPSConstants.OVERCLOCK, NuminaConstants.HARVEST_SPEED, PickaxeModuleConfig.netheritePickAxeModuleHarvestSpeedOverclockMultiplier);
                    }
                }
            }

            @Override
            public boolean mineBlock(ItemStack powerFist, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving, double playerEnergy) {
//            NuminaLogger.logDebug("energy usage: " + getEnergyUsage() +", player energy: " + playerEnergy);

                if (this.canHarvestBlock(powerFist, state, (Player) entityLiving, pos, playerEnergy)) {
                    ElectricItemUtils.drainPlayerEnergy(entityLiving, getEnergyUsage(), false);
//                NuminaLogger.logDebug("returning true");
                    return true;
                }
//            NuminaLogger.logDebug("returning false");
                return false;
            }

            @Override
            public void handleBreakSpeed(PlayerEvent.BreakSpeed event) {
                event.setNewSpeed((float) (event.getNewSpeed() * applyPropertyModifiers(NuminaConstants.HARVEST_SPEED)));
            }

            @Override
            public double getEnergyUsage() {
                return applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
            }

            @Override
            public ItemStack getEmulatedTool() {
                return switch (tier) {
                    case 1 -> new ItemStack(Items.STONE_PICKAXE);
                    case 2 -> new ItemStack(Items.IRON_PICKAXE);
                    case 3 -> new ItemStack(Items.DIAMOND_PICKAXE);
                    case 4 -> new ItemStack(Items.NETHERITE_PICKAXE);
                    default -> new ItemStack(Items.STICK);
                };
            }

            @Override
            public boolean isAllowed() {
                return switch (tier) {
                    case 1 -> PickaxeModuleConfig.stonePickAxeModuleIsAllowed;
                    case 2 -> PickaxeModuleConfig.ironPickAxeModuleIsAllowed;
                    case 3 -> PickaxeModuleConfig.diamondPickAxeModuleIsAllowed;
                    case 4 -> PickaxeModuleConfig.netheritePickAxeModuleIsAllowed;
                    default -> false;
                };
            }

            @Override
            public String getModuleGroup() {
                return "Pickaxe";
            }

            @Override
            public int getTier() {
                return this.tier;
            }
        }

        static int getTier(ItemStack stack) {
            if(stack.is(MPSItems.STONE_PICKAXE_MODULE.get())) {
                return 1;
            }
            if(stack.is(MPSItems.IRON_PICKAXE_MODULE.get())) {
                return 2;
            }
            if(stack.is(MPSItems.DIAMOND_PICK_UPGRADE_MODULE_LEGACY.get())) {
                return 3;
            }
            if (stack.is(MPSItems.NETHERITE_PICKAXE_MODULE.get())) {
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