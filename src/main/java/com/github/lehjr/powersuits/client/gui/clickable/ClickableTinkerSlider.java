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

package com.github.lehjr.powersuits.client.gui.clickable;

import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.nbt.propertymodifier.PropertyModifierIntLinearAdditive;
import com.github.lehjr.numina.util.nbt.propertymodifier.PropertyModifierLinearAdditive;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

/**
 * Ported to Java by lehjr on 10/19/16.
 */
public class ClickableTinkerSlider extends ClickableSlider {
    public CompoundNBT moduleTag;
    PropertyModifierLinearAdditive modifier;

    public ClickableTinkerSlider(MusePoint2D topmiddle,
                                 double width,
                                 CompoundNBT moduleTag,
                                 String id,
                                 TranslationTextComponent label,
                                 @Nullable PropertyModifierLinearAdditive modifier) {
        super(topmiddle, width, id, label);
        this.moduleTag = moduleTag;
        setValue(getValue());
        this.modifier =modifier;
    }

    /**
     * Overridden for now because sliders are created and destroyed taking their values with them ::eyeroll::
     * @return
     */
    @Override
    public double getInternalVal() {
        return (moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0;
    }

    @Override
    public double getValue() {
        return (moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0;
    }

    @Override
    public void setValueByX(double x) {
        super.setValueByX(x);
        moduleTag.putDouble(id(), super.getValue());

        /** adjusts the slider value so only whole number equivalents are used */
        if (modifier instanceof PropertyModifierIntLinearAdditive) {
           moduleTag.putDouble(id(), ((PropertyModifierIntLinearAdditive) modifier).getScaledDouble(moduleTag, 0));
        }
    }
}