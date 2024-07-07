package lehjr.powersuits.client.gui.common;

import lehjr.numina.client.gui.clickable.button.VanillaButton;
import lehjr.numina.client.gui.frame.AbstractGuiFrame;
import lehjr.numina.client.gui.geometry.MusePoint2D;
import lehjr.numina.client.gui.geometry.Rect;
import lehjr.numina.client.sound.Musique;
import lehjr.numina.client.sound.SoundDictionary;
import lehjr.powersuits.client.gui.cosmetic.CosmeticGui;
import lehjr.powersuits.client.gui.keymap.TinkerKeymapGui;
import lehjr.powersuits.client.gui.module.tweak.ModuleTweakGui;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.serverbound.ContainerGuiOpenPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MachineMuse
 * <p>
 * Ported to Java by lehjr on 10/19/16.
 */
public class TabSelectFrame extends AbstractGuiFrame {
    Player player;
    List<VanillaButton> buttons = new ArrayList<>();

    public TabSelectFrame(double left, double bottom, double width, Player player, int active) {
        super(new Rect(left, bottom - 20, left + width, bottom));
        this.player = player;
        VanillaButton button;

        /** Install Salvage GUI (the only AbstractContainerMenued GUI)*/
        button = new VanillaButton(left(), top(), Component.translatable("gui.powersuits.tab.install.salvage"), active != 0);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            MPSPackets.sendToServer(new ContainerGuiOpenPacket(EquipmentSlot.HEAD, false, 0, 0));
        });
        buttons.add(button);

        /** Module Tweak Gui */
        button = new VanillaButton(left(), top(), Component.translatable("gui.powersuits.tab.module.tweak"), active != 1);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new ModuleTweakGui(Component.translatable("gui.tinkertable"))));
        });
        buttons.add(button);

        /** Keybind Gui */
        button = new VanillaButton(left(), top(), Component.translatable("gui.powersuits.tab.keybinds"), active !=2);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new TinkerKeymapGui(player, Component.translatable("gui.powersuits.tab.keybinds.toggle"))));
        });
        buttons.add(button);

        /** Cosmetic Tweak Frame */
        button = new VanillaButton(left(), top(), Component.translatable("gui.powersuits.tab.visual"), active !=3);
        button.setOnPressed(onPressed->{
            Musique.playClientSound(SoundDictionary.SOUND_EVENT_GUI_SELECT.get(), 1);
            Minecraft.getInstance().tell(() -> Minecraft.getInstance().setScreen(new CosmeticGui(player.getInventory(), Component.translatable("gui.tinkertable"))));
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
    public void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
        buttons.stream().forEach(b->b.render(gfx, mouseX, mouseY, partialTick));
    }
}