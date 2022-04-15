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

package lehjr.powersuits.capability;

import lehjr.numina.util.capabilities.heat.IHeatWrapper;
import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.util.capabilities.render.IModelSpecNBT;
import lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class AbstractModularPowerCap implements ICapabilityProvider {
    ItemStack itemStack;
    IModularItem modularItemCap;
    IHeatWrapper heatStorage;
    IModelSpecNBT modelSpec;
    EquipmentSlotType targetSlot;

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        // All
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            modularItemCap.updateFromNBT();
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(()->modularItemCap));
        }

        // All
        if (cap == ModelSpecNBTCapability.RENDER) {
            return ModelSpecNBTCapability.RENDER.orEmpty(cap, LazyOptional.of(() -> modelSpec));
        }

        // All
        // update item handler to gain access to the battery module if installed
        if (cap == CapabilityEnergy.ENERGY) {
            modularItemCap.updateFromNBT();
            // armor first slot is armor plating, second slot is energy
            return modularItemCap.getStackInSlot(targetSlot.getType() == EquipmentSlotType.Group.ARMOR ? 1 : 0).getCapability(cap, side);
        }

        return LazyOptional.empty();
    }
}
