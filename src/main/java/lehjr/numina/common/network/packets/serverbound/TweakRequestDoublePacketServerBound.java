package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.TweakRequestDoublePacketClientBound;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record TweakRequestDoublePacketServerBound(EquipmentSlot slotType, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
    public static void encode(TweakRequestDoublePacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
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
        NuminaPackets.sendToPlayer(new TweakRequestDoublePacketClientBound(type, moduleRegName, tweakName, tweakValue),  entity);
    }

    public static class Handler {
        public static void handle(TweakRequestDoublePacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final ServerPlayer player = ctx.get().getSender();
                ResourceLocation moduleRegName = message.moduleRegName;
                String tweakName = message.tweakName;
                double tweakValue = message.tweakValue;
                if (moduleRegName != null && tweakName != null) {
                    EquipmentSlot type = message.slotType;
                    ItemStack stack = ItemUtils.getItemFromEntitySlot(player, message.slotType);
                    IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(stack);
                    if (iModularItem != null) {
                        iModularItem.setModuleDouble(message.moduleRegName, message.tweakName, message.tweakValue);
                        sendToClient(player, message.slotType, message.moduleRegName, message.tweakName, message.tweakValue);
                    }
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}