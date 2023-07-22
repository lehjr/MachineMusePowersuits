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

package lehjr.numina.common.capabilities.render.modelspec;

import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.math.Color;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

/**
 * Ported to Java by lehjr on 11/8/16.
 */
public abstract class PartSpecBase {
    public final SpecBase spec;
    final String partName;
    final SpecBinding binding;
    Integer defaultcolorindex; // index getValue of NBTIntArray (array of colors as Int's.)
    private final boolean defaultglow;

    public PartSpecBase(final SpecBase spec,
                        final SpecBinding binding,
                        final String partName,
                        final Color color,
                        final Boolean defaultglow) {
        this.spec = spec;
        this.partName = partName;
        this.binding = binding;

        SpecBase other = NuminaModelSpecRegistry.getInstance().get(spec.getName());

        int defaultcolorindex = other.addColorIfNotExist(color);

        if (defaultcolorindex >= 0)
            this.defaultcolorindex = defaultcolorindex;
        else
            this.defaultcolorindex = 0;
        this.defaultglow = (defaultglow != null) ? defaultglow : false;
    }

    abstract String getNamePrefix();

    public Component getDisaplayName() {
        return Component.translatable(new StringBuilder(getNamePrefix())
                .append(this.spec.getOwnName())
                .append(".")
                .append(this.partName)
                .append(".partName")
                .toString());
    }

    public String getPartName() {
        return partName;
    }

    public boolean hasArmorEquipmentSlot(EquipmentSlot slot) {
        return this.getBinding().getSlot().equals(slot) && slot.getType() == EquipmentSlot.Type.ARMOR;
    }

    public boolean getGlow() {
        return this.defaultglow;
    }

    public boolean getGlow(CompoundTag nbt) {
        return nbt.contains(TagConstants.GLOW) ? nbt.getBoolean(TagConstants.GLOW) : this.defaultglow;
    }

    public void setGlow(CompoundTag nbt, boolean g) {
        if (g == this.defaultglow) nbt.remove(TagConstants.GLOW);
        else nbt.putBoolean(TagConstants.GLOW, g);
    }

    public boolean isForHand(HumanoidArm arm, LivingEntity entity) {
        if (binding.getSlot().getType() == EquipmentSlot.Type.ARMOR) {
            return false;
        }
        return binding.getTarget().getHandFromEquipmentSlot(entity).equals(arm);
    }

    public SpecBinding getBinding() {
        return binding;
    }

    public int getDefaultColorIndex() {
        return this.defaultcolorindex;
    }

    public int getColorIndex(CompoundTag nbt) {
        return nbt.contains(TagConstants.COLOUR_INDEX) ? nbt.getInt(TagConstants.COLOUR_INDEX) : this.defaultcolorindex;
    }

    public void setColorIndex(CompoundTag nbt, int c) {
        if (c == this.defaultcolorindex) {
            nbt.remove(TagConstants.COLOUR_INDEX);
        } else {
            nbt.putInt(TagConstants.COLOUR_INDEX, c);
        }
    }

    public void setModel(CompoundTag nbt, SpecBase model) {
        String modelString = NuminaModelSpecRegistry.getInstance().getName(model);
        setModel(nbt, ((modelString != null) ? modelString : model.getOwnName()));
    }

    public void setModel(CompoundTag nbt, String modelname) {
        nbt.putString(TagConstants.MODEL, modelname);
    }

    public void setPart(CompoundTag nbt) {
        nbt.putString(TagConstants.PART, this.partName);
    }

    public CompoundTag multiSet(CompoundTag nbt, Integer colorIndex, Boolean glow) {
        this.setGlow(nbt, (glow != null) ? glow : false);
        this.setPart(nbt);
        this.setModel(nbt, this.spec);
        this.setColorIndex(nbt, (colorIndex != null) ? colorIndex : defaultcolorindex);
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
                Objects.equals(defaultcolorindex, that.defaultcolorindex) &&
                Objects.equals(defaultglow, that.defaultglow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spec, partName, binding, defaultcolorindex);
    }
}