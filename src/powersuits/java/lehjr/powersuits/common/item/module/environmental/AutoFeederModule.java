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

package lehjr.powersuits.common.item.module.environmental;

import com.google.common.collect.ImmutableList;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.TagUtils;
import lehjr.powersuits.common.config.module.EnvironmentalModuleConfig;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AutoFeederModule extends AbstractPowerModule {
    public static final String TAG_FOOD = "Food";
    public static final String TAG_SATURATION = "Saturation";
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public static float getFoodLevel(@Nonnull ItemStack stack) {
        return TagUtils.getModuleFloat(stack, TAG_FOOD);
    }

    public static void setFoodLevel(@Nonnull ItemStack stack, float saturation) {
        TagUtils.setModuleFloat(stack,TAG_FOOD, saturation);
    }

    public static float getSaturationLevel(@Nonnull ItemStack stack) {
        return TagUtils.getModuleFloat(stack, TAG_SATURATION);
    }

    public static void setSaturationLevel(@Nonnull ItemStack stack, float saturation) {
        TagUtils.setModuleFloat(stack, TAG_SATURATION, saturation);
    }

    public static class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module);

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.loadCapValues();
                return ticker;
            });
        }

        static class Ticker extends PlayerTickModule {
            ImmutableList useOldAutoFeederKey;
            public Ticker(ItemStack module) {
                super(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY);
                addBaseProperty(MPSConstants.ENERGY_CONSUMPTION, EnvironmentalModuleConfig.autoFeederModuleEnergyConsumptionBase);
                addBaseProperty(MPSConstants.AUTO_FEEDER_EFFICIENCY, EnvironmentalModuleConfig.autoFeederModuleEatingEfficiencyBase);
                addTradeoffProperty(MPSConstants.EFFICIENCY, MPSConstants.ENERGY_CONSUMPTION, EnvironmentalModuleConfig.magnetModuleEnergyConsumptionRadiusMultiplier, "FE");
                addTradeoffProperty(MPSConstants.EFFICIENCY, MPSConstants.AUTO_FEEDER_EFFICIENCY, EnvironmentalModuleConfig.autoFeederModuleEfficiencyEatingEfficiencyMultiplier);
            }

            @Override
            public int getTier() {
                return 1;
            }

            @Override
            public String getModuleGroup() {
                return "";
            }

            @Override
            public boolean isAllowed() {
                return EnvironmentalModuleConfig.autoFeederModuleIsAllowed;
            }

            @Override
            public boolean onPlayerTickActive(Player player, Level level, ItemStack modularItemStack, int moduleIndex) {
                float foodLevel = getFoodLevel(getModule());
                float saturationLevel = getSaturationLevel(getModule());
                Inventory inv = player.getInventory();
                double eatingEnergyConsumption = applyPropertyModifiers(MPSConstants.ENERGY_CONSUMPTION);
                double efficiency = applyPropertyModifiers(MPSConstants.AUTO_FEEDER_EFFICIENCY);

                FoodData foodStats = player.getFoodData();
                int foodNeeded = 20 - foodStats.getFoodLevel();
                float saturationNeeded = 20 - foodStats.getSaturationLevel();

                // this consumes all food in the player's inventory and stores the stats in a buffer
                if (EnvironmentalModuleConfig.autoFeederModuleUseOldAutoFeeder) {
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        ItemStack stack = inv.getItem(i);
                        FoodProperties foodProperties = stack.getFoodProperties(player);
                        if(foodProperties != null) {
                            for (int a = 0; a < stack.getCount(); a++) {
                                foodLevel += (float) (foodProperties.getNutrition() * efficiency) * 0.01F;
                                //  copied this from FoodStats.addStats()
                                saturationLevel += (float) (Math.min(foodProperties.getNutrition() * foodProperties.getSaturationModifier() * 2.0D, 20D) * efficiency * 0.01);
                            }
                            player.getInventory().setItem(i, ItemStack.EMPTY);
                        }
                    }
                    setFoodLevel(modularItemStack, foodLevel, level);
                    setSaturationLevel(modularItemStack, saturationLevel, level);
                } else {
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        if (foodNeeded < foodLevel)
                            break;
                        ItemStack stack = inv.getItem(i);
                        FoodProperties foodProperties = stack.getFoodProperties(player);
                        if (foodProperties != null) {
                            while (true) {
                                if (foodNeeded > foodLevel) {
                                    foodLevel += (float) (foodProperties.getNutrition() * efficiency) * 0.01F;
                                    //  copied this from FoodStats.addStats()
                                    saturationLevel += (float) (Math.min(foodProperties.getNutrition() * foodProperties.getSaturationModifier() * 2.0D, 20D) * efficiency * 0.01);
                                    stack.setCount(stack.getCount() - 1);
                                    if (stack.getCount() == 0) {
                                        player.getInventory().setItem(i, ItemStack.EMPTY);
                                        break;
                                    } else
                                        player.getInventory().setItem(i, stack);
                                } else
                                    break;
                            }
                        }
                    }
                    setFoodLevel(modularItemStack, foodLevel, level);
                    setSaturationLevel(modularItemStack, saturationLevel, level);
                }

                CompoundTag foodStatNBT = new CompoundTag();

                // only consume saturation if food is consumed. This keeps the food buffer from overloading with food while the
                //   saturation buffer drains completely.
                if (foodNeeded > 0 && getFoodLevel(modularItemStack) >= 1) {
                    int foodUsed = 0;
                    // if buffer has enough to fill player stat
                    if (getFoodLevel(modularItemStack) >= foodNeeded && foodNeeded * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                        foodUsed = foodNeeded;
                        // if buffer has some but not enough to fill the player stat
                    } else if ((foodNeeded - getFoodLevel(modularItemStack)) > 0 && getFoodLevel(modularItemStack) * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                        foodUsed = (int) getFoodLevel(modularItemStack);
                        // last resort where using just 1 unit from buffer
                    } else if (eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player) && getFoodLevel(modularItemStack) >= 1) {
                        foodUsed = 1;
                    }
                    if (foodUsed > 0) {
                        // populate the tag with the nbt data
                        foodStats.addAdditionalSaveData(foodStatNBT);
                        foodStatNBT.putInt("foodLevel", foodStatNBT.getInt("foodLevel") + foodUsed);
                        // put the values back into foodstats
                        foodStats.readAdditionalSaveData(foodStatNBT);
                        // update getValue stored in buffer
                        setFoodLevel(modularItemStack, getFoodLevel(modularItemStack) - foodUsed, level);
                        // split the cost between using food and using saturation
                        ElectricItemUtils.drainPlayerEnergy(player, (int) (eatingEnergyConsumption * 0.5 * foodUsed), false);

                        if (saturationNeeded >= 1.0D) {
                            // using int for better precision
                            int saturationUsed = 0;
                            // if buffer has enough to fill player stat
                            if (getSaturationLevel(modularItemStack) >= saturationNeeded && saturationNeeded * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                                saturationUsed = (int) saturationNeeded;
                                // if buffer has some but not enough to fill the player stat
                            } else if ((saturationNeeded - getSaturationLevel(modularItemStack)) > 0 && getSaturationLevel(modularItemStack) * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                                saturationUsed = (int) getSaturationLevel(modularItemStack);
                                // last resort where using just 1 unit from buffer
                            } else if (eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player) && getSaturationLevel(modularItemStack) >= 1) {
                                saturationUsed = 1;
                            }

                            if (saturationUsed > 0) {
                                // populate the tag with the nbt data
                                foodStats.addAdditionalSaveData(foodStatNBT);
                                foodStatNBT.putFloat("foodSaturationLevel", foodStatNBT.getFloat("foodSaturationLevel") + saturationUsed);
                                // put the values back into foodstats
                                foodStats.readAdditionalSaveData(foodStatNBT);
                                // update getValue stored in buffer
                                setSaturationLevel(modularItemStack, getSaturationLevel(modularItemStack) - saturationUsed, level);
                                // split the cost between using food and using saturation
                                ElectricItemUtils.drainPlayerEnergy(player, (int) (eatingEnergyConsumption * 0.5 * saturationUsed), false);
                            }
                        }
                    }
                }
                return false;
            }

            // these setters should be called server side only -------------------------------------------------------------
            public void setFoodLevel(ItemStack stack, float food, Level level) {
                if(!level.isClientSide()) {
                    IModularItem modularItem = NuminaCapabilities.getModularItem(stack);
                    if (modularItem != null) {
                        modularItem.setModuleFloat(ItemUtils.getRegistryName(getModule()), MPSConstants.TAG_FOOD, food);
                    }
                }
            }

            public void setSaturationLevel(ItemStack stack, float saturation, Level level) {
                if (!level.isClientSide()) {
                    IModularItem modularItem = NuminaCapabilities.getModularItem(stack);
                    if (modularItem != null) {
                        modularItem.setModuleFloat(ItemUtils.getRegistryName(getModule()), MPSConstants.TAG_SATURATION, saturation);
                    }
                }
            }
        }

        public static float getFoodLevel(ItemStack stack) {
            return TagUtils.getModuleFloat(stack, MPSConstants.TAG_FOOD);
        }

        public static float getSaturationLevel(ItemStack stack) {
            return TagUtils.getModuleFloat(stack, MPSConstants.TAG_SATURATION);
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