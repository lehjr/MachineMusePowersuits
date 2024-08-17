package com.lehjr.numina.client.gui.clickable.slider;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.base.NuminaLogger;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

public class VanillaTinkerSlider extends VanillaSlider {
    public CompoundTag moduleTag;

    public VanillaTinkerSlider(MusePoint2D ul,
                                 double width,
                                 CompoundTag moduleTag,
                                 String id,
                                 Component label) {
        super(ul, width, id);
        this.moduleTag = moduleTag;
        setValue(getValue());
        setMessage(label);
        setActive(true);
    }

    @Override
    public double getSliderInternalValue() {
        return (moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0;
    }

    @Override
    public double getValue() {
        NuminaLogger.logDebug("getting value: " + ((moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0));

        return (moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0;
    }

    @Override
    public void setValue(double value) {
        super.setValue(value);
    }

//    @Override
//    public void setValueByMouse(double value) {
//        super.setValueByMouse(value);
//        moduleTag.putDouble(id(), super.getValue());
//    }

    @Override
    public void setValueByMouse(double value) {
        super.setValueByMouse(value);
        NuminaLogger.logDebug("double module tag Before: " + moduleTag);
        moduleTag.putDouble(id(), super.getValue());
        NuminaLogger.logDebug("double module tag After: " + moduleTag);
    }


    @Override
    public void updateSlider() {
    }
}
