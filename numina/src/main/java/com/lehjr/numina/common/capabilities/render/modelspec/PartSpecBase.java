package com.lehjr.numina.common.capabilities.render.modelspec;

import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.math.Color;
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
        return this.getBinding().getSlot().equals(slot) && slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR;
    }

    public boolean getGlow() {
        return this.defaultglow;
    }

    public boolean getGlow(CompoundTag nbt) {
        return nbt.contains(NuminaConstants.GLOW) ? nbt.getBoolean(NuminaConstants.GLOW) : this.defaultglow;
    }

    public void setGlow(CompoundTag nbt, boolean g) {
        if (g == this.defaultglow) nbt.remove(NuminaConstants.GLOW);
        else nbt.putBoolean(NuminaConstants.GLOW, g);
    }

    public boolean isForHand(HumanoidArm arm, LivingEntity entity) {
        if (binding.getSlot().getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
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
        return nbt.contains(NuminaConstants.COLOUR_INDEX) ? nbt.getInt(NuminaConstants.COLOUR_INDEX) : this.defaultcolorindex;
    }

    public void setColorIndex(CompoundTag nbt, int c) {
        if (c == this.defaultcolorindex) {
            nbt.remove(NuminaConstants.COLOUR_INDEX);
        } else {
            nbt.putInt(NuminaConstants.COLOUR_INDEX, c);
        }
    }

    public void setModel(CompoundTag nbt, SpecBase model) {
        String modelString = NuminaModelSpecRegistry.getInstance().getName(model);
        setModel(nbt, ((modelString != null) ? modelString : model.getOwnName()));
    }

    public void setModel(CompoundTag nbt, String modelname) {
        nbt.putString(NuminaConstants.MODEL, modelname);
    }

    public void setPart(CompoundTag nbt) {
        nbt.putString(NuminaConstants.PART, this.partName);
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
