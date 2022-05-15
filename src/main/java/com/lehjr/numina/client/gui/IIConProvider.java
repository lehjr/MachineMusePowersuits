package com.lehjr.numina.client.gui;

import com.lehjr.numina.common.math.Color;
import com.mojang.blaze3d.vertex.PoseStack;

public interface IIConProvider {
        void drawIconAt(PoseStack matrixStack, double posX, double posY, Color color);
}
