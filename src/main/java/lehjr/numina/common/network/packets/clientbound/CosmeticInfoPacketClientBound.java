package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.item.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record CosmeticInfoPacketClientBound(EquipmentSlot slotType, String tagName, CompoundTag tagData) {

    public static void encode(CosmeticInfoPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeUtf(msg.tagName);
        packetBuffer.writeNbt(msg.tagData);
    }

    public static CosmeticInfoPacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new CosmeticInfoPacketClientBound(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readUtf(500),
                packetBuffer.readNbt());
    }

    public static class Handler {
        public static void handle(CosmeticInfoPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                EquipmentSlot slotType = message.slotType;
                String tagName = message.tagName;
                CompoundTag tagData = message.tagData;

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
}