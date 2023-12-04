package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.item.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record TweakRequestDoublePacketClientBound(EquipmentSlot type, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
    public static void encode(TweakRequestDoublePacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.type);
        packetBuffer.writeResourceLocation(msg.moduleRegName);
        packetBuffer.writeUtf(msg.tweakName);
        packetBuffer.writeDouble(msg.tweakValue);
    }

    public static TweakRequestDoublePacketClientBound decode(FriendlyByteBuf packetBuffer) {
        return new TweakRequestDoublePacketClientBound(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readResourceLocation(),
                packetBuffer.readUtf(500),
                packetBuffer.readDouble());
    }

    public static class Handler {
        public static void handle(TweakRequestDoublePacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final Player player = Minecraft.getInstance().player;
                ResourceLocation moduleRegName = message.moduleRegName;
                String tweakName = message.tweakName;
                double tweakValue = message.tweakValue;
                if (moduleRegName != null && tweakName != null) {
                    EquipmentSlot type = message.type;
                    ItemUtils.getItemFromEntitySlot(player, type).getCapability(ForgeCapabilities.ITEM_HANDLER)
                            .filter(IModularItem.class::isInstance)
                            .map(IModularItem.class::cast)
                            .ifPresent(iItemHandler -> {
                                iItemHandler.setModuleTweakDouble(moduleRegName, tweakName, tweakValue);
                            });
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
