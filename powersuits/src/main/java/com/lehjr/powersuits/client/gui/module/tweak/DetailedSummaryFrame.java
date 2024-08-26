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

package com.lehjr.powersuits.client.gui.module.tweak;

import com.lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.StringUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.Objects;

public class DetailedSummaryFrame extends ScrollableFrame {
    protected int energy = 0;
    protected double armor = 0;
    protected ModularItemSelectionFrame itemSelectionFrame;
    Component energyText = Component.translatable(MPSConstants.GUI_ENERGY_STORAGE);
    Component armorText = Component.translatable(MPSConstants.GUI_ARMOR);

    public DetailedSummaryFrame(Rect rect, ModularItemSelectionFrame itemSelectionFrame) {
        super(rect);
        this.itemSelectionFrame = itemSelectionFrame;
    }

    @Override
    public void update(double mousex, double mousey) {
        energy = 0;
        armor = 0;

        for (EquipmentSlot type : EquipmentSlot.values()) {
            IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(ItemUtils.getItemFromEntitySlot(Objects.requireNonNull(getMinecraft().player), type));
            if (iModularItem != null) {
                Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
                if (range != null) {
                    for (int i = range.getFirst(); i < range.getSecond(); i++) {
                        IPowerModule pm = iModularItem.getModuleCapability(iModularItem.getStackInSlot(i));
                        if(pm != null) {
                            armor += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL);
                            armor += pm.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY);
                        }
                    }
                }

                range =  iModularItem.getRangeForCategory(ModuleCategory.ENERGY_STORAGE);
                if (range != null) {
                    for (int i = range.getFirst(); i < range.getSecond(); i++) {
                        IEnergyStorage energyStorage  = iModularItem.getStackInSlot(i).getCapability(Capabilities.EnergyStorage.ITEM);
                        if(energyStorage != null) {
                            energy = energyStorage.getEnergyStored();
                        }
                    }
                }
            }
        }
    }


    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick)  {
        if (getMinecraft().player != null) {
            super.render(gfx, mouseX, mouseY, partialTick);
            int margin = 4;
            int nexty = (int) top() + margin + 4;

            // Max Energy
            String formattedValue = StringUtils.formatNumberFromUnits(energy, "FE");
            StringUtils.drawShadowedString(gfx, energyText, left() + margin, nexty);
            StringUtils.drawRightAlignedShadowedString(gfx, formattedValue, right() - margin, nexty);
            nexty += 10;

            // Armor points
            formattedValue = StringUtils.formatNumberFromUnits(armor, "pts");
            StringUtils.drawShadowedString(gfx, armorText, left() + margin, nexty);
            StringUtils.drawRightAlignedShadowedString(gfx, formattedValue, right() - margin, nexty);
        }
    }
}
