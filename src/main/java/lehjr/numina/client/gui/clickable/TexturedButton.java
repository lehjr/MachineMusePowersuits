/*
 * Copyright (c) 2021. MachineMuse, Lehjr
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *      Redistributions of source code must retain the above copyright notice, this
 *      list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *  ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lehjr.numina.client.gui.clickable;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.GuiIcon;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.RelativeRect;
import lehjr.numina.common.math.Colour;
import net.minecraft.util.ResourceLocation;

public class TexturedButton extends AbstractTexturedButton {
    public TexturedButton(double left, double top, double right, double bottom, boolean growFromMiddle, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(left, top, right, bottom, growFromMiddle, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedButton(double left, double top, double right, double bottom, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(left, top, right, bottom, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedButton(MusePoint2D ul, MusePoint2D br, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(ul, br, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    public TexturedButton(RelativeRect ref, Colour backgroundColourEnabled, Colour backgroundColourDisabled, Colour borderColourEnabled, Colour borderColourDisabled, double textureWidth, double textureHeight, ResourceLocation textureLocation) {
        super(ref, backgroundColourEnabled, backgroundColourDisabled, borderColourEnabled, borderColourDisabled, textureWidth, textureHeight, textureLocation);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isVisible) {
            super.render(matrixStack, mouseX, mouseY, partialTicks);
            Colour color;
            if (this.isEnabled) {
                color = this.hitBox(mouseX, mouseY) ? Colour.LIGHT_BLUE.withAlpha(0.6F) : Colour.WHITE;
            } else {
                color = Colour.RED.withAlpha(0.6F);
            }
            GuiIcon.renderTextureWithColour(this.textureLocation, matrixStack, left(), right(), top(), bottom(), getZLevel(),
                    iconWidth, iconHeight, texStartX, texStartY, textureWidth, textureHeight, color);
        }
    }
}
