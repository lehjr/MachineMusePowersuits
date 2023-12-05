package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.packets.clientbound.CosmeticInfoPacketClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CosmeticInfoPacketClientHandler {
    public static void handlePacket(CosmeticInfoPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            EquipmentSlot slotType = message.getSlotType();
            String tagName = message.getTagName();
            CompoundTag tagData = message.getTagData();

            Player player = Minecraft.getInstance().player;
            if (player != null) {
                ItemUtils.getItemFromEntitySlot(player, slotType).getCapability(NuminaCapabilities.RENDER).ifPresent(render -> {
                    render.setRenderTag(tagData, tagName);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
