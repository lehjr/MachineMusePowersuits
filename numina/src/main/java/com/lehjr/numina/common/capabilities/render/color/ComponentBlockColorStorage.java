package com.lehjr.numina.common.capabilities.render.color;

import com.lehjr.numina.common.math.Color;
import com.lehjr.numina.common.registration.NuminaCodecs;
import net.neoforged.neoforge.common.MutableDataComponentHolder;

public class ComponentBlockColorStorage implements IColorTag {
    protected final MutableDataComponentHolder parent;

    public ComponentBlockColorStorage(MutableDataComponentHolder parent) {
        this.parent = parent;
    }

    @Override
    public Color getColor() {
        return (Color) parent.getOrDefault(NuminaCodecs.COLOR, Color.WHITE);
    }

    @Override
    public void setColor(Color color) {
        parent.set(NuminaCodecs.COLOR, color.getARGBInt());
    }
}
