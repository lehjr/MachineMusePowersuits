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

package com.github.lehjr.numina.util.client.gui.clickable;

import com.github.lehjr.numina.util.client.gui.gemoetry.DrawableTile;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.render.MuseRenderer;
import com.github.lehjr.numina.util.math.Colour;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundEvents;

public class CheckBox extends Clickable {
    protected boolean isChecked;
    protected DrawableTile tile;
    String label;

    public CheckBox(MusePoint2D position, String displayString, boolean isChecked) {
        super();
        makeNewTile();
        this.label = displayString;
        this.isChecked = isChecked;
        this.enableAndShow();
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float frameTime) {
        if (this.isVisible()) {
            this.tile.render(matrixStack, mouseX, mouseY, frameTime);
            if (this.isChecked) {
                MuseRenderer.drawShadowedString(matrixStack, "x", this.tile.centerx() - 2.0D, this.tile.centery() - 5.0D, Colour.WHITE);
            }
            MuseRenderer.drawShadowedString(matrixStack, this.label, this.tile.centerx() + 8.0D, this.tile.centery() - 4.0D, Colour.WHITE);
        }
    }

    void makeNewTile() {
        if (tile == null) {
            MusePoint2D ul = getPosition().plus(4.0D, 4.0D);
            this.tile = (new DrawableTile(ul, ul.plus(8.0D, 8.0D))).setBackgroundColour(Colour.BLACK).setTopBorderColour(Colour.DARK_GREY).setBottomBorderColour(Colour.DARK_GREY);
        }
    }

    public boolean hitBox(double x, double y) {
        return this.isVisible() && this.isEnabled() ? this.tile.containsPoint(x, y) : false;
    }

    public void setPosition(MusePoint2D position) {
        super.setPosition(position);
        if(tile == null) {
            makeNewTile();
        }
        this.tile.setPosition(position);
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public void onPressed() {
        if (this.isVisible() && this.isEnabled()) {
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.isChecked = !this.isChecked;
        }
        super.onPressed();
    }
}