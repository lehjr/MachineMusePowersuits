package com.github.lehjr.powersuits.client.control;

import com.github.lehjr.numina.network.NuminaPackets;
import com.github.lehjr.numina.network.packets.ToggleRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.ResourceLocation;

public class MPSKeyBinding extends KeyBinding {
    public final ResourceLocation registryName;
    public boolean showOnHud = false;
    public boolean toggleval = false;

    public MPSKeyBinding(ResourceLocation registryName, String name, int key, String category) {
        super(name, key, category);
        this.registryName = registryName;
    }

    @Override
    public void setKey(InputMappings.Input key) {
        super.setKey(key);
    }


    public void toggleModules() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }

//        for (int i = 0; i < player.inventory.getContainerSize(); i++) {
//            player.inventory.getItem(i).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
//                    .filter(IModularItem.class::isInstance)
//                    .map(IModularItem.class::cast)
//                    .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
//        }
        NuminaPackets.CHANNEL_INSTANCE.sendToServer(new ToggleRequestPacket(registryName, toggleval));

        toggleval = !toggleval;
    }
}