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
import lehjr.numina.util.client.gui.clickable.ClickableButton2;
import lehjr.numina.util.client.gui.frame.GuiFrameWithoutBackground;
import lehjr.numina.util.client.gui.gemoetry.IRect;
import lehjr.numina.util.client.gui.gemoetry.MusePoint2D;
import lehjr.numina.util.client.sound.Musique;
import lehjr.numina.util.client.sound.SoundDictionary;
import lehjr.powersuits.client.gui.keybind.TinkerKeybindGui;
import lehjr.powersuits.client.gui.modding.cosmetic.CosmeticGui;
import lehjr.powersuits.client.gui.modding.module.tweak.ModuleTweakGui;
import lehjr.powersuits.network.MPSPackets;
import lehjr.powersuits.network.packets.ContainerGuiOpenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class TabSelectFrame extends GuiFrameWithoutBackground {
    PlayerEntity player;
    List<ClickableButton2> buttons = new ArrayList<>();

    public TabSelectFrame(PlayerEntity player, int active) {
        super();
        this.player = player;
        ClickableButton2 button;

//        /** Craft Install Salvage GUI (the only Containered GUI)*/
//        button = new ClickableButton2(new TranslationTextComponent("gui.powersuits.tab.craft.install.salvage"), new MusePoint2D(0, 0), active != 0);
//        button.setOnPressed(onPressed->{
//            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
//            MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(0));
//        });
//        buttons.add(button);

        /** Install Salvage GUI (the only Containered GUI)*/
        button = new ClickableButton2(new TranslationTextComponent("gui.powersuits.tab.install.salvage"), new MusePoint2D(0, 0), active != 0);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            MPSPackets.CHANNEL_INSTANCE.sendToServer(new ContainerGuiOpenPacket(EquipmentSlotType.MAINHAND));
        });
        buttons.add(button);

        /** Module Tweak Gui */
        button = new ClickableButton2(new TranslationTextComponent("gui.powersuits.tab.module.tweak"), new MusePoint2D(0, 0), active != 1);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(new TranslationTextComponent("gui.tinkertable"), false)));
        });
        buttons.add(button);

        /** Keybind Gui */
        button = new ClickableButton2(new TranslationTextComponent("gui.powersuits.tab.keybinds"), new MusePoint2D(0, 0), active !=2);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new TinkerKeybindGui(player, new TranslationTextComponent("gui.powersuits.tab.keybinds.toggle"))));
        });
        buttons.add(button);

        /** Cosmetic Tweak Frame */
        button = new ClickableButton2(new TranslationTextComponent("gui.powersuits.tab.visual"), new MusePoint2D(0, 0), active !=3);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT, 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new CosmeticGui(player.inventory, new TranslationTextComponent("gui.tinkertable"))));
        });
        buttons.add(button);

        for(ClickableButton2 b : buttons) {
            b.setVisible(true);
        }
        setDoThisOnChange(change->init());
    }

    public void initFromBackgroundRect(IRect background) {
        super.init(background.finalLeft(), background.finalTop(), background.finalRight(), background.finalBottom());
    }

    private void init() {
        double totalButtonWidth = 0;
        for (ClickableButton2 button : buttons) {
            totalButtonWidth += (button.getRadius().getX() * 2);
        }
        // totalButtonWidth greater than width will produce a negative spacing value
        double spacing = (this.width() - totalButtonWidth) / (buttons.size() +1);

        double x = spacing; // first entry may be negative and will allow an oversized tab frame to be centered
        for (ClickableButton2 button : buttons) {
            button.setPosition(new MusePoint2D(this.finalLeft() + x + button.getRadius().getX(), this.finalTop() -6));
            x += Math.abs(spacing) + button.getRadius().getX() * 2;
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (button != 0) {
            return false;
        }
        return buttons.stream().anyMatch(b ->b.mouseClicked(x, y, button));
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
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        buttons.stream().forEach(b->b.render(matrixStack, mouseX, mouseY, partialTicks));
    }

//    @Override
//    public void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
//        buttons.stream().forEach(b->b.renderText(matrixStack, mouseX, mouseY));
//    }

    @Override
    public List<ITextComponent> getToolTip(int i, int i1) {
        return null;
    }
}