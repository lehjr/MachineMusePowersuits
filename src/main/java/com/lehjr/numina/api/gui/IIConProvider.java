package com.lehjr.numina.api.gui;

import com.lehjr.numina.api.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;

public interface IIConProvider {
        void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color);
}
