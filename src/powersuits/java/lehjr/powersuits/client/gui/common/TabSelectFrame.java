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

package lehjr.powersuits.client.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.frame.fixed.AbstractGuiFrame;
import lehjr.numina.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.client.gui.gemoetry.Rect;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.powersuits.client.gui.keybind.TinkerKeybindGui;
import lehjr.powersuits.client.gui.modding.cosmetic.CosmeticGui;
import lehjr.powersuits.client.gui.modding.module.tweak.ModuleTweakGui;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.ContainerGuiOpenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class TabSelectFrame extends AbstractGuiFrame {
    PlayerEntity player;
    List<VanillaButton> buttons = new ArrayList<>();

    public TabSelectFrame(double left, double bottom, double width, PlayerEntity player, int active) {
        super(new Rect(left, bottom - 20, left + width, bottom));
        this.player = player;
        VanillaButton button;
        System.out.println("active: " + active);


//        /** TODO? Craft Install Salvage GUI (the only Containered GUI)*/
//        button = new ClickableButton2(new TranslationTextComponent("gui.powersuits.tab.craft.install.salvage"), new MusePoint2D(0, 0), active != 0);
//        button.setOnPressed(onPressed->{
//            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//            MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(0));
//        });
//        buttons.add(button);

        /** Install Salvage GUI (the only Containered GUI)*/
        button = new VanillaButton(left(), top(), new TranslationTextComponent("gui.powersuits.tab.install.salvage"), active != 0);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(EquipmentSlotType.MAINHAND));
        });
        buttons.add(button);

        /** Module Tweak Gui */
        button = new VanillaButton(left(), top(), new TranslationTextComponent("gui.powersuits.tab.module.tweak"), active != 1);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(new TranslationTextComponent("gui.tinkertable"), false)));
        });
        buttons.add(button);

        /** Keybind Gui */
        button = new VanillaButton(left(), top(), new TranslationTextComponent("gui.powersuits.tab.keybinds"), active !=2);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new TinkerKeybindGui(player, new TranslationTextComponent("gui.powersuits.tab.keybinds.toggle"))));
        });
        buttons.add(button);

        /** Cosmetic Tweak Frame */
        button = new VanillaButton(left(), top(), new TranslationTextComponent("gui.powersuits.tab.visual"), active !=3);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new CosmeticGui(player.inventory, new TranslationTextComponent("gui.tinkertable"))));
        });
        buttons.add(button);

        for(VanillaButton b : buttons) {
            b.setVisible(true);
        }

        double totalButtonWidth = 0;
        for (VanillaButton button1 : buttons) {
            totalButtonWidth += button1.width();
        }
        // totalButtonWidth greater than width will produce a negative spacing value
        double spacing = (this.width() - totalButtonWidth) / (buttons.size() +1);

        double x = spacing; // first entry may be negative and will allow an oversized tab frame to be centered
        for (VanillaButton button1 : buttons) {
            button1.setPosition(new MusePoint2D(this.left() + x + button1.width() * 0.5, centerY()));
            int index = buttons.indexOf(button1);
            x += Math.abs(spacing) + button1.width();
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        return buttons.stream().anyMatch(b ->b.mouseClicked(x, y, button));
    }

    @Override
    public boolean mouseReleased(double x, double y, int button) {
        return buttons.stream().anyMatch(b ->b.mouseReleased(x, y, button));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        buttons.stream().forEach(b->b.render(matrixStack, mouseX, mouseY, partialTicks));
    }
}