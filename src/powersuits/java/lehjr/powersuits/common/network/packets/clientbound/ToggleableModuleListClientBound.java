package lehjr.powersuits.common.network.packets.clientbound;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.network.packets.clienthandlers.ToggleableModuleListClientHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record ToggleableModuleListClientBound(NonNullList<ResourceLocation> moduleNames) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ToggleableModuleListClientBound> ID = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "toggleable_module_list_to_client"));

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf,  ToggleableModuleListClientBound> STREAM_CODEC =
            StreamCodec.ofMember( ToggleableModuleListClientBound::write,  ToggleableModuleListClientBound::new);

    public ToggleableModuleListClientBound(RegistryFriendlyByteBuf packetBuffer) {
        this(getModuleList(packetBuffer));
    }

    public void write(RegistryFriendlyByteBuf packetBuffer) {
        packetBuffer.writeCollection(moduleNames, FriendlyByteBuf::writeResourceLocation);
    }

    static NonNullList<ResourceLocation> getModuleList(RegistryFriendlyByteBuf packetBuffer) {
        List<ResourceLocation> list = packetBuffer.readList(FriendlyByteBuf::readResourceLocation);
        return NonNullList.copyOf(list);
    }

    public static void handle(ToggleableModuleListClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ToggleableModuleListClientHandler.handlePacket(data, ctx);
            }
        });
    }
}
