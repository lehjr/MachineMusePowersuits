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

package com.github.lehjr.powersuits.client.gui.common;

import com.github.lehjr.numina.util.client.gui.clickable.ClickableButton;
import com.github.lehjr.numina.util.client.gui.frame.ScrollableFrame;
import com.github.lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import com.github.lehjr.numina.util.client.gui.gemoetry.MuseRelativeRect;
import com.github.lehjr.numina.util.client.sound.Musique;
import com.github.lehjr.numina.util.client.sound.SoundDictionary;
import com.github.lehjr.numina.util.math.Colour;
import com.github.lehjr.powersuits.client.gui.keybind.TinkerKeybindGui;
import com.github.lehjr.powersuits.client.gui.modding.cosmetic.CosmeticGui;
import com.github.lehjr.powersuits.network.MPSPackets;
import com.github.lehjr.powersuits.network.packets.ContainerGuiOpenPacket;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class TabSelectFrame extends ScrollableFrame {
    PlayerEntity player;
    List<ClickableButton> buttons = new ArrayList<>();

    public TabSelectFrame(PlayerEntity player, int exclude, float zLevel) {
        super(new MusePoint2D(0, 0), new MusePoint2D(0, 0), zLevel, Colour.WHITE.withAlpha(0), Colour.WHITE.withAlpha(0));
        this.player = player;
        ClickableButton button;
        if (exclude != 0) {
            button = new ClickableButton(new TranslationTextComponent("gui.powersuits.tab.tinker"), new MusePoint2D(0, 0), true);
            button.setOnPressed(onPressed->{
                Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
                MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(0));
            });
            buttons.add(button);
        }

        if (exclude !=1) {
            button = new ClickableButton(new TranslationTextComponent("gui.powersuits.tab.keybinds"), new MusePoint2D(0, 0), true);
            button.setOnPressed(onPressed->{
                Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
                Minecraft.getInstance().enqueue(() -> Minecraft.getInstance().displayGuiScreen(new TinkerKeybindGui(player.inventory, new TranslationTextComponent("gui.tinkertable"))));
            });
            buttons.add(button);
        }

        if (exclude !=2) {
            button = new ClickableButton(new TranslationTextComponent("gui.powersuits.tab.visual"), new MusePoint2D(0, 0), true);
            button.setOnPressed(onPressed->{
                Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
                Minecraft.getInstance().enqueue(() -> Minecraft.getInstance().displayGuiScreen(new CosmeticGui(player.inventory, new TranslationTextComponent("gui.tinkertable"))));
            });
            buttons.add(button);
        }

        if (exclude != 3) {
            button = new ClickableButton(new TranslationTextComponent("container.crafting"), new MusePoint2D(0, 0), true);
            button.setOnPressed(onPressed->{
                Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
                player.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
                MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(3));
            });
            buttons.add(button);
        }

        for(ClickableButton b : buttons) {
            b.setVisible(true);
        }
    }

    private void init() {
        double totalButtonWidth = 0;
        for (ClickableButton button : buttons) {
            totalButtonWidth += (button.getRadius().getX() * 2);
        }
        // totalButtonWidth greater than width will produce a negative spacing value
        double spacing = (this.width() - totalButtonWidth) / (buttons.size() +1);

        double x = spacing; // first entry may be negative and will allow an oversized tab frame to be centered
        for (ClickableButton button : buttons) {
            button.setPosition(new MusePoint2D(this.left() + x + button.getRadius().getX(), this.top() -6));
            x += Math.abs(spacing) + button.getRadius().getX() * 2;
        }
    }

    @Override
    public MuseRelativeRect setLeft(double value) {
        super.setLeft(value);
        init();
        return this.border;
    }

    @Override
    public void init(double left, double top, double right, double bottom) {
        this.setTargetDimensions(left, top, right, bottom);
        this.init();
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (button != 0)
            return false;

        for (ClickableButton b : buttons) {
            if (b.isEnabled() && b.hitBox(x, y)) {
                b.onPressed();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double v, double v1, int i) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double v, double v1, double v2) {
        return false;
    }

    @Override
    public void update(double v, double v1) {

    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        for (ClickableButton b : buttons) {
            b.render(matrixStack, mouseX, mouseY, partialTicks, getzLevel());
        }
    }

    @Override
    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
    }
}