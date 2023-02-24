package lehjr.numina.client.gui.clickable.slider;

import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.common.capabilities.module.powermodule.IPowerModule;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class VanillaTinkerIntSlider extends VanillaTinkerSlider {
    IPowerModule.PropertyModifierIntLinearAdditive modifier;

    public VanillaTinkerIntSlider(MusePoint2D ul,
                                  double width,
                                  CompoundNBT moduleTag,
                                  String id,
                                  TranslationTextComponent label,
                                  @Nullable IPowerModule.PropertyModifierIntLinearAdditive modifier) {
        super(ul, width, moduleTag, id, label);
        this.modifier =modifier;
    }

    @Override
    public void setValueByMouse(double value) {
        super.setValueByMouse(value);
        moduleTag.putDouble(id(), modifier.getScaledDouble(moduleTag, 0));
    }
}