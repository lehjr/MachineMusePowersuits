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

import com.mojang.blaze3d.matrix.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import lehjr.numina.constants.NuminaConstants;
import lehjr.numina.network.NuminaPackets;
import lehjr.numina.network.packets.TweakRequestDoublePacket;
import lehjr.numina.util.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import lehjr.numina.util.client.gui.clickable.ClickableTinkerIntSlider;
import lehjr.numina.util.client.gui.clickable.ClickableTinkerSlider;
import lehjr.numina.util.client.gui.frame.ScrollableFrame;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Color;
import lehjr.numina.util.nbt.propertymodifier.IPropertyModifier;
import lehjr.numina.util.nbt.propertymodifier.PropertyModifierIntLinearAdditive;
import lehjr.numina.util.nbt.propertymodifier.PropertyModifierLinearAdditive;
import lehjr.numina.util.string.MuseStringUtils;
import lehjr.powersuits.client.gui.common.ModularItemSelectionFrame;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.text.TranslatableComponent;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModuleTweakFrame extends ScrollableFrame {
    protected static int margin = 4;
    protected ModularItemSelectionFrame itemTarget;
    protected ModuleSelectionFrame moduleTarget;
    protected List<ClickableTinkerSlider> sliders = new LinkedList<>();
    protected Map<String, Double> propertyStrings = new HashMap<>();
    protected ClickableTinkerSlider selectedSlider;

    public ModuleTweakFrame(
            MusePoint2D topleft,
            MusePoint2D bottomright,
            Color background,
            Color topBorder,
            Color bottomBorder,
            ModularItemSelectionFrame itemTarget,
            ModuleSelectionFrame moduleTarget) {
        super(topleft, bottomright, background, topBorder, bottomBorder);
        this.itemTarget = itemTarget;
        this.moduleTarget = moduleTarget;
    }

    @Override
    public void update(double mousex, double mousey) {
        LazyOptional<IPowerModule> cap = moduleTarget.getModuleCap();
        if (cap.isPresent()) {
            loadTweaks(cap);
        } else {
            sliders.clear();
            propertyStrings.clear();
            selectedSlider = null;
        }

        for (ClickableTinkerSlider slider : sliders) {
            slider.update(mousex, mousey);
        }

        if (selectedSlider != null) {
            selectedSlider.setValueByX(mousex);
        }
    }

    public void resetScroll() {
        currentScrollPixels=0;
    }

    String getUnit(String key) {
        return moduleTarget.getSelectedModule().map(target->target.getModule().getCapability(PowerModuleCapability.POWER_MODULE)
                .map(pm-> pm.getUnit(key)).orElse("")).orElse("");
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        super.preRender(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(0, -currentScrollPixels, 0);
        // FIXME: translation
        MuseRenderer.drawShadowedStringCentered(matrixStack, new TranslatableComponent("gui.powersuits.tinker"), centerx(), top() + 7);

        for (ClickableTinkerSlider slider : sliders) {
            slider.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        int nexty = (int) (sliders.size() * 24 + getRect().top() + 18);

        for (Map.Entry<String, Double> property : propertyStrings.entrySet()) {
            String name = property.getKey();
            String formattedValue = MuseStringUtils.formatNumberFromUnits(property.getValue(), getUnit(name));
            double valueWidth = MuseRenderer.getStringWidth(formattedValue);
            double allowedNameWidth = getRect().width() - valueWidth - margin * 2;
            List<String> namesList = MuseStringUtils.wrapStringToVisualLength(
                    new TranslatableComponent(NuminaConstants.MODULE_TRADEOFF_PREFIX + name).getString(), allowedNameWidth);

            for (int i = 0; i < namesList.size(); i++) {
                MuseRenderer.drawLeftAlignedShadowedString(matrixStack, namesList.get(i), getRect().left() + margin, nexty + 9 * i);
            }
            MuseRenderer.drawRightAlignedShadowedString(matrixStack, formattedValue, getRect().right() - margin, nexty + 9 * (namesList.size() - 1) / 2);
            nexty += 9 * namesList.size() + 1;
        }

        RenderSystem.popPose();
        super.postRender(mouseX, mouseY, partialTicks);
    }

    /**
     * Loads values that can be adjusted through the sliders
     * Also loads permanently set values for display
     *
     * @param cap
     */
    private void loadTweaks(LazyOptional<IPowerModule> cap) {
        propertyStrings = new HashMap();
        Map<String, PropertyModifierLinearAdditive> tweaks = new HashMap<>();
        sliders.clear();
        this.totalSize = cap.map(pm -> {
            int totalSize = 0;
            CompoundTag moduleTag = pm.getModuleTag();
            Map<String, List<IPropertyModifier>> propertyModifiers = pm.getPropertyModifiers();
            for (Map.Entry<String, List<IPropertyModifier>> property : propertyModifiers.entrySet()) {
                double currValue = 0;
                for (IPropertyModifier modifier : property.getValue()) {
                    currValue = modifier.applyModifier(moduleTag, currValue);
                    if (modifier instanceof PropertyModifierLinearAdditive) {
                        String modifierName = ((PropertyModifierLinearAdditive) modifier).getTradeoffName();
                        // overwriting PropertyModifierIntLinearAdditive messes up rounding to int
                        if (!(tweaks.get(modifierName) instanceof PropertyModifierIntLinearAdditive)) {
                            tweaks.put(modifierName, (PropertyModifierLinearAdditive) modifier);
                        }
                        totalSize += 9;
                    }
                }
                propertyStrings.put(property.getKey(), currValue);
                totalSize += 9;
            }

            int y = 0;
            for (String tweak : tweaks.keySet()) {
                y += 23;
                MusePoint2D center = new MusePoint2D(getRect().centerx(), getRect().top() + y);
                PropertyModifierLinearAdditive tweakObj = tweaks.get(tweak);
                if (tweakObj instanceof PropertyModifierIntLinearAdditive) {
                    ClickableTinkerIntSlider slider = new ClickableTinkerIntSlider(
                            center,
                            getRect().finalRight() - getRect().finalLeft() - 16,
                            moduleTag,
                            tweak,
                            new TranslatableComponent(NuminaConstants.MODULE_TRADEOFF_PREFIX + tweak),
                            (PropertyModifierIntLinearAdditive) tweaks.get(tweak));
                    sliders.add(slider);
                    totalSize += slider.finalHeight();
                } else {
                    ClickableTinkerSlider slider = new ClickableTinkerSlider(
                            center,
                            getRect().finalRight() - getRect().finalLeft() - 16,
                            moduleTag,
                            tweak,
                            new TranslatableComponent(NuminaConstants.MODULE_TRADEOFF_PREFIX + tweak));
                    sliders.add(slider);
                    totalSize += slider.finalHeight();
                }
            }
            return totalSize + 20;
        }).orElse(0);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (button == 0) {
            y += currentScrollPixels;
            for (ClickableTinkerSlider slider : sliders) {
                if (slider.mouseClicked(x, y, button)) {
                    selectedSlider = slider;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        if (selectedSlider != null && button == 0) {
            selectedSlider.mouseReleased(x, y, button);
            itemTarget.selectedType().ifPresent(type-> moduleTarget.getModuleCap().ifPresent(pm->
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(
                            new TweakRequestDoublePacket(
                                    type,
                                    pm.getModuleStack().getItem().getRegistryName(),
                                    selectedSlider.id(),
                                    selectedSlider.getValue()))));
            selectedSlider = null;
            return true;
        }
        return false;
    }
}