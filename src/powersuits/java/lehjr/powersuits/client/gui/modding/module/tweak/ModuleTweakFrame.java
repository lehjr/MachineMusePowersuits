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

import com.mojang.blaze3d.vertex.PoseStack;
import lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import lehjr.numina.client.gui.clickable.slider.VanillaTinkerIntSlider;
import lehjr.numina.client.gui.clickable.slider.VanillaTinkerSlider;
import lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import lehjr.numina.client.gui.frame.ScrollableFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.TweakRequestDoublePacket;
import lehjr.numina.common.string.StringUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.common.util.LazyOptional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModuleTweakFrame extends ScrollableFrame {
    protected static int margin = 4;
    protected ModularItemSelectionFrame itemTarget;
    protected ModuleSelectionFrame moduleTarget;
    protected List<VanillaTinkerSlider> sliders = new LinkedList<>();
    protected Map<String, Double> propertyStrings = new HashMap<>();
    protected VanillaTinkerSlider selectedSlider;
    VanillaFrameScrollBar scrollBar;

    public ModuleTweakFrame(Rect rect, ModularItemSelectionFrame itemTarget, ModuleSelectionFrame moduleTarget) {
        super(rect);
        this.itemTarget = itemTarget;
        this.moduleTarget = moduleTarget;
        this.scrollBar = new VanillaFrameScrollBar(this, "slider");
    }

    @Override
    public void update(double mousex, double mousey) {
        LazyOptional<IPowerModule> cap = moduleTarget.getModuleCap();
        if (cap.isPresent()) {
            loadTweaks(cap);
        } else {
            this.sliders.clear();
            this.propertyStrings.clear();
            this.selectedSlider = null;
        }

        for (VanillaTinkerSlider slider : sliders) {
            slider.update(mousex, mousey);
        }

        if (selectedSlider != null) {
            selectedSlider.setValueByMouse(mousex);
        }
        scrollBar.setMaxValue(getMaxScrollPixels());
        scrollBar.setValueByMouse(mousey);
        setCurrentScrollPixels(scrollBar.getValue());
    }

    public void resetScroll() {
        currentScrollPixels=0;
    }

    String getUnit(String key) {
        return moduleTarget.getSelectedModule().map(target->target.getModule()
                .getCapability(NuminaCapabilities.POWER_MODULE)
                .map(pm-> pm.getUnit(key))
                .orElse("")).orElse("");
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
        double scrolledY = mouseY + currentScrollPixels;
        super.render(matrixStack, mouseX, mouseY, partialTick);
        scrollBar.render(matrixStack, mouseX, mouseY, partialTick);
        super.preRender(matrixStack, mouseX, mouseY, partialTick);
        matrixStack.pushPose();
        matrixStack.translate(0, (float) -currentScrollPixels, 0);
        sliders.forEach(slider->  slider.render(matrixStack, mouseX, (int)scrolledY, partialTick));
        int nexty = sliders.size() > 0 ? (int) sliders.get(sliders.size() - 1).bottom() + 9 : 4;

        for (Map.Entry<String, Double> property : propertyStrings.entrySet()) {
            String name = property.getKey();
            String formattedValue = StringUtils.formatNumberFromUnits(property.getValue(), getUnit(name));
            double valueWidth = StringUtils.getStringWidth(formattedValue);
            double allowedNameWidth = width() - valueWidth - margin * 2;
            List<Component> namesList = StringUtils.wrapComponentToLength(Component.translatable(TagConstants.MODULE_TRADEOFF_PREFIX + name), (int)allowedNameWidth);

            for (int i = 0; i < namesList.size(); i++) {
                StringUtils.drawLeftAlignedShadowedString(matrixStack, namesList.get(i), left() + margin, nexty + 9 * i);
            }
            StringUtils.drawRightAlignedShadowedString(matrixStack, formattedValue, right() - margin, -5 + nexty + 9 * (namesList.size() - 1) / 2);            nexty += 9 * namesList.size() + 1;
        }

        matrixStack.popPose();
        super.postRender(mouseX, mouseY, partialTick);
    }

    /**
     * Loads values that can be adjusted through the sliders
     * Also loads permanently set values for display
     *
     * @param cap
     */
    private void loadTweaks(LazyOptional<IPowerModule> cap) {
        propertyStrings = new HashMap();
        sliders = new LinkedList<>();
        Map<String, IPowerModule.PropertyModifierLinearAdditive> tweaks = new HashMap<>();

        this.totalSize = cap.map(pm -> {
            int totalSize = 0;
            CompoundTag moduleTag = pm.getModuleTag();

            Map<String, List<IPowerModule.IPropertyModifier>> propertyModifiers = new HashMap<>();

            pm.getPropertyModifiers().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEachOrdered(property -> {
                        propertyModifiers.put(property.getKey(), property.getValue());
                    });

            for (Map.Entry<String, List<IPowerModule.IPropertyModifier>> property : propertyModifiers.entrySet()) {
                double currValue = 0;
                for (IPowerModule.IPropertyModifier modifier : property.getValue()) {
                    currValue = modifier.applyModifier(moduleTag, currValue);
                    if (modifier instanceof IPowerModule.PropertyModifierLinearAdditive) {
                        String modifierName = ((IPowerModule.PropertyModifierLinearAdditive) modifier).getTradeoffName();
                        // overwriting PropertyModifierIntLinearAdditive messes up rounding to int
                        if (!(tweaks.get(modifierName) instanceof IPowerModule.PropertyModifierIntLinearAdditive)) {
                            tweaks.put(modifierName, (IPowerModule.PropertyModifierLinearAdditive) modifier);
                            totalSize += 9;
                        }

                    }
                }
                propertyStrings.put(property.getKey(), currValue);
//                totalSize += 9;
            }

            double left = centerX() - ((width() -16) * 0.5 );
            int y = 5;
            for (String tweak : tweaks.keySet()) {
                MusePoint2D ul = new MusePoint2D(left, top() +y);

                IPowerModule.PropertyModifierLinearAdditive tweakObj = tweaks.get(tweak);
                if (tweakObj instanceof IPowerModule.PropertyModifierIntLinearAdditive) {
                    VanillaTinkerIntSlider slider = new VanillaTinkerIntSlider(
                            ul,
                            width() - 16,
                            moduleTag,
                            tweak,
                            Component.translatable(TagConstants.MODULE_TRADEOFF_PREFIX + tweak),
                            (IPowerModule.PropertyModifierIntLinearAdditive) tweaks.get(tweak));
                    sliders.add(slider);
                    totalSize += slider.height();
                } else {
                    VanillaTinkerSlider slider = new VanillaTinkerSlider(
                            ul,
                            width() - 16,
                            moduleTag,
                            tweak,
                            Component.translatable(TagConstants.MODULE_TRADEOFF_PREFIX + tweak));
                    sliders.add(slider);
                    totalSize += slider.height();
                }
                y += 22;
            }
            return totalSize;// + 20;
        }).orElse(0);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            mouseY += currentScrollPixels;
            for (VanillaTinkerSlider slider : sliders) {
                if (slider.mouseClicked(mouseX, mouseY, button)) {
                    selectedSlider = slider;
                    return true;
                }
            }
            return scrollBar.mouseClicked(mouseX, mouseY, button);
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (selectedSlider != null) {
            selectedSlider.mouseReleased(mouseX, mouseY, button);
            itemTarget.selectedType().ifPresent(type-> moduleTarget.getModuleCap().ifPresent(pm-> {
                    NuminaPackets.CHANNEL_INSTANCE.sendToServer(
                            new TweakRequestDoublePacket(
                                    type,
                                    ItemUtils.getRegistryName(pm.getModuleStack()),
                                    selectedSlider.id(),
                                    selectedSlider.getValue()));
            }));
            selectedSlider = null;
            return true;
        }
        return scrollBar.mouseReleased(mouseX, mouseY, button);
    }
}