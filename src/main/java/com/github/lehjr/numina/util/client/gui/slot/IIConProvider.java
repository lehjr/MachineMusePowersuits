package com.github.lehjr.numina.util.client.gui.slot;

import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;

public interface IIConProvider {
        void drawIconAt(MatrixStack matrixStack, double posX, double posY, Colour colour);
}
