package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class ColorInfoPacketClientHandler {
    public static void handlePacket(EquipmentSlot slotType, int[] tagData) {
            final Player player = Minecraft.getInstance().player;
            NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, slotType),
                    NuminaCapabilities.RENDER).ifPresent(render -> render.setColorArray(tagData));
    }
}
