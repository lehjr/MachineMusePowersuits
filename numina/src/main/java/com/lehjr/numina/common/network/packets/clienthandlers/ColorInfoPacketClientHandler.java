package com.lehjr.numina.common.network.packets.clienthandlers;

import com.lehjr.numina.common.capabilities.render.modelspec.IModelSpec;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class ColorInfoPacketClientHandler {
    public static void handlePacket(EquipmentSlot slotType, int[] tagData) {
            final Player player = Minecraft.getInstance().player;
            IModelSpec iModelSpec = ItemUtils.getItemFromEntitySlot(player, slotType).getCapability(NuminaCapabilities.RENDER);
            if(iModelSpec != null) {
                iModelSpec.setColorArray(tagData);
            }
    }
}
