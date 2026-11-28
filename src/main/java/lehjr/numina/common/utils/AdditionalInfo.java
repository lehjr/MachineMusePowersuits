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

package lehjr.numina.common.utils;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.item.ComponentItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdditionalInfo {
    public static void appendHoverText(ItemStack stack, List<Component> components) {
        boolean doAdditionalInfo = Screen.hasShiftDown();

        List<Component> installed = new ArrayList<>();
        Map<Component, FluidInfo> fluids = new HashMap<>();

        // is this a Modular Item
        IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(stack);
        if(iModularItem != null) {
            // Mode changing item such as power fist
            if (iModularItem instanceof IModeChangingItem) {
                ItemStack activeModule = ((IModeChangingItem) iModularItem).getActiveModule();
                if (!activeModule.isEmpty()) {
                    MutableComponent localizedName = (MutableComponent) activeModule.getDisplayName();
                    components.add(
                            Component.translatable(NuminaConstants.TOOLTIP_MODE)
                                    .append(Component.literal(" "))
                                    .append(localizedName.setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED))));
                } else {
                    components.add(Component.translatable(NuminaConstants.TOOLTIP_CHANGE_MODES));
                }
            }
        }

        // is it a power module, or is it both (special case)?
        IPowerModule pm = stack.getCapability(NuminaCapabilities.Module.POWER_MODULE).resolve().orElse(null);
        if(iModularItem != null) {
            for (ItemStack module : iModularItem.getInstalledModules()) {
                installed.add(((MutableComponent) module.getDisplayName()).setStyle(Style.EMPTY.applyFormat((ChatFormatting.LIGHT_PURPLE))));
                powerModuleFluidInfo(module, fluids);
            }
        }

        if(pm != null || stack.getItem() instanceof ComponentItem) {
            addDesc(stack, components);
            if(pm != null) {
                powerModuleFluidInfo(stack, fluids);
            }
        }

        if(iModularItem != null) {
            if (doAdditionalInfo) {
                if (installed.isEmpty()) {
                    Component message = Component.translatable(NuminaConstants.TOOLTIP_NO_MODULES);
                    components.addAll(StringUtils.wrapComponentToLength(message, 30));
                } else {
                    components.add(Component.translatable(NuminaConstants.TOOLTIP_INSTALLED_MODULES));
                    components.addAll(installed);
                }
            } else {
                components.add(additionalInfoInstructions());
            }
        }

        if (!fluids.isEmpty()) {
            for(FluidInfo info : fluids.values()) {
                components.add(info.getOutput());
            }
        }

        IEnergyStorage energyStorage = stack.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
        if(energyStorage != null) {
            // FIXME use Component.translatable??? !!!
            components.add(Component.literal(I18n.get(NuminaConstants.TOOLTIP_BATTERY_ENERGY,
                            StringUtils.formatNumberShort(energyStorage.getEnergyStored()),
                            StringUtils.formatNumberShort(energyStorage.getMaxEnergyStored())))
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA).withItalic(true)));
        }
    }

    static void powerModuleFluidInfo(ItemStack module,
                                     Map<Component, FluidInfo> fluids)  {

        // check module for fluid
        IFluidHandlerItem iFluidHandlerItem = module.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null);
        if(iFluidHandlerItem != null) {
            int numTanks = iFluidHandlerItem.getTanks();
            for(int i=0; i < numTanks; i++) {
                FluidStack fluidStack = iFluidHandlerItem.getFluidInTank(i);
                if (fluidStack.isEmpty()) {
                    continue;
                }
                int capacity = iFluidHandlerItem.getTankCapacity(i);

                Component fluidName = iFluidHandlerItem.getFluidInTank(i).getDisplayName();
                FluidInfo fluidInfo = fluids.getOrDefault(fluidName, new FluidInfo(fluidName)).addAmmount(fluidStack.getAmount()).addMax(capacity);
                fluids.put(fluidName, fluidInfo);
            }
        }
        //        IPowerModule pm = module.getCapability(NuminaCapabilities.Module.POWER_MODULE);
        //        if(pm != null || module.getItem() instanceof ComponentItem) {
        //            addDesc(module, components, doAdditionalInfo);
        //        }
    }


    public static void addDesc(ItemStack stack, List<Component> components) {
        boolean doAdditionalInfo = Screen.hasShiftDown();

        if (doAdditionalInfo) {
            Component description = Component.translatable( stack.getItem().getDescriptionId() + ".desc");
            components.addAll(StringUtils.wrapComponentToLength(description, 30));
        } else {
            components.add(additionalInfoInstructions());
        }
    }

    static class FluidInfo {
        Component displayName;
        int currentAmount=0;
        int maxAmount=0;

        FluidInfo(Component displayName) {
            this.displayName = displayName;
        }

        public Component getDisplayName() {
            return displayName;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public int getCurrentAmount() {
            return currentAmount;
        }

        public FluidInfo addMax(int maxAmountIn) {
            maxAmount += maxAmountIn;
            return this;
        }

        public FluidInfo addAmmount(int currentAmountIn) {
            currentAmount += currentAmountIn;
            return this;
        }

        public Component getOutput() {
            return displayName.copy().append(Component.literal(": ")).append("" +currentAmount).append(Component.literal("/" + maxAmount))
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_AQUA).withItalic(true));
        }
    }

    public static Component additionalInfoInstructions() {
        return Component.translatable(NuminaConstants.TOOLTIP_PRESS_SHIFT)
                .setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY).withItalic(true));
    }
}