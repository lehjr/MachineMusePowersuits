package com.lehjr.powersuits.common.network.packets.clientbound;

import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.network.packets.clienthandlers.SetSprintAssistDoubleClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record SetSprintAssistDoubleClientBound(double boost) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SetSprintAssistDoubleClientBound> ID = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "sprint_assist_value_request_to_client"));

    @Nonnull
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, SetSprintAssistDoubleClientBound> STREAM_CODEC =
        StreamCodec.ofMember(SetSprintAssistDoubleClientBound::write, SetSprintAssistDoubleClientBound::new);

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeDouble(boost);
    }

    public SetSprintAssistDoubleClientBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readDouble());
    }

    public static void handle(SetSprintAssistDoubleClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                SetSprintAssistDoubleClientHandler.handlePacket(data, ctx);
            }
        });
    }
}
