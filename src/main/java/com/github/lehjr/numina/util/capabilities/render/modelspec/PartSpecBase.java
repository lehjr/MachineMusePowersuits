/*
 * Copyright (c) 2019 MachineMuse, Lehjr
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.lehjr.numina.util.capabilities.render.modelspec;

import com.github.lehjr.numina.constants.NuminaConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

import java.util.Objects;

/**
 * Ported to Java by lehjr on 11/8/16.
 */
public abstract class PartSpecBase {
    public final SpecBase spec;
    final String partName;
    final SpecBinding binding;
    Integer defaultcolourindex; // index getValue of NBTIntArray (array of colours as Int's.)

    public PartSpecBase(final SpecBase spec,
                        final SpecBinding binding,
                        final String partName,
                        final Integer defaultcolourindex) {
        this.spec = spec;
        this.partName = partName;
        this.binding = binding;
        if (defaultcolourindex != null && defaultcolourindex >= 0)
            this.defaultcolourindex = defaultcolourindex;
        else
            this.defaultcolourindex = 0;
    }

    public SpecBinding getBinding() {
        return binding;
    }

    public int getDefaultColourIndex() {
        return this.defaultcolourindex;
    }

    public abstract ITextComponent getDisaplayName();

    public int getColourIndex(CompoundNBT nbt) {
        return nbt.contains(NuminaConstants.TAG_COLOUR_INDEX) ? nbt.getInt(NuminaConstants.TAG_COLOUR_INDEX) : this.defaultcolourindex;
    }

    public void setColourIndex(CompoundNBT nbt, int c) {
        if (c == this.defaultcolourindex) nbt.remove(NuminaConstants.TAG_COLOUR_INDEX);
        else nbt.putInt(NuminaConstants.TAG_COLOUR_INDEX, c);
    }

    public void setModel(CompoundNBT nbt, SpecBase model) {
        String modelString = ModelRegistry.getInstance().getName(model);
        setModel(nbt, ((modelString != null) ? modelString : ""));
    }

    public void setModel(CompoundNBT nbt, String modelname) {
        nbt.putString(NuminaConstants.TAG_MODEL, modelname);
    }

    public void setPart(CompoundNBT nbt) {
        nbt.putString(NuminaConstants.TAG_PART, this.partName);
    }

    public CompoundNBT multiSet(CompoundNBT nbt, Integer colourIndex) {
        this.setPart(nbt);
        this.setModel(nbt, this.spec);
        this.setColourIndex(nbt, (colourIndex != null) ? colourIndex : defaultcolourindex);
        return nbt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartSpecBase that = (PartSpecBase) o;
        return Objects.equals(spec, that.spec) &&
                Objects.equals(partName, that.partName) &&
                Objects.equals(binding, that.binding) &&
                Objects.equals(defaultcolourindex, that.defaultcolourindex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spec, partName, binding, defaultcolourindex);
    }
}