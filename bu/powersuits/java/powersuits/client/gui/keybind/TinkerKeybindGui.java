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

package lehjr.powersuits.client.gui.keybind;

import lehjr.numina.util.client.gui.ContainerlessGui;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.powersuits.client.control.KeybindManager;
import lehjr.powersuits.client.gui.common.TabSelectFrame;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputConstants;
import net.minecraft.entity.player.Player;
import net.minecraft.util.text.Component;
import net.minecraftforge.client.settings.KeyModifier;


/**
 * Fixme: buttons rendering on top of module icons and their label/text
 */
public class TinkerKeybindGui extends ContainerlessGui {
    TabSelectFrame tabSelectFrame;
    Player player;
    KeyBindFrame kbFrame;

    public TinkerKeybindGui(Player player, Component titleIn) {
        super(titleIn, false);
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);
        this.player = player;
        MainWindow screen = Minecraft.getInstance().getWindow();
        this./*xSize*/imageWidth = 340;
        this./*ySize*/imageHeight = 217;
        tabSelectFrame = new TabSelectFrame(player, 2);
        kbFrame = new KeyBindFrame(
                new MusePoint2D(backgroundRect.left() + 8, backgroundRect.top() + 8),
                new MusePoint2D(backgroundRect.right() -8, backgroundRect.bottom() -8));
        addFrame(tabSelectFrame);
        addFrame(kbFrame);
    }

    /**
     * Add the buttons (and other controls) to the screen.
     */
    @Override
    public void init() {
        super.init();
        kbFrame.init(backgroundRect.finalLeft() + 8, backgroundRect.finalTop() + 8, backgroundRect.finalRight() -8, backgroundRect.finalBottom() -8);
        tabSelectFrame.initFromBackgroundRect(this.backgroundRect);
    }

    @Override
    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if (kbFrame.keybindingToRemap != null) {
            if (p_231046_1_ == 256) {
                kbFrame.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), InputConstants.UNKNOWN);
                Minecraft.getInstance().options.setKey(kbFrame.keybindingToRemap, InputConstants.UNKNOWN);
            } else {
                kbFrame.keybindingToRemap.setKeyModifierAndCode(KeyModifier.getActiveModifier(), InputConstants.getKey(p_231046_1_, p_231046_2_));
                Minecraft.getInstance().options.setKey(kbFrame.keybindingToRemap, InputConstants.getKey(p_231046_1_, p_231046_2_));
            }

            if (!KeyModifier.isKeyCodeModifier(kbFrame.keybindingToRemap.getKey())) {
                kbFrame.keybindingToRemap = null;
            }

//            this.lastKeySelection = Util.getMillis();
//            KeyBinding.resetMapping();
            return true;
        } else {
            return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!minecraft.isWindowActive()) {
            KeybindManager.INSTANCE.writeOutKeybindSetings();
            this.player.closeContainer();

//            super.onClose();
//            container.onContainerClosed(player);
        }
    }
}