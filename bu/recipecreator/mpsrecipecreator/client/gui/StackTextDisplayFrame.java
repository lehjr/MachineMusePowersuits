package com.lehjr.mpsrecipecreator.client.gui;


import com.mojang.blaze3d.matrix.PoseStack;
import lehjr.numina.util.client.gui.frame.ScrollableFrame;
import lehjr.numina.util.client.render.MuseRenderer;
import lehjr.numina.util.math.Color;

public class StackTextDisplayFrame extends ScrollableFrame {
    String label = "";
    int slot = -1;

    public StackTextDisplayFrame(Color backgroundColor) {
        super();
        setBackgroundColor(backgroundColor);
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setLabel(String labelIn) {
        label = labelIn;
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        MuseRenderer.drawLeftAlignedShadowedString(matrixStack, slot != -1 ? "Slot " + slot + ": " +
                this.label : "No slot selected", finalLeft() + 4, center().getY());
    }
}