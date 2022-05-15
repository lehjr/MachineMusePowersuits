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

package com.lehjr.powersuits.common.item.module.cosmetic;

import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleTarget;
import com.lehjr.numina.common.capabilities.module.toggleable.IToggleableModule;
import com.lehjr.numina.common.capabilities.module.toggleable.ToggleableModule;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.item.module.AbstractPowerModule;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TransparentArmorModule extends AbstractPowerModule {
    public TransparentArmorModule() {
        super();
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new CapProvider(stack);
    }

    public class CapProvider implements ICapabilityProvider {
        private final IToggleableModule powerModule;
        private final LazyOptional<IPowerModule> powerModuleHolder;

        ItemStack module;

        public CapProvider(final ItemStack module) {
            this.module = module;
            powerModule = new ToggleableModule(module, ModuleCategory.COSMETIC, ModuleTarget.ARMORONLY, MPSSettings::getModuleConfig, true);
            powerModuleHolder = LazyOptional.of(() -> {
                powerModule.onLoad();
                return powerModule;
            });
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