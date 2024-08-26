package com.lehjr.powersuits.client.gui.module.tweak;

import com.lehjr.numina.client.gui.clickable.slider.TinkerDoubleSlider;
import com.lehjr.numina.client.gui.clickable.slider.VanillaFrameScrollBar;
import com.lehjr.numina.client.gui.clickable.slider.TinkerIntSlider;
import com.lehjr.numina.client.gui.clickable.slider.AbstractTinkerSlider;
import com.lehjr.numina.client.gui.frame.ModularItemSelectionFrame;
import com.lehjr.numina.client.gui.frame.ScrollableFrame;
import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.client.gui.geometry.Rect;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.capabilities.module.powermodule.UnitMap;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.network.packets.serverbound.TweakRequestDoublePacketServerBound;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.numina.common.utils.StringUtils;
import com.lehjr.numina.common.utils.TagUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModuleTweakFrame extends ScrollableFrame {
    protected static int margin = 4;
    protected ModularItemSelectionFrame itemTarget;
    protected ModuleSelectionFrame moduleTarget;
    VanillaFrameScrollBar scrollBar;
    // the slider currently being adjusted, if any
    protected AbstractTinkerSlider selectedSlider;

    // repopulated on module or modular item change
    protected List<AbstractTinkerSlider> sliders = new LinkedList<>();
    // This should be updated every loop
    protected Map<String, Double> propertyStrings = new HashMap<>();
    // <TranslationKey, <List of associated propertyModifiers>>
    Map<String, List<IPowerModule.IPropertyModifier>> propertyModifiers = new HashMap<>();

    // This needs updating whenever a slider value changes just to make the value changes display, easier to just do it on every render loop
    Map<String, IPowerModule.PropertyModifierLinearAdditive> tweaks = new HashMap<>();

    // Needs updating on modular item change, module change, even value change
    IPowerModule pm = null;
    // Just a local copy that makes sliders work. Actual tag updated on server side.
    CompoundTag moduleTag = new CompoundTag();
    boolean needsUpdating = false;

    public ModuleTweakFrame(Rect rect, ModularItemSelectionFrame itemTarget, ModuleSelectionFrame moduleTarget) {
        super(rect);
        this.itemTarget = itemTarget;
        this.moduleTarget = moduleTarget;
        this.scrollBar = new VanillaFrameScrollBar(this, "scrolbar");
    }

    public void setRefresh() {
        this.needsUpdating = true;
    }

    @Override
    public void update(double mousex, double mousey) {
        pm = moduleTarget.getModuleCap();
        if (needsUpdating || pm == null){
            if (pm != null) {
                loadTweaks(pm);
            } else {
                propertyModifiers.clear();
                tweaks.clear();
                moduleTag = new CompoundTag();
                this.sliders.clear();
                this.propertyStrings.clear();
                this.selectedSlider = null;
            }
            needsUpdating = false;
        } else {
            updateTweaks(pm);
        }
        sliders.forEach(slider->slider.update(mousex, mousey));

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
        return UnitMap.getUnit(key);
    }

    @Override
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        double scrolledY = mouseY + currentScrollPixels;
        super.render(gfx, mouseX, mouseY, partialTick);
        scrollBar.render(gfx, mouseX, mouseY, partialTick);
        super.preRender(gfx, mouseX, mouseY, partialTick);
        gfx.pose().pushPose();
        gfx.pose().translate(0, (float) -currentScrollPixels, 0);
        sliders.forEach(slider->  slider.render(gfx, mouseX, (int)scrolledY, partialTick));
        int nexty = !sliders.isEmpty() ? (int) sliders.getLast().bottom() + 9 : 4;

        for (Map.Entry<String, Double> property : propertyStrings.entrySet()) {
            String name = property.getKey();
            String formattedValue = StringUtils.formatNumberFromUnits(property.getValue(), getUnit(name));
            double valueWidth = StringUtils.getStringWidth(formattedValue);
            double allowedNameWidth = width() - valueWidth - margin * 2;
            List<Component> namesList = StringUtils.wrapComponentToLength(Component.translatable(NuminaConstants.MODULE_TRADEOFF_PREFIX + name), (int)allowedNameWidth);

            for (int i = 0; i < namesList.size(); i++) {
                StringUtils.drawLeftAlignedShadowedString(gfx, namesList.get(i), left() + margin, nexty + 9 * i);
            }
            StringUtils.drawRightAlignedShadowedString(gfx, formattedValue, right() - margin, -5 + nexty + (double) (9 * (namesList.size() - 1)) / 2);            nexty += 9 * namesList.size() + 1;
        }

        gfx.pose().popPose();
        super.postRender(gfx, mouseX, mouseY, partialTick);
    }

    private void updateTweaks(IPowerModule pm) {
        if(pm != null) {
            moduleTag = pm.getModuleTag();
            for (Map.Entry<String, List<IPowerModule.IPropertyModifier>> property : propertyModifiers.entrySet()) {
                double currValue = 0;
                for (IPowerModule.IPropertyModifier modifier : property.getValue()) {
                    currValue = modifier.applyModifier(moduleTag, currValue);
                    if (modifier instanceof IPowerModule.PropertyModifierLinearAdditive) {
                        String modifierName = ((IPowerModule.PropertyModifierLinearAdditive) modifier).getTradeoffName();
                        // overwriting PropertyModifierIntLinearAdditive messes up rounding to int
                        if (!(tweaks.get(modifierName) instanceof IPowerModule.PropertyModifierIntLinearAdditive)) {
                            tweaks.put(modifierName, (IPowerModule.PropertyModifierLinearAdditive) modifier);
                        }
                    }
                }
                propertyStrings.put(property.getKey(), currValue);
            }
        }
    }

    /**
     * Loads values that can be adjusted through the sliders
     * Also loads permanently set values for display
     *
     * @param pm
     */
    private void loadTweaks(IPowerModule pm) {
        propertyStrings = new HashMap<>();
        sliders = new LinkedList<>();
        this.tweaks = new HashMap<>();

        this.totalSize = 0;
        if(pm != null) {
            moduleTag = pm.getModuleTag();
            this.propertyModifiers = new HashMap<>();
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
            }

            double left = centerX() - ((width() - 16) * 0.5);
            int y = 5;
            for (String tweak : tweaks.keySet()) {
                MusePoint2D ul = new MusePoint2D(left, top() + y);

                IPowerModule.PropertyModifierLinearAdditive tweakObj = tweaks.get(tweak);
                if (tweakObj instanceof IPowerModule.PropertyModifierIntLinearAdditive) {
                    TinkerIntSlider slider = new TinkerIntSlider(
                        ul,
                        width() - 16,
                        moduleTag,
                        tweak,
                        Component.translatable(NuminaConstants.MODULE_TRADEOFF_PREFIX + tweak),
                        (IPowerModule.PropertyModifierIntLinearAdditive) tweaks.get(tweak));
                    sliders.add(slider);
                    totalSize += (int) slider.height();
                } else {
                    TinkerDoubleSlider slider = new TinkerDoubleSlider(
                        ul,
                        width() - 16,
                        tweak,
                        Component.translatable(NuminaConstants.MODULE_TRADEOFF_PREFIX + tweak));
                    sliders.add(slider);
                    totalSize += (int) slider.height();
                }
                y += 22;
            }

            sliders.forEach(slider->{
                slider.setValue(TagUtils.getDoubleOrZero(moduleTag, slider.id()));
                slider.setOnUpdated(()-> {
                    itemTarget.selectedType().ifPresent(type-> {
                        NuminaPackets.sendToServer(new TweakRequestDoublePacketServerBound(type, ItemUtils.getRegistryName(pm.getModule()), slider.id(),
                            slider.getValue()));
                        moduleTarget.refreshModule();
                    });
                });
            });
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            mouseY += currentScrollPixels;
            for (AbstractTinkerSlider slider : sliders) {
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

            selectedSlider = null;
            return true;
        }
        return scrollBar.mouseReleased(mouseX, mouseY, button);
    }
}
