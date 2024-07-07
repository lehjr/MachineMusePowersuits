package lehjr.powersuits.common.network.packets.clientbound;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.network.packets.clienthandlers.CreativeInstallPacketClientHandler;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CreativeInstallPacketClientBound (EquipmentSlot slotType, ResourceLocation regName) implements CustomPacketPayload {
    public static final Type<CreativeInstallPacketClientBound> ID = new Type<>(new ResourceLocation(MPSConstants.MOD_ID, "creative_install_to_client"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf,  CreativeInstallPacketClientBound> STREAM_CODEC =
            StreamCodec.ofMember( CreativeInstallPacketClientBound::write,  CreativeInstallPacketClientBound::new);

    public CreativeInstallPacketClientBound(RegistryFriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readResourceLocation());
    }

    public void write(RegistryFriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeResourceLocation(regName);
    }

    public static void handle(CreativeInstallPacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                CreativeInstallPacketClientHandler.handlePacket(data, ctx);
            }
        });
    }
}