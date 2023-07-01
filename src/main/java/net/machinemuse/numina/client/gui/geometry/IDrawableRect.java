package net.machinemuse.numina.client.gui.geometry;

import net.machinemuse.numina.client.gui.IDrawable;

import java.util.List;

public interface IDrawableRect extends IRect, IDrawable {
    default void preRender(int mouseX, int mouseY, float frameTIme) {

    }

    default void postRender(int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * @param x mouseX
     * @param y mouseY
     * @return tooltip or null if not returning tooltip;
     */
    default List<String> getToolTip(int x, int y) {
        return null;
    }
}