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


import lehjr.numina.util.capabilities.heat.CapabilityHeat;
import lehjr.numina.util.capabilities.heat.HeatItemWrapper;
import lehjr.numina.util.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.util.capabilities.inventory.modularitem.NuminaRangedWrapper;
import lehjr.numina.util.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.util.capabilities.module.powermodule.CapabilityPowerModule;
import lehjr.powersuits.client.render.ArmorModelSpecNBT;
import lehjr.powersuits.config.MPSSettings;
import lehjr.powersuits.constants.MPSRegistryNames;
import net.minecraft.inventory.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PowerArmorCap extends AbstractModularPowerCap {
    double maxHeat;

    public PowerArmorCap(@Nonnull ItemStack itemStackIn, EquipmentSlot slot) {
        this.itemStack = itemStackIn;
        this.targetSlot = slot;
        this.modelSpec = new ArmorModelSpecNBT(itemStackIn);
        Map<ModuleCategory, NuminaRangedWrapper> rangedWrapperMap = new HashMap<>();
        switch(targetSlot) {
            case HEAD: {
                this.modularItemCap = new ModularItem(itemStack, 18) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
                    setRangedWrapperMap(rangedWrapperMap);
                }};

                this.maxHeat = MPSSettings.getMaxHeatHelmet();
                break;
            }

            case CHEST: {
                this.modularItemCap = new ModularItem(itemStack, 18) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                this.maxHeat = MPSSettings.getMaxHeatChestplate();
                break;
            }

            case LEGS: {
                this.modularItemCap = new ModularItem(itemStackIn, 10) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                this.maxHeat = MPSSettings.getMaxHeatLegs();
                break;
            }

            case FEET: {
                this.modularItemCap = new ModularItem(itemStack, 8) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 2, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                this.maxHeat = MPSSettings.getMaxHeatBoots();
                break;
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        // update item handler to gain access to the armor module if installed
        if (cap == CapabilityHeat.HEAT) {
            modularItemCap.onLoad();
            // initialize heat storage with whatever value is retrieved
            heatStorage = new HeatItemWrapper(
                    itemStack, maxHeat, modularItemCap.getStackInSlot(0).getCapability(CapabilityPowerModule.POWER_MODULE));
            // update heat storage to set current heat amount
            heatStorage.onLoad();
            return CapabilityHeat.HEAT.orEmpty(cap, LazyOptional.of(()-> heatStorage));
        }

        // Chest only
        if (targetSlot == EquipmentSlot.CHEST && cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
            modularItemCap.onLoad();
            return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap,
                    LazyOptional.of(()->modularItemCap.getOnlineModuleOrEmpty(MPSRegistryNames.FLUID_TANK_MODULE_REGNAME).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(new EmptyFluidHandler())));
        }

        return super.getCapability(cap, side);
    }

    class EmptyFluidHandler extends FluidHandlerItemStack {
        public EmptyFluidHandler() {
            super(ItemStack.EMPTY, 0);
        }
    }
}
