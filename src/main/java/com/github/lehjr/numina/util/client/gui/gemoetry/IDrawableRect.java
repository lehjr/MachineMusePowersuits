package com.github.lehjr.numina.util.client.gui.gemoetry;

import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IDrawableRect extends IRect, IDrawable {

    /**
     * @param x mouseX
     * @param y mouseY
     * @return tooltip or null if not returning tooltip;
     */
    default List<ITextComponent> getToolTip(int x, int y) {
        return null;
    }
}