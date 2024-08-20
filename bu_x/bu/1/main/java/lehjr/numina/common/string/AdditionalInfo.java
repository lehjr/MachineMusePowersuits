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

package com.lehjr.numina.common.string;

import com.mojang.blaze3d.platform.InputConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class AdditionalInfo {
    /**
     * Adds information to the item's tooltip when 'getting' it.
     *
     * @param stack            The itemstack to get the tooltip for
     * @param worldIn          The world (unused)
     * @param currentTipList   A list of strings containing the existing tooltip. When
     *                         passed, it will just contain the id of the item;
     *                         enchantments and lore are
     *                         appended afterwards.
     * @param advancedToolTips Whether or not the player has 'advanced tooltips' turned on in
     *                         their settings.
     */
    public static void appendHoverText(@Nonnull ItemStack stack, Level worldIn, List currentTipList, TooltipFlag advancedToolTips) {
        if (worldIn == null) {
            return;
        }

//        // TODO: remove enchantment labels.
//        if (currentTipList.contains(I18n.get("silkTouch"))) {
//            currentTipList.remove(I18n.get("silkTouch"));
//        }

        // Modular Item Check
        NuminaCapabilities.getCapability(stack, NuminaCapabilities.MODULAR_ITEM)
                // base class
                .map(IModularItem.class::cast)
                .ifPresent(iItemHandler -> {
                    // Mode changing item such as power fist
                    if (iItemHandler instanceof IModeChangingItem) {
                        ItemStack activeModule = ((IModeChangingItem) iItemHandler).getActiveModule();
                        if (!activeModule.isEmpty()) {

                            // MutableComponent
                            // Component.translatable
                            MutableComponent localizedName = (MutableComponent) activeModule.getDisplayName();
                            currentTipList.add(
                                    Component.translatable(NuminaConstants.TOOLTIP_MODE)
//                                        .appendString(" ")
                                            .append(Component.literal(" "))
                                            .append(localizedName.setStyle(Style.EMPTY.applyFormat(ChatFormatting.RED))));
                        } else {
                            currentTipList.add(Component.translatable(NuminaConstants.TOOLTIP_CHANGE_MODES));
                        }
                    }

                    if (doAdditionalInfo()) {
                        List<Component> installed = new ArrayList<>();
                        Map<Component, FluidInfo> fluids = new HashMap<>();

                        for (ItemStack module : iItemHandler.getInstalledModules()) {
                            installed.add(((MutableComponent)module.getDisplayName()).setStyle(Style.EMPTY.applyFormat((ChatFormatting.LIGHT_PURPLE))));

                            // check mpodule for fluid
                            NuminaCapabilities.getCapability(module, Capabilities.FluidHandler.ITEM).ifPresent(fluidHandler ->{
                                int numTanks = fluidHandler.getTanks();

                                for(int i=0; i < numTanks; i++) {
                                    FluidStack fluidStack = fluidHandler.getFluidInTank(i);
                                    if (fluidStack.isEmpty()) {
                                        continue;
                                    }
                                    int capacity = fluidHandler.getTankCapacity(i);

                                    Component fluidName = fluidHandler.getFluidInTank(i).getDisplayName();
                                    FluidInfo fluidInfo = fluids.getOrDefault(fluidName, new FluidInfo(fluidName)).addAmmount(fluidStack.getAmount()).addMax(capacity);
                                    fluids.put(fluidName, fluidInfo);
                                }
                            });
                        }

                        if (fluids.size() > 0) {
                            for(FluidInfo info : fluids.values()) {
                                currentTipList.add(info.getOutput());
                            }
                        }

                        if (installed.size() == 0) {
                            Component message = Component.translatable(NuminaConstants.TOOLTIP_NO_MODULES);
                            currentTipList.addAll(StringUtils.wrapComponentToLength(message, 30));
                        } else {
                            currentTipList.add(Component.translatable(NuminaConstants.TOOLTIP_INSTALLED_MODULES));
                            currentTipList.addAll(installed);
                        }
                    } else {
                        currentTipList.add(additionalInfoInstructions());
                    }
                });

        NuminaCapabilities.getCapability(stack, NuminaCapabilities.POWER_MODULE).ifPresent(iPowerModule -> {
            if (doAdditionalInfo()) {
                Component description = Component.translatable( stack.getItem().getDescriptionId() + ".desc");
                currentTipList.addAll(StringUtils.wrapComponentToLength(description, 30));
            } else {
                currentTipList.add(additionalInfoInstructions());
            }
        });

        NuminaCapabilities.getCapability(stack, Capabilities.EnergyStorage.ITEM).ifPresent(energyCap->
                // FIXME use Component.translatable??? !!!
                currentTipList.add(Component.literal(I18n.get(NuminaConstants.TOOLTIP_BATTERY_ENERGY,
                        StringUtils.formatNumberShort(energyCap.getEnergyStored()),
                        StringUtils.formatNumberShort(energyCap.getMaxEnergyStored())))
                        .setStyle(Style.EMPTY.applyFormat(ChatFormatting.AQUA).withItalic(true))));
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
            return displayName.copy().append(Component.literal(": ")).append(Component.literal(new StringBuilder(currentAmount).append("/").append(maxAmount).toString()))
                    .setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_AQUA).withItalic(true));
        }
    }



//    public static List<Component> getItemInstalledModules(@Nonnull ItemStack stack) {
//        return NuminaCapabilities.getCapability(stack, Capabilities.ItemHandler.ITEM)
//                .filter(IModularItem.class::isInstance)
//                .map(IModularItem.class::cast)
//                .map(iItemHandler -> {
//            List<Component> moduleNames = new ArrayList<>();
//                for (ItemStack module : iItemHandler.getInstalledModules()) {
//                    moduleNames.add(((MutableComponent) module.getDisplayName()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.LIGHT_PURPLE)));
//                }
//
//            return moduleNames;
//        }).orElse(new ArrayList<>());
//    }

    public static boolean doAdditionalInfo() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
    }
}
