package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ToggleRequestPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record ToggleRequestPacketServerBound(ResourceLocation registryName, boolean toggleval) implements CustomPacketPayload {
    public static final Type<ToggleRequestPacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "ToggleRequestServer"));

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, ToggleRequestPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(ToggleRequestPacketServerBound::write, ToggleRequestPacketServerBound::new);

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(registryName);
        packetBuffer.writeBoolean(toggleval);
    }

    public ToggleRequestPacketServerBound(FriendlyByteBuf packetBuffer) {
            this(packetBuffer.readResourceLocation(), packetBuffer.readBoolean()
        );
    }

    public static void sendToClient(ServerPlayer entity, ResourceLocation registryName, boolean active) {
        NuminaPackets.sendToPlayer(new ToggleRequestPacketClientBound(registryName, active), entity);
    }

    public static void handle(ToggleRequestPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    NuminaCapabilities.getCapability(player.getInventory().getItem(i), NuminaCapabilities.Inventory.MODULAR_ITEM)
                            .ifPresent(handler -> handler.toggleModule(data.registryName, data.toggleval));
                        // fixme:?
//                    NuminaCapabilities.getCapability(player.getInventory().getItem(i), NuminaCapabilities.MODE_CHANGING_MODULAR_ITEM)
//                            .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
                }
                sendToClient((ServerPlayer) player, data.registryName, data.toggleval);
        });
    }
}