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

import com.mojang.blaze3d.platform.InputConstants;
import lehjr.numina.client.gui.geometry.IDrawableRect;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public interface IClickable extends IDrawableRect {
    default void moveBy(double x, double y) {
        this.moveBy(new MusePoint2D(x, y));
    }

    default void moveBy(MusePoint2D amount) {
        setPosition(center().plus(amount));
    }

    void setEnabled(boolean enabled);

    boolean isEnabled();

    void setVisible(boolean visible);

    boolean isVisible();

    default void hide() {
        setVisible(false);
    }

    default void show() {
        setVisible(true);
    }

    default void enable() {
        setEnabled(true);
    }

    default void disable() {
        setEnabled(false);
    }

    default void enableAndShow() {
        enable();
        show();
    }

    default void disableAndHide() {
        disable();
        hide();
    }

    default boolean mouseClicked(double mouseX, double mouseY, int button) {
        if( this.isEnabled() && this.isVisible() && containsPoint(mouseX, mouseY)) {
            InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
            boolean flag = Minecraft.getInstance().options.keyPickItem.isActiveAndMatches(mouseKey);

            if (button == 0 || button == 1 || flag) {
                this.onPressed();
            }
            this.playDownSound(Minecraft.getInstance().getSoundManager());
            return true;
        }
        return false;
    }

    default void playDownSound(SoundManager soundHandler) {
        soundHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }

    default boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(this.isEnabled() && this.isVisible()) {
            InputConstants.Key mouseKey = InputConstants.Type.MOUSE.getOrCreate(button);
            boolean flag = Minecraft.getInstance().options.keyPickItem.isActiveAndMatches(mouseKey);

            if (button == 0 || button == 1 || flag) {
                this.onReleased();
            }
        }
        return false;
    }

    void setOnPressed(IPressable onPressed);

    void setOnReleased(IReleasable onReleased);

    void onPressed();

    void onReleased();

    @OnlyIn(Dist.CLIENT)
    interface IPressable {
        void onPressed(IClickable doThis);
    }

    @OnlyIn(Dist.CLIENT)
    interface IReleasable {
        void onReleased(IClickable doThis);
    }

    default Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }
}