package com.lehjr.numina.client.gui.geometry;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface IDrawableRect extends IRect, IDrawable {
    default void preRender(GuiGraphics gfx, int mouseX, int mouseY, float frameTIme) {

    }

    default void postRender(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * @param x mouseX
     * @param y mouseY
     * @return tooltip or null if not returning tooltip;
     */
    default List<Component> getToolTip(int x, int y) {
        return null;
    }
}
