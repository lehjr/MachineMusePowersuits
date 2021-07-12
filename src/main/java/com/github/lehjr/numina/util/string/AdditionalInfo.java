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

package com.github.lehjr.numina.util.string;

import com.github.lehjr.numina.constants.NuminaConstants;
import com.github.lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
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
    public static void appendHoverText(@Nonnull ItemStack stack, World worldIn, List currentTipList, ITooltipFlag advancedToolTips) {
        if (worldIn == null) {
            return;
        }

//        // TODO: remove enchantment labels.
//        if (currentTipList.contains(I18n.get("silkTouch"))) {
//            currentTipList.remove(I18n.get("silkTouch"));
//        }

        // Modular Item Check
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
            // base class
            if(iItemHandler instanceof IModularItem) {
                // Mode changing item such as power fist
                if (iItemHandler instanceof IModeChangingItem) {
                    ItemStack activeModule = ((IModeChangingItem) iItemHandler).getActiveModule();
                    if (!activeModule.isEmpty()) {

                        // IFormattableTextComponent
                        // TranslationTextComponent
                        IFormattableTextComponent localizedName = (IFormattableTextComponent) activeModule.getDisplayName();
                        currentTipList.add(
                                new TranslationTextComponent("tooltip.numina.mode")
//                                        .appendString(" ")
                                        .append(new StringTextComponent(" "))
                                        .append(localizedName.setStyle(Style.EMPTY.applyFormat(TextFormatting.RED))));
                    } else {
                        currentTipList.add(new TranslationTextComponent("tooltip.numina.changeModes"));
                    }
                }

                if (doAdditionalInfo()) {
                    List<ITextComponent> installed = new ArrayList<>();
                    Map<ITextComponent, FluidInfo> fluids = new HashMap<>();

                    if(iItemHandler instanceof IModularItem) {
                        for (ItemStack module : ((IModularItem) iItemHandler).getInstalledModules()) {
                            installed.add(((IFormattableTextComponent)module.getDisplayName()).setStyle(Style.EMPTY.applyFormat((TextFormatting.LIGHT_PURPLE))));

                            // check mpodule for fluid
                            module.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluidHandler ->{
                                int numTanks = fluidHandler.getTanks();

                                for(int i=0; i < numTanks; i++) {
                                    FluidStack fluidStack = fluidHandler.getFluidInTank(i);
                                    if (fluidStack.isEmpty()) {
                                        continue;
                                    }
                                    int capacity = fluidHandler.getTankCapacity(i);

                                    ITextComponent fluidName = fluidHandler.getFluidInTank(i).getDisplayName();
                                    FluidInfo fluidInfo = fluids.getOrDefault(fluidName, new FluidInfo(fluidName)).addAmmount(fluidStack.getAmount()).addMax(capacity);
                                    fluids.put(fluidName, fluidInfo);
                                }
                            });
                        }
                    }

                    if (fluids.size() > 0) {
                        for(FluidInfo info : fluids.values()) {
                            currentTipList.add(info.getOutput());
                        }
                    }

                    if (installed.size() == 0) {
                        String message = I18n.get("tooltip.numina.noModules");
                        currentTipList.addAll(MuseStringUtils.wrapStringToLength(message, 30));
                    } else {
                        currentTipList.add(new TranslationTextComponent("tooltip.numina.installedModules"));
                        currentTipList.addAll(installed);
                    }
                } else {
                    currentTipList.add(additionalInfoInstructions());
                }
            }
        });

        stack.getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(iPowerModule -> {
            if (doAdditionalInfo()) {
                String description = I18n.get( stack.getItem().getDescriptionId() + ".desc");
                currentTipList.addAll(MuseStringUtils.wrapStringToLength(description, 30));
            } else {
                currentTipList.add(additionalInfoInstructions());
            }
        });

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energyCap->
                currentTipList.add(new StringTextComponent(I18n.get(NuminaConstants.TOOLTIP_ENERGY,
                MuseStringUtils.formatNumberShort(energyCap.getEnergyStored()),
                MuseStringUtils.formatNumberShort(energyCap.getMaxEnergyStored())))
                .setStyle(Style.EMPTY.applyFormat(TextFormatting.AQUA).withItalic(true))));
    }

    static class FluidInfo {
        TranslationTextComponent displayName;
        int currentAmount=0;
        int maxAmount=0;

        FluidInfo(ITextComponent displayName) {
            this.displayName = (TranslationTextComponent)displayName;
        }

        public ITextComponent getDisplayName() {
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

        public ITextComponent getOutput() {
            return displayName.append(new StringTextComponent(": ")).append(new StringTextComponent(new StringBuilder(currentAmount).append("/").append(maxAmount).toString()))
                    .setStyle(Style.EMPTY.applyFormat(TextFormatting.DARK_AQUA).withItalic(true));
        }
    }

    public static ITextComponent additionalInfoInstructions() {
        return new TranslationTextComponent("tooltip.numina.pressShift")
                .setStyle(Style.EMPTY.applyFormat(TextFormatting.GRAY).withItalic(true));
    }

    public static List<ITextComponent> getItemInstalledModules(@Nonnull ItemStack stack) {
        return stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(iItemHandler -> {
            List<ITextComponent> moduleNames = new ArrayList<>();

            if(iItemHandler instanceof IModularItem) {
                for (ItemStack module : ((IModularItem) iItemHandler).getInstalledModules()) {
                    moduleNames.add(((IFormattableTextComponent) module.getDisplayName()).setStyle(Style.EMPTY.applyFormat(TextFormatting.LIGHT_PURPLE)));
                }
            }
            return moduleNames;
        }).orElse(new ArrayList<>());
    }

    public static boolean doAdditionalInfo() {
        return InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT);
    }
}
