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

package lehjr.powersuits.client.gui.modding.module.tweak;


import com.google.common.util.concurrent.AtomicDouble;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.atomic.AtomicInteger;

public class DetailedSummaryFrame extends ScrollableFrame {
    protected AtomicInteger energy = new AtomicInteger(0);
    protected AtomicDouble armor = new AtomicDouble(0);
    protected ModularItemSelectionFrame itemSelectionFrame;
    Component energyText = Component.translatable(MPSConstants.GUI_ENERGY_STORAGE);
    Component armorText = Component.translatable(MPSConstants.GUI_ARMOR);

    public DetailedSummaryFrame(Rect rect, ModularItemSelectionFrame itemSelectionFrame) {
        super(rect);
        this.itemSelectionFrame = itemSelectionFrame;
    }

    @Override
    public void update(double mousex, double mousey) {
        energy.set(0);
        armor.set(0);

        for (EquipmentSlot type : EquipmentSlot.values()) {
            ItemUtils.getItemFromEntitySlot(getMinecraft().player, type)
                    .getCapability(ForgeCapabilities.ITEM_HANDLER)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> {
                        Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
                        if (range != null) {
                            for (int i = range.getLeft(); i < range.getRight(); i++) {
                                iModularItem.getStackInSlot(i).getCapability(NuminaCapabilities.POWER_MODULE).ifPresent(iPowerModule -> {
                                    armor.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL));
                                    armor.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY));
                                });
                            }
                        }

                        range =  iModularItem.getRangeForCategory(ModuleCategory.ENERGY_STORAGE);
                        if (range != null) {
                            for (int i = range.getLeft(); i < range.getRight(); i++) {
                                energy.getAndAdd(iModularItem.getStackInSlot(i).getCapability(ForgeCapabilities.ENERGY).map((energyHandler) -> energyHandler.getEnergyStored()).orElse(0));
                            }
                        }
                    });
        }
    }


    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick)  {
        if (getMinecraft().player != null) {
            super.render(gfx, mouseX, mouseY, partialTick);
            int margin = 4;
            int nexty = (int) top() + margin + 4;

            // Max Energy
            String formattedValue = StringUtils.formatNumberFromUnits(energy.get(), "FE");
            StringUtils.drawShadowedString(gfx, energyText, left() + margin, nexty);
            StringUtils.drawRightAlignedShadowedString(gfx, formattedValue, right() - margin, nexty);
            nexty += 10;

            // Armor points
            formattedValue = StringUtils.formatNumberFromUnits(armor.get(), "pts");
            StringUtils.drawShadowedString(gfx, armorText, left() + margin, nexty);
            StringUtils.drawRightAlignedShadowedString(gfx, formattedValue, right() - margin, nexty);
        }
    }
}
