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

package com.github.lehjr.powersuits.item.module.movement;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.event.MovementManager;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Ported by leon on 10/18/16.
 */
public class SprintAssistModule extends AbstractPowerModule {
    public SprintAssistModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.MOVEMENT, EnumModuleTarget.LEGSONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.SPRINT_ENERGY_CONSUMPTION, 0, "FE");
                addTradeoffProperty(MPSConstants.SPRINT_ASSIST, MPSConstants.SPRINT_ENERGY_CONSUMPTION, 100);
                addBaseProperty(MPSConstants.SPRINT_SPEED_MULTIPLIER, .01F, "%");
                addTradeoffProperty(MPSConstants.SPRINT_ASSIST, MPSConstants.SPRINT_SPEED_MULTIPLIER, 2.49F);

                addBaseProperty(MPSConstants.SPRINT_ENERGY_CONSUMPTION, 0, "FE");
                addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.SPRINT_ENERGY_CONSUMPTION, 20);
                addBaseProperty(MPSConstants.FOOD_COMPENSATION, 0, "%");
                addTradeoffProperty(MPSConstants.COMPENSATION, MPSConstants.FOOD_COMPENSATION, 1);

                addBaseProperty(MPSConstants.WALKING_ENERGY_CONSUMPTION, 0, "FE");
                addTradeoffProperty(MPSConstants.WALKING_ASSISTANCE, MPSConstants.WALKING_ENERGY_CONSUMPTION, 100);
                addBaseProperty(MPSConstants.WALKING_SPEED_MULTIPLIER, 0.01F, "%");
                addTradeoffProperty(MPSConstants.WALKING_ASSISTANCE, MPSConstants.WALKING_SPEED_MULTIPLIER, 1.99F);
            }};
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap instanceof IPlayerTickModule) {
                ticker.updateFromNBT();
            }
            return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(()-> ticker));
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, @Nonnull ItemStack itemStack) {
                if (player.abilities.flying || player.isPassenger() || player.isFallFlying())
                    onPlayerTickInactive(player, itemStack);

                double horzMovement = player.walkDist - player.walkDistO;
                double totalEnergy = ElectricItemUtils.getPlayerEnergy(player);
                if (horzMovement > 0) { // stop doing drain calculations when player hasn't moved
                    if (player.isSprinting()) {
                        double exhaustion = Math.round(horzMovement * 100.0F) * 0.01;
                        double sprintCost = applyPropertyModifiers(MPSConstants.SPRINT_ENERGY_CONSUMPTION);
                        if (sprintCost < totalEnergy) {
                            double sprintMultiplier = applyPropertyModifiers(MPSConstants.SPRINT_SPEED_MULTIPLIER);
                            double exhaustionComp = applyPropertyModifiers(MPSConstants.FOOD_COMPENSATION);
                            ElectricItemUtils.drainPlayerEnergy(player, (int) (sprintCost * horzMovement * 5));
                            MovementManager.INSTANCE.setMovementModifier(itemStack, sprintMultiplier, player);
                            player.getFoodData().addExhaustion((float) (-0.01 * exhaustion * exhaustionComp));
                            player.flyingSpeed = player.getSpeed() * .2f;
                        }
                    } else {
                        double cost = applyPropertyModifiers(MPSConstants.WALKING_ENERGY_CONSUMPTION);
                        if (cost < totalEnergy) {
                            double walkMultiplier = applyPropertyModifiers(MPSConstants.WALKING_SPEED_MULTIPLIER);
                            ElectricItemUtils.drainPlayerEnergy(player, (int) (cost * horzMovement * 5));
                            MovementManager.INSTANCE.setMovementModifier(itemStack, walkMultiplier, player);
                            player.flyingSpeed = player.getSpeed() * .2f;
                        }
                    }
                }
            }

            @Override
            public void onPlayerTickInactive(PlayerEntity player, @Nonnull ItemStack itemStack) {
                MovementManager.INSTANCE.setMovementModifier(itemStack, 0, player);
            }
        }
    }
}