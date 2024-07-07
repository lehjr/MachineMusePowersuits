package lehjr.numina.client.gui.clickable.slider;

import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capability.module.powermodule.IPowerModule;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class VanillaTinkerIntSlider extends VanillaTinkerSlider {
    IPowerModule.PropertyModifierIntLinearAdditive modifier;

    public VanillaTinkerIntSlider(MusePoint2D ul,
                                  double width,
                                  CompoundTag moduleTag,
                                  String id,
                                  Component label,
                                  @Nullable IPowerModule.PropertyModifierIntLinearAdditive modifier) {
        super(ul, width, moduleTag, id, label);
        this.modifier =modifier;
    }

    @Override
    public void setValueByMouse(double value) {
        super.setValueByMouse(value);
        NuminaLogger.logDebug("int slider module tag Before: " + moduleTag);
        moduleTag.putDouble(id(), modifier.getScaledDouble(moduleTag, 0));
        NuminaLogger.logDebug("int slider module tag After: " + moduleTag);
    }
}