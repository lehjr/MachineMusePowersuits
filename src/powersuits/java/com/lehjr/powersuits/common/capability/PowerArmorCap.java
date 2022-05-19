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

package com.lehjr.powersuits.common.capability;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.heat.CapabilityHeat;
import com.lehjr.numina.common.capabilities.heat.HeatItemWrapper;
import com.lehjr.numina.common.capabilities.heat.IHeatStorage;
import com.lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import com.lehjr.numina.common.capabilities.inventory.modularitem.NuminaRangedWrapper;
import com.lehjr.numina.common.capabilities.module.powermodule.CapabilityPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.capabilities.render.CapabilityModelSpec;
import com.lehjr.numina.common.capabilities.render.IModelSpec;
import com.lehjr.numina.common.constants.TagConstants;
import com.lehjr.powersuits.client.render.ArmorModelSpecNBT;
import com.lehjr.powersuits.common.config.MPSSettings;
import com.lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PowerArmorCap implements ICapabilityProvider {
//    final ItemStack itemStack;
    final EquipmentSlot targetSlot;
    final ModularItem modularItem;
    final LazyOptional<IItemHandler> modularItemHolder;

    final ArmorModelSpecNBT modelSpec;
    final LazyOptional<IModelSpec> modelSpecHolder;

    final HeatItemWrapper heatStorage;
    final LazyOptional<IHeatStorage> heatHolder;

    final LazyOptional<IEnergyStorage> energyHolder;

    final LazyOptional<IFluidHandlerItem> fluidHolder;



    public PowerArmorCap(@Nonnull ItemStack itemStackIn, EquipmentSlot slot) {
        double maxHeat;

//        this.itemStack = itemStackIn;
        this.targetSlot = slot;
        Map<ModuleCategory, NuminaRangedWrapper> rangedWrapperMap = new HashMap<>();
        switch(targetSlot) {
            case HEAD: {
                this.modularItem = new ModularItem(itemStackIn, 18) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
                    setRangedWrapperMap(rangedWrapperMap);
                }};

                maxHeat = MPSSettings.getMaxHeatHelmet();
                break;
            }

            case CHEST: {
                this.modularItem = new ModularItem(itemStackIn, 18) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                maxHeat = MPSSettings.getMaxHeatChestplate();
                break;
            }

            case LEGS: {
                this.modularItem = new ModularItem(itemStackIn, 10) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new NuminaRangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 3, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                maxHeat = MPSSettings.getMaxHeatLegs();
                break;
            }

            case FEET: {
                this.modularItem = new ModularItem(itemStackIn, 8) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new NuminaRangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new NuminaRangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.NONE,new NuminaRangedWrapper(this, 2, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                maxHeat = MPSSettings.getMaxHeatBoots();
                break;
            }

            default: {
                this.modularItem = new ModularItem(itemStackIn, 8);
                maxHeat = 0;
            }

        }

        this.modularItemHolder = LazyOptional.of(()-> {
            modularItem.onLoad();
            return modularItem;
        });

        this.modelSpec = new ArmorModelSpecNBT(itemStackIn);
        this.modelSpecHolder = LazyOptional.of(()-> modelSpec);

        NuminaLogger.logDebug("stack here in PowerArmorCap: " + itemStackIn);

        heatStorage = new HeatItemWrapper(itemStackIn, maxHeat);
        heatHolder = LazyOptional.of(() -> {
            modularItem.onLoad();
            heatStorage.setHeatCapacity(maxHeat + modularItem.getStackInSlot(0)
                    .getCapability(CapabilityPowerModule.POWER_MODULE).map(pm->pm.applyPropertyModifiers(TagConstants.MAXIMUM_HEAT)).orElse(0D));
            heatStorage.onLoad();
            return heatStorage;
        });

        NuminaLogger.logDebug("stack here in PowerArmorCap again: " + itemStackIn);

        energyHolder = LazyOptional.of(()-> {
            modularItem.onLoad();
            return modularItem.getStackInSlot(1).getCapability(CapabilityEnergy.ENERGY).orElse(new EnergyStorage(0));
        });

        this.fluidHolder = LazyOptional.of(()-> {
            if (targetSlot == EquipmentSlot.CHEST ) {
                modularItem.onLoad();
                return modularItem.getOnlineModuleOrEmpty(MPSRegistryNames.FLUID_TANK_MODULE).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(new EmptyFluidHandler());
            } else {
                return new EmptyFluidHandler();
            }
        });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == null) {
            return LazyOptional.empty();
        }

        final LazyOptional<T> modularItemCapability = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, modularItemHolder);
        if (modularItemCapability.isPresent()) {
            return modularItemCapability;
        }

        final LazyOptional<T> modelSpecCapability = CapabilityModelSpec.RENDER.orEmpty(cap, modelSpecHolder);
        if (modelSpecCapability.isPresent()) {
            return modelSpecCapability;
        }

        final LazyOptional<T> heatCapability = CapabilityHeat.HEAT.orEmpty(cap, heatHolder);
        if (heatCapability.isPresent()) {
            return heatCapability;
        }

        final LazyOptional<T> energyCapability = CapabilityEnergy.ENERGY.orEmpty(cap, energyHolder);
        if (energyCapability.isPresent()) {
            return energyCapability;
        }

        final LazyOptional<T> fluidCapability = CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, fluidHolder);
        if (fluidCapability.isPresent()) {
            return fluidCapability;
        }

        return LazyOptional.empty();
    }

    class EmptyFluidHandler extends FluidHandlerItemStack {
        public EmptyFluidHandler() {
            super(ItemStack.EMPTY, 0);
        }
    }
}