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

package lehjr.powersuits.common.capability;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.heat.HeatItemWrapper;
import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.inventory.modechanging.ModeChangingModularItem;
import lehjr.numina.common.capabilities.inventory.modularitem.NuminaRangedWrapper;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.capabilities.render.ModelSpecStorage;
import lehjr.powersuits.client.render.PowerFistSpecNBT;
import lehjr.powersuits.common.config.MPSSettings;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;



public class PowerFistCap implements ICapabilityProvider {
    final ItemStack itemStack;
    final EquipmentSlot targetSlot;
    final ModeChangingModularItem modularItem;
    final LazyOptional<IItemHandler> modularItemHolder;

    final ModelSpecStorage modelSpec;
    final LazyOptional<IModelSpec> modelSpecHolder;

    final HeatItemWrapper heatStorage;
    final LazyOptional<IHeatStorage> heatHolder;

    public PowerFistCap(@Nonnull ItemStack itemStackIn) {
        this.itemStack = itemStackIn;
        this.targetSlot = EquipmentSlot.MAINHAND;

        this.modularItem = new ModeChangingModularItem(itemStack, 60)  {{
            Map<ModuleCategory, NuminaRangedWrapper> rangedWrapperMap = new HashMap<>();
            rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE, new NuminaRangedWrapper(this, 0, 1));
            rangedWrapperMap.put(ModuleCategory.NONE, new NuminaRangedWrapper(this, 1, this.getSlots() ));
            this.setRangedWrapperMap(rangedWrapperMap);
        }};

        this.modularItemHolder = LazyOptional.of(()-> {
            modularItem.loadCapValues();
            return modularItem;
        });

        this.modelSpec = new PowerFistSpecNBT(itemStack);
        this.modelSpecHolder = LazyOptional.of(()-> modelSpec);

        this.heatStorage = new HeatItemWrapper(itemStack, MPSSettings.getMaxHeatPowerFist());
        heatHolder = LazyOptional.of(() -> {
            modularItem.loadCapValues();
            heatStorage.loadCapValues();
            return heatStorage;
        });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        final LazyOptional<T> modularItemCapability = ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, modularItemHolder);
        if (modularItemCapability.isPresent()) {
            return modularItemCapability;
        }

        final LazyOptional<T> modelSpecCapability = NuminaCapabilities.RENDER.orEmpty(cap, modelSpecHolder);
        if (modelSpecCapability.isPresent()) {
            return modelSpecCapability;
        }

        final LazyOptional<T> heatCapability = NuminaCapabilities.HEAT.orEmpty(cap, heatHolder);
        if (heatCapability.isPresent()) {
            return heatCapability;
        }

        // update item handler to gain access to the battery module if installed
        if (cap == ForgeCapabilities.ENERGY) {
            modularItem.loadCapValues();
            // armor first slot is armor plating, second slot is energy
            return modularItem.getStackInSlot(0).getCapability(cap, side);
        }

        return LazyOptional.empty();
    }
}