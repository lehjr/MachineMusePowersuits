package com.lehjr.numina.api.gui.meter;

import com.lehjr.numina.api.math.Color;
import com.lehjr.numina.api.render.NuminaRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

public class EnergyMeter extends HeatMeter {
    public Color getColour() {
        return Color.YELLOW;
    }

    public void draw(PoseStack poseStack, float xpos, float ypos, float value) {
        super.draw(poseStack, xpos, ypos, value);
        RenderSystem.enableBlend();
            NuminaRenderer.drawMPDLightning(poseStack,
                    xpos + xsize * value, (float) (ypos + ysize * (Math.random() / 2F + 0.25)),
                    1F,
                    xpos, (float) (ypos + ysize * (Math.random() / 2 + 0.25)),
                    1F, Color.WHITE,
                    4, 1);
        RenderSystem.disableBlend();
    }
}