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
import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.capabilities.module.powermodule.ModuleCategory;
import lehjr.numina.common.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.common.string.StringUtils;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.atomic.AtomicInteger;

public class DetailedSummaryFrame extends ScrollableFrame {
    protected AtomicInteger energy = new AtomicInteger(0);
    protected AtomicDouble armor = new AtomicDouble(0);
    protected ModularItemSelectionFrame itemSelectionFrame;
    TranslationTextComponent energyText = new TranslationTextComponent("gui.powersuits.energyStorage");
    TranslationTextComponent armorText = new TranslationTextComponent("gui.powersuits.armor");

    public DetailedSummaryFrame(Rect rect, ModularItemSelectionFrame itemSelectionFrame) {
        super(rect);
        this.itemSelectionFrame = itemSelectionFrame;
    }

    @Override
    public void update(double mousex, double mousey) {
        energy.set(0);
        armor.set(0);

        for (EquipmentSlotType type : EquipmentSlotType.values()) {
            getMinecraft().player.getItemBySlot(type).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> {
                        Pair<Integer, Integer> range = iModularItem.getRangeForCategory(ModuleCategory.ARMOR);
                        if (range != null) {
                            for (int i = range.getLeft(); i < range.getRight(); i++) {
                                iModularItem.getStackInSlot(i).getCapability(PowerModuleCapability.POWER_MODULE).ifPresent(iPowerModule -> {
                                    armor.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_PHYSICAL));
                                    armor.getAndAdd(iPowerModule.applyPropertyModifiers(MPSConstants.ARMOR_VALUE_ENERGY));
                                });
                            }
                        }

                        range =  iModularItem.getRangeForCategory(ModuleCategory.ENERGY_STORAGE);
                        if (range != null) {
                            for (int i = range.getLeft(); i < range.getRight(); i++) {
                                energy.getAndAdd(iModularItem.getStackInSlot(i).getCapability(CapabilityEnergy.ENERGY).map((energyHandler) -> energyHandler.getEnergyStored()).orElse(0));
                            }
                        }
                    });
        }
    }


    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)  {
        if (getMinecraft().player != null) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            int margin = 4;
            int nexty = (int) top() + margin + 4;

            // Max Energy
            String formattedValue = StringUtils.formatNumberFromUnits(energy.get(), "FE");
            StringUtils.drawShadowedString(matrixStack, energyText, left() + margin, nexty);
            StringUtils.drawRightAlignedShadowedString(matrixStack, formattedValue, right() - margin, nexty);
            nexty += 10;

            // Armor points
            formattedValue = StringUtils.formatNumberFromUnits(armor.get(), "pts");
            StringUtils.drawShadowedString(matrixStack, armorText, left() + margin, nexty);
            StringUtils.drawRightAlignedShadowedString(matrixStack, formattedValue, right() - margin, nexty);
        }
    }
}
