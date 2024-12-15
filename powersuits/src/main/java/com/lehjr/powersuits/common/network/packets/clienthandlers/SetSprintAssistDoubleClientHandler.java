package com.lehjr.powersuits.common.network.packets.clienthandlers;

import com.lehjr.numina.common.base.NuminaLogger;
import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.network.packets.clientbound.SetSprintAssistDoubleClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SetSprintAssistDoubleClientHandler {
    public static void handlePacket(SetSprintAssistDoubleClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            double value = data.boost();

            IModularItem iModularItem = NuminaCapabilities.getModularItem(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.LEGS));
            if(iModularItem != null) {
                if (iModularItem.setModuleDouble(MPSConstants.SPRINT_ASSIST_MODULE, MPSConstants.MOVEMENT_SPEED, value)) {
                    NuminaLogger.logDebug("set sprint value client side: " + value);
                }
            }
        });
    }
}
