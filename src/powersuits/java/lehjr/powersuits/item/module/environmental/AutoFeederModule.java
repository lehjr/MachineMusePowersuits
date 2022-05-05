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

package lehjr.powersuits.item.module.environmental;

import lehjr.numina.util.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.ModuleTarget;
import lehjr.numina.util.capabilities.module.powermodule.IConfig;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import lehjr.numina.util.energy.ElectricItemUtils;
import lehjr.numina.util.nbt.MuseNBTUtils;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSConstants;
import lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.FoodStats;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class AutoFeederModule extends AbstractPowerModule {
    public static final String TAG_FOOD = "Food";
    public static final String TAG_SATURATION = "Saturation";

    public AutoFeederModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public static float getFoodLevel(@Nonnull ItemStack stack) {
        return MuseNBTUtils.getModuleFloatOrZero(stack, TAG_FOOD);
    }

    public static void setFoodLevel(@Nonnull ItemStack stack, float saturation) {
        MuseNBTUtils.setModuleFloatOrRemove(stack,TAG_FOOD, saturation);
    }

    public static float getSaturationLevel(@Nonnull ItemStack stack) {
        return MuseNBTUtils.getModuleFloatOrZero(stack, TAG_SATURATION);
    }

    public static void setSaturationLevel(@Nonnull ItemStack stack, float saturation) {
        MuseNBTUtils.setModuleFloatOrRemove(stack, TAG_SATURATION, saturation);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.HEADONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.AUTO_FEEDER_ENERGY, 100);
                addBaseProperty(MPSConstants.EATING_EFFICIENCY, 50);
                addTradeoffProperty(MPSConstants.EFFICIENCY, MPSConstants.AUTO_FEEDER_ENERGY, 1000, "FE");
                addTradeoffProperty(MPSConstants.EFFICIENCY, MPSConstants.EATING_EFFICIENCY, 50);
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IToggleableModule) {
                ((IToggleableModule) cap).updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, ItemStack itemX) {
                float foodLevel = getFoodLevel(module);
                float saturationLevel = getSaturationLevel(module);
                IInventory inv = player.inventory;
                double eatingEnergyConsumption = applyPropertyModifiers(MPSConstants.AUTO_FEEDER_ENERGY);
                double efficiency = applyPropertyModifiers(MPSConstants.EATING_EFFICIENCY);

                FoodStats foodStats = player.getFoodData();
                int foodNeeded = 20 - foodStats.getFoodLevel();
                float saturationNeeded = 20 - foodStats.getSaturationLevel();

                // this consumes all food in the player's inventory and stores the stats in a buffer
//        if (MPSSettings::getModuleConfig.useOldAutoFeeder()) { // FIXME!!!!!
                if (true) {

                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        ItemStack stack = inv.getItem(i);
                        if (stack.isEdible()) {
                            for (int a = 0; a < stack.getCount(); a++) {
                                foodLevel += stack.getItem().getFoodProperties().getNutrition() * efficiency / 100.0F;
                                //  copied this from FoodStats.addStats()
                                saturationLevel += Math.min(stack.getItem().getFoodProperties().getNutrition() * stack.getItem().getFoodProperties().getSaturationModifier() * 2.0F, 20F) * efficiency / 100.0;
                            }
                            player.inventory.setItem(i, ItemStack.EMPTY);
                        }
                    }
                    setFoodLevel(module, foodLevel);
                    setSaturationLevel(module, saturationLevel);
                } else {
                    for (int i = 0; i < inv.getContainerSize(); i++) {
                        if (foodNeeded < foodLevel)
                            break;
                        ItemStack stack = inv.getItem(i);
                        if (stack.isEdible()) {
                            while (true) {
                                if (foodNeeded > foodLevel) {
                                    foodLevel += stack.getItem().getFoodProperties().getNutrition() * efficiency / 100.0;
                                    //  copied this from FoodStats.addStats()
                                    saturationLevel += Math.min(stack.getItem().getFoodProperties().getNutrition() * stack.getItem().getFoodProperties().getSaturationModifier() * 2.0D, 20D) * efficiency / 100.0;
                                    stack.setCount(stack.getCount() - 1);
                                    if (stack.getCount() == 0) {
                                        player.inventory.setItem(i, ItemStack.EMPTY);
                                        break;
                                    } else
                                        player.inventory.setItem(i, stack);
                                } else
                                    break;
                            }
                        }
                    }
                    setFoodLevel(module, foodLevel);
                    setSaturationLevel(module, saturationLevel);
                }

                CompoundNBT foodStatNBT = new CompoundNBT();

                // only consume saturation if food is consumed. This keeps the food buffer from overloading with food while the
                //   saturation buffer drains completely.
                if (foodNeeded > 0 && getFoodLevel(module) >= 1) {
                    int foodUsed = 0;
                    // if buffer has enough to fill player stat
                    if (getFoodLevel(module) >= foodNeeded && foodNeeded * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                        foodUsed = foodNeeded;
                        // if buffer has some but not enough to fill the player stat
                    } else if ((foodNeeded - getFoodLevel(module)) > 0 && getFoodLevel(module) * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                        foodUsed = (int) getFoodLevel(module);
                        // last resort where using just 1 unit from buffer
                    } else if (eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player) && getFoodLevel(module) >= 1) {
                        foodUsed = 1;
                    }
                    if (foodUsed > 0) {
                        // populate the tag with the nbt data
                        foodStats.addAdditionalSaveData(foodStatNBT);
                        foodStatNBT.putInt("foodLevel", foodStatNBT.getInt("foodLevel") + foodUsed);
                        // put the values back into foodstats
                        foodStats.readAdditionalSaveData(foodStatNBT);
                        // update getValue stored in buffer
                        setFoodLevel(module, getFoodLevel(module) - foodUsed);
                        // split the cost between using food and using saturation
                        ElectricItemUtils.drainPlayerEnergy(player, (int) (eatingEnergyConsumption * 0.5 * foodUsed));

                        if (saturationNeeded >= 1.0D) {
                            // using int for better precision
                            int saturationUsed = 0;
                            // if buffer has enough to fill player stat
                            if (getSaturationLevel(module) >= saturationNeeded && saturationNeeded * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                                saturationUsed = (int) saturationNeeded;
                                // if buffer has some but not enough to fill the player stat
                            } else if ((saturationNeeded - getSaturationLevel(module)) > 0 && getSaturationLevel(module) * eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player)) {
                                saturationUsed = (int) getSaturationLevel(module);
                                // last resort where using just 1 unit from buffer
                            } else if (eatingEnergyConsumption * 0.5 < ElectricItemUtils.getPlayerEnergy(player) && getSaturationLevel(module) >= 1) {
                                saturationUsed = 1;
                            }

                            if (saturationUsed > 0) {
                                // populate the tag with the nbt data
                                foodStats.addAdditionalSaveData(foodStatNBT);
                                foodStatNBT.putFloat("foodSaturationLevel", foodStatNBT.getFloat("foodSaturationLevel") + saturationUsed);
                                // put the values back into foodstats
                                foodStats.readAdditionalSaveData(foodStatNBT);
                                // update getValue stored in buffer
                                setSaturationLevel(module, getSaturationLevel(module) - saturationUsed);
                                // split the cost between using food and using saturation
                                ElectricItemUtils.drainPlayerEnergy(player, (int) (eatingEnergyConsumption * 0.5 * saturationUsed));
                            }
                        }
                    }
                }
            }
        }
    }
}