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

package com.github.lehjr.powersuits.item.module.energy_generation;

import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.tickable.IPlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.tickable.PlayerTickModule;
import com.github.lehjr.numina.util.capabilities.module.toggleable.IToggleableModule;
import com.github.lehjr.numina.util.energy.ElectricItemUtils;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.item.module.AbstractPowerModule;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class BasicSolarGeneratorModule extends AbstractPowerModule {
    public BasicSolarGeneratorModule() {
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, @Nullable CompoundNBT nbt){
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        ItemStack module;
        IPlayerTickModule ticker;

        public CapProvider(@Nonnull ItemStack module) {
            this.module = module;
            this.ticker = new Ticker(module, EnumModuleCategory.ENERGY_GENERATION, EnumModuleTarget.HEADONLY, MPSSettings::getModuleConfig) {{
                addBaseProperty(MPSConstants.ENERGY_GENERATION_DAY, 15000, "FE");
                addBaseProperty(MPSConstants.ENERGY_GENERATION_NIGHT, 1500, "FE");
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
            public Ticker(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                super(module, category, target, config, true);
            }

            @Override
            public int getTier() {
                return 1;
            }

            @Override
            public String getModuleGroup() {
                return "Energy Generation";
            }

            @Override
            public void onPlayerTickActive(PlayerEntity player, ItemStack itemStack) {
                World world = player.level;
                boolean isRaining, canRain = true;
                if (world.getGameTime() % 20 == 0) {
                    canRain = !(world.getBiome(player.blockPosition()).getPrecipitation() == Biome.RainType.NONE);
                }

                isRaining = canRain && (world.isRaining() || world.isThundering());
                boolean sunVisible = world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().offset(0, 1, 0));
                boolean moonVisible = !world.isDay() && !isRaining && world.canSeeSkyFromBelowWater(player.blockPosition().offset(0, 1, 0));
                if (!world.isClientSide && world.dimensionType().hasSkyLight() && (world.getGameTime() % 80) == 0) {
                    double lightLevelScaled = (world.getBrightness(LightType.SKY, player.blockPosition().above()) - world.getSkyDarken())/15D;
                    if (sunVisible) {
                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_DAY) * lightLevelScaled));
                    } else if (moonVisible) {
                        ElectricItemUtils.givePlayerEnergy(player, (int) (applyPropertyModifiers(MPSConstants.ENERGY_GENERATION_NIGHT) * lightLevelScaled));
                    }
                }
            }
        }
    }
}