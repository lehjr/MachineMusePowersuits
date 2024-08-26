package com.lehjr.numina.client.gui.clickable.slider;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import com.lehjr.numina.common.utils.MathUtils;
import com.lehjr.numina.common.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public abstract class AbstractTinkerSlider extends VanillaSlider {
    double prevValue = 0;

    public AbstractTinkerSlider(MusePoint2D ul,
                                double width,
                                String id,
                                Component label) {
        super(ul, width, id);
        setValue(getValue());
        setMessage(label);
        setActive(true);
    }

    @Override
    public void setValue(double value) {
        super.setValue(value);
    }

    public void checkIfUpdated() {
        double sliderVal = super.getValue();
        if(prevValue != sliderVal) {
            prevValue = sliderVal;
            onUpdated();
        }
    }

    public void setValueByTag(CompoundTag tag) {
        sliderValue = MathUtils.clampDouble(TagUtils.getDoubleOrZero(tag, id), 0, 1);
    }

    @Override
    public void updateSlider() {
        // overriding to eliminate irrelevant number displayed
    }
}
