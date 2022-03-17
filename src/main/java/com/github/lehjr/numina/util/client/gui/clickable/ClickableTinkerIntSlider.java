package com.github.lehjr.numina.util.client.gui.clickable;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.nbt.propertymodifier.PropertyModifierIntLinearAdditive;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class ClickableTinkerIntSlider extends ClickableTinkerSlider {
    PropertyModifierIntLinearAdditive modifier;

    public ClickableTinkerIntSlider(MusePoint2D topmiddle,
                                 double width,
                                 CompoundNBT moduleTag,
                                 String id,
                                 TranslationTextComponent label,
                                 @Nullable PropertyModifierIntLinearAdditive modifier) {
        super(topmiddle,
        width,
        moduleTag,
        id,
        label);
        this.modifier =modifier;
    }

    @Override
    public void setValueByX(double x) {
        super.setValueByX(x);
        moduleTag.putDouble(id(), modifier.getScaledDouble(moduleTag, 0));
//        System.out.println("setting value " + x);
//        super.setValueByX(x);
//        System.out.println("value set as: " + getValue());
//
//        System.out.println("setting value new way1: " + x);
//        moduleTag.putDouble(id(), modifier.getScaledDouble(moduleTag, 0));
//        System.out.println("new way1 value set as: " + getValue());
//
//
//        System.out.println("setting value new way2: " + x);

//        System.out.println("new way 2 value set as: " + getValue());


    }
}