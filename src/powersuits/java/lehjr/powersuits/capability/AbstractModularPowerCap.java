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

import lehjr.numina.util.capabilities.heat.HeatCapability;
import lehjr.numina.util.capabilities.heat.HeatItemWrapper;
import lehjr.numina.util.capabilities.heat.IHeatStorage;
import lehjr.numina.util.capabilities.heat.IHeatWrapper;
import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.util.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.capabilities.render.IModelSpecNBT;
import lehjr.numina.util.capabilities.render.ModelSpecNBTCapability;
import lehjr.powersuits.constants.MPSRegistryNames;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

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

//
//public class AbstractModularPowerCap implements ICapabilityProvider {
//    protected final ItemStack itemStack;
//    protected final EquipmentSlotType targetSlot;
//    protected final ModularItem modularItem;
//    protected final LazyOptional<IItemHandler> modularItemHolder;
//
//    protected final IModelSpecNBT modelSpec;
//    protected final LazyOptional<IModelSpecNBT> modelSpecHolder;
//
//    protected final IHeatWrapper heatStorage;
//    protected final LazyOptional<IHeatStorage> heatHolder;
//
//    protected final LazyOptional<IEnergyStorage> energyHolder;
//
//    protected final LazyOptional<IFluidHandlerItem> fluidHolder;
//
//    /**
//     * Used by power fist
//     * @param itemStack
//     * @param targetSlot
//     * @param modularItem
//     * @param modelSpec
//     * @param heatWrapper
//     */
//    public AbstractModularPowerCap(
//            final ItemStack itemStack,
//            final EquipmentSlotType targetSlot,
//            final ModularItem modularItem,
//            final IModelSpecNBT modelSpec,
//            final IHeatWrapper heatWrapper) {
//        this.itemStack = itemStack;
//        this.targetSlot = targetSlot;
//
//        this.modularItem = modularItem;
//        this.modularItemHolder = LazyOptional.of(()-> {
////            System.out.println("nbt before updateFromNBT: " + modularItem.serializeNBT());
//
//            modularItem.updateFromNBT();
//
////            System.out.println("nbt AFTER updateFromNBT: " + modularItem.serializeNBT());
//
//            return modularItem;
//        });
//
//        this.modelSpec = modelSpec;
//        this.modelSpecHolder = LazyOptional.of(()-> modelSpec);
//
//        this.heatStorage = heatWrapper;
//        this.heatHolder = LazyOptional.of(()-> {
//            heatStorage.updateFromNBT();
//            return heatStorage;
//        });
//
//        // armor first slot is armor plating, second slot is energy
//        this.energyHolder = modularItem.getStackInSlot(targetSlot.getType() == EquipmentSlotType.Group.ARMOR ? 1 : 0).getCapability(CapabilityEnergy.ENERGY);
//
//        if (targetSlot != EquipmentSlotType.CHEST) {
//            fluidHolder = LazyOptional.empty();
//        } else {
//            fluidHolder = LazyOptional.of(()->modularItem.getOnlineModuleOrEmpty(MPSRegistryNames.FLUID_TANK_MODULE).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(new EmptyFluidHandler()));
//        }
//    }
//
//    /**
//     * Used by armor
//     * @param itemStack
//     * @param targetSlot
//     * @param modularItem
//     * @param maxHeat
//     * @param modelSpec
//     */
//    public AbstractModularPowerCap(
//            final ItemStack itemStack,
//            final EquipmentSlotType targetSlot,
//            final ModularItem modularItem,
//            final double maxHeat,
//            final IModelSpecNBT modelSpec
//    ) {
//        this.itemStack = itemStack;
//        this.targetSlot = targetSlot;
//
//        this.modularItem = modularItem;
//        this.modularItemHolder = LazyOptional.of(()-> {
//            System.out.println("nbt before updateFromNBT: " + modularItem.serializeNBT());
//
//            modularItem.updateFromNBT();
//
//            System.out.println("nbt AFTER updateFromNBT: " + modularItem.serializeNBT());
//            System.out.println("start installed modules: ");
//            modularItem.getInstalledModules().forEach(installed -> System.out.println(installed.serializeNBT()));
//            System.out.println("end installed modules: ");
//
//            System.out.println("slot 0:" + modularItem.getStackInSlot(0));
//            System.out.println("slot 1:" + modularItem.getStackInSlot(1));
//
//            return modularItem;
//        });
//
//        this.modelSpec = modelSpec;
//        this.modelSpecHolder = LazyOptional.of(()-> modelSpec);
//
//        heatStorage = new HeatItemWrapper(itemStack, maxHeat, modularItem.getStackInSlot(0).getCapability(PowerModuleCapability.POWER_MODULE));
//        this.heatHolder = LazyOptional.of(()-> {
//            heatStorage.updateFromNBT();
//            return heatStorage;
//        });
//
//
//        // armor first slot is armor plating, second slot is energy
//        this.energyHolder = modularItem.getStackInSlot(targetSlot.getType() == EquipmentSlotType.Group.ARMOR ? 1 : 0).getCapability(CapabilityEnergy.ENERGY);
//
//        System.out.println("stack here: " + modularItem.getStackInSlot(targetSlot.getType() == EquipmentSlotType.Group.ARMOR ? 1 : 0));
//
//        System.out.println("modular item serialized " + modularItem.serializeNBT());
//
//        System.out.println("modular item container: " + itemStack.serializeNBT());
//
//
//        if (targetSlot != EquipmentSlotType.CHEST) {
//            fluidHolder = LazyOptional.empty();
//        } else {
//            fluidHolder = LazyOptional.of(()->modularItem.getOnlineModuleOrEmpty(MPSRegistryNames.FLUID_TANK_MODULE).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).orElse(new EmptyFluidHandler()));
//        }
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if (cap== null) {
//            return LazyOptional.empty();
//        }
//
//
//        // All
//        final LazyOptional<T> modularItemCap = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, modularItemHolder);
//        if (modularItemCap.isPresent()) {
////            System.out.println("modular item is present");
//
//            return modularItemCap;
//        } else {
////            System.out.println("modular item is NOT present");
//        }
//
//        // All
//        final LazyOptional<T> modelSpecCap = ModelSpecNBTCapability.RENDER.orEmpty(cap, modelSpecHolder);
//        if (modelSpecCap.isPresent()) {
//            return modelSpecCap;
//        }
//
//        // All
//        final LazyOptional<T> energyCap = CapabilityEnergy.ENERGY.orEmpty(cap, energyHolder);
//        // update item handler to gain access to the battery module if installed
//        if (energyHolder.isPresent()) {
////            System.out.println("energy is present");
//
//            return energyCap;
//        } else {
////            System.out.println("energy is NOT present");
//        }
//
//        final LazyOptional<T> heatCap = HeatCapability.HEAT.orEmpty(cap, heatHolder);
//        if (heatCap.isPresent()) {
//            return heatCap;
//        }
//
//        final LazyOptional<T> fluidCap = CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.orEmpty(cap, fluidHolder);
//        if (fluidCap.isPresent()) {
//            return fluidCap;
//        }
//        return LazyOptional.empty();
//    }
//
//    class EmptyFluidHandler extends FluidHandlerItemStack {
//        public EmptyFluidHandler() {
//            super(ItemStack.EMPTY, 0);
//        }
//    }
//}
