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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

/**
 * Ported to Java by lehjr on 10/19/16.
 */
public class ClickableTinkerSlider2 extends RangedSlider {
    private CompoundNBT moduleTag;
    private CompoundNBT moduleTagBu;

    public ClickableTinkerSlider2(MusePoint2D pos, double width, CompoundNBT moduleTag, String id, ITextComponent label) {
        super(pos, true, width, label, id, 0, 1, 0);
        this.moduleTag = moduleTag;
        this.moduleTagBu = moduleTag.copy();
        System.out.println("moduleTag: " + this.moduleTag);
    }

    @Override
    public double getValue() {
        if (moduleTag != null) {
            return (moduleTag.contains(this.id())) ? moduleTag.getDouble(id()) : 0;
        } else {
            System.out.println("module tag NULL");
            moduleTag = moduleTagBu.copy();
            moduleTag.putDouble(id(), super.getValue());
            return super.getValue();
        }
    }

    @Override
    public void setValue(double value) {
        super.setValue(value);
        if (moduleTag != null) {
            moduleTag.putDouble(id(), super.getValue());
        } else {
            System.out.println("module tag NULL");
            moduleTag = moduleTagBu.copy();
            moduleTag.putDouble(id(), super.getValue());
        }
    }
}