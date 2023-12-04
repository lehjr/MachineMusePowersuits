package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.item.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ColorInfoPacketClientBound(EquipmentSlot slotType, int[] tagData) {

    public static void encode(ColorInfoPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeVarIntArray(msg.tagData);
    }

    public static ColorInfoPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new ColorInfoPacketClientBound(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readVarIntArray());
    }

    public static class Handler {
        public static void handle(ColorInfoPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final Player player = Minecraft.getInstance().player;
                EquipmentSlot slotType = message.slotType;
                int[] tagData = message.tagData;
                ItemUtils.getItemFromEntitySlot(player, slotType)
                        .getCapability(NuminaCapabilities.RENDER)
                        .ifPresent(render -> render.setColorArray(tagData));
            });
            ctx.get().setPacketHandled(true);
        }
    }
}