package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class CosmeticInfoPacketClientHandler {
    public static void handlePacket(EquipmentSlot slotType, String tagName, CompoundTag tagData) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, slotType), NuminaCapabilities.RENDER).ifPresent(render -> {
                render.setRenderTag(tagData, tagName);
            });
        }
    }
}
