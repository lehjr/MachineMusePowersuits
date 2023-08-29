package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.TweakRequestDoublePacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record TweakRequestDoublePacketServerBound(EquipmentSlot type, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
    public static void encode(TweakRequestDoublePacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.type);
        packetBuffer.writeResourceLocation(msg.moduleRegName);
        packetBuffer.writeUtf(msg.tweakName);
        packetBuffer.writeDouble(msg.tweakValue);
    }

    public static TweakRequestDoublePacketServerBound decode(FriendlyByteBuf packetBuffer) {
        return new TweakRequestDoublePacketServerBound(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readResourceLocation(),
                packetBuffer.readUtf(500),
                packetBuffer.readDouble());
    }

    public static void sendToClient(ServerPlayer entity, EquipmentSlot type, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new TweakRequestDoublePacketClientBound(type, moduleRegName, tweakName, tweakValue));
    }

    public static class Handler {
        public static void handle(TweakRequestDoublePacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final ServerPlayer player = ctx.get().getSender();
                ResourceLocation moduleRegName = message.moduleRegName;
                String tweakName = message.tweakName;
                double tweakValue = message.tweakValue;
                if (moduleRegName != null && tweakName != null) {
                    EquipmentSlot type = message.type;
                    player.getItemBySlot(type).getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .ifPresent(iItemHandler -> {
                                iItemHandler.setModuleTweakDouble(moduleRegName, tweakName, tweakValue);
                            });
                    sendToClient((ServerPlayer) player, type, moduleRegName, tweakName, tweakValue);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}