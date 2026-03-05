package lehjr.powersuits.common.network.packets.clienthandlers;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.network.packets.clientbound.SetSprintAssistDoubleClientBound;
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
