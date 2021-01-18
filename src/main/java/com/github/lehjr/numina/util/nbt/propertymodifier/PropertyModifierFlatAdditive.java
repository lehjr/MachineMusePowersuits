package com.github.lehjr.numina.util.nbt.propertymodifier;

import net.minecraft.nbt.CompoundNBT;

public class PropertyModifierFlatAdditive implements IPropertyModifier {
    public double valueAdded;

    public PropertyModifierFlatAdditive(double valueAdded) {
        this.valueAdded = valueAdded;
    }

    /**
     * @param moduleTag unused
     * @param value
     * @return getValue + this.valueAdded
     */
    @Override
    public double applyModifier(CompoundNBT moduleTag, double value) {
        return value + this.valueAdded;
    }
}