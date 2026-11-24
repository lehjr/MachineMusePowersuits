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

package lehjr.powersuits.common.capability.legacy;


import lehjr.numina.common.capabilities.heat.IHeatStorage;
import lehjr.numina.common.capabilities.heat.ModularItemHeatWrapper;
import lehjr.numina.common.capabilities.inventory.modularitem.ModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.render.IModelSpec;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.powersuits.common.capability.armor.PowerArmorRenderWrapper;
import lehjr.powersuits.common.constants.MPSRegistryNames;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class PowerArmorLegacyCap implements ICapabilityProvider {
    final ItemStack itemStack;
    final ArmorItem.Type targetType;
    final ModularItem modularItem;
    final LazyOptional<IItemHandler> modularItemHolder;

    final PowerArmorRenderWrapper modelSpec;
    final LazyOptional<IModelSpec> modelSpecHolder;

    final LazyOptional<IHeatStorage> heatHolder;

    final LazyOptional<IFluidHandlerItem> fluidHolder;

    double maxHeat = 10;

    public PowerArmorLegacyCap(@Nonnull ItemStack itemStackIn, ArmorItem.Type type) {
        this.itemStack = itemStackIn;
        this.targetType = type;
        Map<ModuleCategory, RangedWrapper> rangedWrapperMap = new HashMap<>();
        switch(targetType) {
            case HELMET: {
                this.modularItem = new ModularItem(itemStack, 0, 18) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new RangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new RangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new RangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new RangedWrapper(this, 3, this.getSlots()));
                    setRangedWrapperMap(rangedWrapperMap);
                }};

                break;
            }

            case CHESTPLATE: {
                this.modularItem = new ModularItem(itemStack, 0, 18) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new RangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new RangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new RangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new RangedWrapper(this, 3, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                break;
            }

            case LEGGINGS: {
                this.modularItem = new ModularItem(itemStackIn, 0, 10) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new RangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new RangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_GENERATION,new RangedWrapper(this, 2, 3));
                    rangedWrapperMap.put(ModuleCategory.NONE,new RangedWrapper(this, 3, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                break;
            }

            case BOOTS: {
                this.modularItem = new ModularItem(itemStack, 0, 8) {{
                    rangedWrapperMap.put(ModuleCategory.ARMOR,new RangedWrapper(this, 0, 1));
                    rangedWrapperMap.put(ModuleCategory.ENERGY_STORAGE,new RangedWrapper(this, 1, 2));
                    rangedWrapperMap.put(ModuleCategory.NONE,new RangedWrapper(this, 2, this.getSlots()));
                    this.setRangedWrapperMap(rangedWrapperMap);
                }};
                break;
            }

            default:
                this.modularItem = new ModularItem(itemStack, 0, 8);

        }

        this.modularItemHolder = LazyOptional.of(()-> {
            modularItem.loadCapValues();
            return modularItem;
        });

        this.modelSpec = new PowerArmorRenderWrapper(itemStackIn);
        this.modelSpecHolder = LazyOptional.of(()-> modelSpec);


        heatHolder = LazyOptional.of(() -> {
            modularItem.loadCapValues();

            final ModularItemHeatWrapper heatStorage = new ModularItemHeatWrapper(itemStack, maxHeat);
            heatStorage.loadCapValues();
            return heatStorage;
        });

        this.fluidHolder = LazyOptional.of(()-> {
            if (targetType == ArmorItem.Type.CHESTPLATE) {
                modularItem.loadCapValues();
                return modularItem.getOnlineModuleOrEmpty(MPSRegistryNames.FLUID_TANK_MODULE).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(new EmptyFluidHandler());
            } else {
                return new EmptyFluidHandler();
            }
        });
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
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
            return modularItem.getStackInSlot(1).getCapability(cap, side);
        }

        final LazyOptional<T> fluidCapability = ForgeCapabilities.FLUID_HANDLER_ITEM.orEmpty(cap, fluidHolder);
        if (fluidCapability.isPresent()) {
            return fluidCapability;
        }

        // Try this and see if it works
        for (int i=0; i < modularItem.getSlots(); i++) {
            ItemStack module = modularItem.getStackInSlot(i);
            if (!module.isEmpty()) {
                if (module.getCapability(cap).isPresent()) {
                    return module.getCapability(cap);
                }
            }
        }
        return LazyOptional.empty();
    }

    class EmptyFluidHandler extends FluidHandlerItemStack {
        public EmptyFluidHandler() {
            super(ItemStack.EMPTY, 0);
        }
    }
}