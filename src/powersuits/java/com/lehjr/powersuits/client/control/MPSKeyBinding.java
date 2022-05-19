package com.lehjr.powersuits.client.control;

import com.lehjr.numina.common.network.NuminaPackets;
import com.lehjr.numina.common.network.packets.ToggleRequestPacket;
import com.lehjr.powersuits.client.event.RenderEventHandler;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

public class MPSKeyBinding extends KeyMapping {
    public final ResourceLocation registryName;
    public boolean showOnHud = true;
    public boolean toggleval = false;

    public MPSKeyBinding(ResourceLocation registryName, String name, int key, String category) {
        super(name, key, category);
        this.registryName = registryName;
    }

    /**
     * Do not use this
     */
    @Override
    public void setKey(InputConstants.Key key) {
        super.setKey(key);
        KeybindManager.INSTANCE.writeOutKeybindSetings();
        RenderEventHandler.INSTANCE.makeKBDisplayList();
    }

    /**
     * Use this one to set the key from inside MPS
     * @param key
     */
    public void setKeyInternal(InputConstants.Key key) {
        super.setKey(key);
    }


    public void toggleModules() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
    // FIXME: needed client side?
//        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
//            player.getInventory().getItem(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                    .filter(IModularItem.class::isInstance)
//                    .map(IModularItem.class::cast)
//                    .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
//        }
        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ToggleRequestPacket(registryName, toggleval));

        toggleval = !toggleval;
    }
}