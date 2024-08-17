package com.lehjr.numina.common.capabilities.render.color;

import com.lehjr.numina.common.math.Color;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class ColorStorage implements IColorTag, INBTSerializable<Tag> {
    Color color;

    public ColorStorage() {
        color = Color.LIGHT_BLUE;
    }

    public ColorStorage(Color colorIn) {
        color = colorIn;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Tag serializeNBT(HolderLookup.Provider provider) {
        return IntTag.valueOf(color.getARGBInt());
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        if (!(nbt instanceof IntTag intNbt)) {
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        }
        this.color = new Color(intNbt.getAsInt());
    }
}