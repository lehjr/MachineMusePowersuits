package com.github.lehjr.numina.util.client.gui.gemoetry;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface IDrawableRect extends IRect, IDrawable {
    default void preRender(MatrixStack matrixStack, int mouseX, int mouseY, float frameTIme) {

    }

    default void postRender(int mouseX, int mouseY, float partialTicks) {

    }

    /**
     * @param x mouseX
     * @param y mouseY
     * @return tooltip or null if not returning tooltip;
     */
    default List<ITextComponent> getToolTip(int x, int y) {
        return null;
    }
}