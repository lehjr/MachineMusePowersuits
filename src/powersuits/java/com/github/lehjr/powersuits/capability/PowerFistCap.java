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

package com.github.lehjr.powersuits.capability;

import com.github.lehjr.numina.util.capabilities.heat.HeatCapability;
import com.github.lehjr.numina.util.capabilities.heat.HeatItemWrapper;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.ModeChangingModularItem;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.NuminaRangedWrapper;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.powersuits.client.render.PowerFistSpecNBT;
import com.github.lehjr.powersuits.config.MPSSettings;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PowerFistCap extends AbstractModularPowerCap {
    public PowerFistCap(@Nonnull ItemStack itemStackIn) {
        this.itemStack = itemStackIn;
        this.targetSlot = EquipmentSlotType.MAINHAND;

        this.modularItemCap = new ModeChangingModularItem(itemStack, 40)  {{
            Map<EnumModuleCategory, NuminaRangedWrapper> rangedWrapperMap = new HashMap<>();
            rangedWrapperMap.put(EnumModuleCategory.ENERGY_STORAGE, new NuminaRangedWrapper(this, 0, 1));
            rangedWrapperMap.put(EnumModuleCategory.NONE, new NuminaRangedWrapper(this, 1, this.getSlots() ));
            this.setRangedWrapperMap(rangedWrapperMap);
        }};
        this.modelSpec = new PowerFistSpecNBT(itemStack);
        this.heatStorage = new HeatItemWrapper(itemStack, MPSSettings.getMaxHeatPowerFist());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        if (cap == HeatCapability.HEAT) {
            heatStorage.updateFromNBT();
            return HeatCapability.HEAT.orEmpty(cap, LazyOptional.of(()->heatStorage));
        }

        return super.getCapability(cap, side);
    }
}
