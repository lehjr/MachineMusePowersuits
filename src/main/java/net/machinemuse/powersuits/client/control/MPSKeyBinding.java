package net.machinemuse.powersuits.client.control;

import net.machinemuse.powersuits.client.event.RenderEventHandler;
import net.machinemuse.powersuits.common.network.MPSPackets;
import net.machinemuse.powersuits.common.network.packets.MusePacketToggleRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyModifier;

public class MPSKeyBinding extends KeyBinding {
    public final String dataName;
    public boolean showOnHud = true;
    public boolean toggleval = false;

    public MPSKeyBinding(String dataName, String description, int key, String category) {
        super(description, key, category);
        this.dataName = dataName;
    }

    public MPSKeyBinding(KeyBinding keybinding, String dataName, boolean showOnHud) {
        super(keybinding.getKeyDescription(), keybinding.getKeyCode(), keybinding.getKeyCategory());
        this.dataName = dataName;
        this.showOnHud = showOnHud;
    }

    public MPSKeyBinding(String dataName, String description, int keyCode, String category, boolean showOnHud) {
        super(description, keyCode, category);
        this.dataName = dataName;
        this.showOnHud = showOnHud;
    }

    @Override
    public void setKeyCode(int keyCode) {
        super.setKeyCode(keyCode);
        KeybindManager.INSTANCE.writeOutKeybinds();
        RenderEventHandler.makeKBDisplayList();
    }

    public void setKeyModifierAndCodeInternal(KeyModifier keyModifier, int keyCode) {
        super.setKeyModifierAndCode(keyModifier, keyCode);
    }

    @Override
    public void setKeyModifierAndCode(KeyModifier keyModifier, int keyCode) {
        super.setKeyModifierAndCode(keyModifier, keyCode);
        KeybindManager.INSTANCE.writeOutKeybinds();
        RenderEventHandler.makeKBDisplayList();
    }

    /**
     * Use this one to set the key from inside MPS
     * @param keyCode
     */
    public void setKeyInternal(int keyCode) {
        super.setKeyCode(keyCode);
    }

    public void toggleModules() {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (player == null) {
            return;
        }

        MPSPackets.sendToServer(new MusePacketToggleRequest(player, dataName, toggleval));
        toggleval = !toggleval;
    }
}