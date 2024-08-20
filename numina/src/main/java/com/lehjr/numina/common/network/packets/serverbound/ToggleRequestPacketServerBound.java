package com.lehjr.numina.common.network.packets.serverbound;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.constants.NuminaConstants;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record ToggleRequestPacketServerBound(ResourceLocation registryName, boolean toggleval) implements CustomPacketPayload {
    public static final Type<ToggleRequestPacketServerBound> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(NuminaConstants.MOD_ID, "toggle_request_to_server"));

    @Override
    @Nonnull
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

    public static void handle(ToggleRequestPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
                for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                    IModularItem modularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(player.getInventory().getItem(i));
                    if(modularItem != null) {
                        modularItem.toggleModule(data.registryName, data.toggleval);
                    }
                }
        });
    }
}
