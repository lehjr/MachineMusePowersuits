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
}
