package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.BlockNamePacketClientBound;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public record BlockNamePacketServerBound(ResourceLocation regName) {
    public static void encode(BlockNamePacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static BlockNamePacketServerBound decode(FriendlyByteBuf packetBuffer) {
        return new BlockNamePacketServerBound(packetBuffer.readResourceLocation());
    }

    public static void sendToClient(ServerPlayer entity, ResourceLocation regName) {
        NuminaPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new BlockNamePacketClientBound(regName));
    }

    public static class Handler {
        public static void handle(BlockNamePacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final ServerPlayer player = ctx.get().getSender();
                ResourceLocation regName = message.regName;

                if (player != null && regName != null) {
                    player.getItemBySlot(EquipmentSlot.MAINHAND).getCapability(ForgeCapabilities.ITEM_HANDLER).filter(IModeChangingItem.class::isInstance)
                            .map(IModeChangingItem.class::cast)
                            .ifPresent(handler-> {
                                TagUtils.setModuleResourceLocation(handler.getActiveModule(), TagConstants.BLOCK, regName);
                            });
                    sendToClient((ServerPlayer) player, regName);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
