package lehjr.numina.common.network.packets.clientbound;

import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.packets.clienthandlers.ColorInfoPacketClientHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record ColorInfoPacketClientBound(EquipmentSlot slotType, int[] tagData) implements CustomPacketPayload {
    public static final Type<ColorInfoPacketClientBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "color_info_to_client"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, ColorInfoPacketClientBound> STREAM_CODEC =
            StreamCodec.ofMember(ColorInfoPacketClientBound::write, ColorInfoPacketClientBound::new);

    public ColorInfoPacketClientBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readVarIntArray());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeVarIntArray(tagData);
    }

    public static void handle(ColorInfoPacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ColorInfoPacketClientHandler.handlePacket(data.slotType, data.tagData);
            }
        });
    }
}