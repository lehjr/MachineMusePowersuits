package com.lehjr.numina.client.gui.clickable.slider;

import com.lehjr.numina.client.gui.geometry.MusePoint2D;
import com.lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class TinkerIntSlider extends AbstractTinkerSlider {
    CompoundTag moduleTagCopy;
    IPowerModule.PropertyModifierIntLinearAdditive modifier;

    public TinkerIntSlider(MusePoint2D ul,
                           double width,
                           CompoundTag moduleTag,
                           String id,
                           Component label,
                           @Nullable IPowerModule.PropertyModifierIntLinearAdditive modifier) {
        super(ul, width, id, label);
        this.moduleTagCopy = moduleTag.copy();
        this.modifier = modifier;
    }

    @Override
    public void setValueByMouse(double value) {
        super.setValueByMouse(value);
        moduleTagCopy.putDouble(id, getValue());
        double scaledValue = modifier.getScaledDouble(moduleTagCopy, 0);
        moduleTagCopy.putDouble(id, scaledValue);
        setValue(scaledValue);
        checkIfUpdated();
    }




}
