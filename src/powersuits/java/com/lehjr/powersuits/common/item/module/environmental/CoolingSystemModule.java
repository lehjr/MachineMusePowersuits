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

package com.lehjr.powersuits.common.item.module.environmental;

import com.lehjr.numina.common.capabilities.module.powermodule.*;
import com.lehjr.numina.common.capabilities.module.tickable.PlayerTickModule;
import com.lehjr.numina.common.energy.ElectricItemUtils;
import com.lehjr.numina.common.heat.HeatUtils;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

/**
 * Created by Eximius88 on 1/17/14.
 */
public class CoolingSystemModule extends AbstractPowerModule {
    public CoolingSystemModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        private final Ticker ticker;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, ModuleCategory.ENVIRONMENTAL, ModuleTarget.TORSOONLY, MPSSettings::getModuleConfig, true) {{
                addTradeoffProperty(MPSConstants.COOLING_POWER, MPSConstants.COOLING_BONUS, 1, "%");
                addTradeoffProperty(MPSConstants.COOLING_POWER, MPSConstants.COOLING_ENERGY, 40, "RF/t");
            }};

            powerModuleHolder = LazyOptional.of(() -> {
                ticker.onLoad();
                return ticker;
            });
        }

        class Ticker extends PlayerTickModule {
            public Ticker(@Nonnull ItemStack module, ModuleCategory category, ModuleTarget target, Callable<IConfig> config, boolean defBool) {
                super(module, category, target, config, defBool);
            }

            @Override
            public void onPlayerTickActive(Player player, @Nonnull ItemStack item) {
                double heatBefore = HeatUtils.getPlayerHeat(player);

                HeatUtils.coolPlayer(player, /*0.1 * */ applyPropertyModifiers(MPSConstants.COOLING_BONUS));
                double cooling = heatBefore - HeatUtils.getPlayerHeat(player);
                ElectricItemUtils.drainPlayerEnergy(player, (int) (cooling * applyPropertyModifiers(MPSConstants.COOLING_ENERGY)));
            }
        }

        /** ICapabilityProvider ----------------------------------------------------------------------- */
        @Override
        @Nonnull
        public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> capability, final @Nullable Direction side) {
            final LazyOptional<T> CapabilityPowerModule = CapabilityPowerModule.POWER_MODULE.orEmpty(capability, powerModuleHolder);
            if (CapabilityPowerModule.isPresent()) {
                return CapabilityPowerModule;
            }
            return LazyOptional.empty();
        }
    }
}