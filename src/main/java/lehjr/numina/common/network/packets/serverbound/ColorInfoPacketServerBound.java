package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.ColorInfoPacketClientBound;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

public record ColorInfoPacketServerBound(EquipmentSlot slotType, int[] tagData) implements CustomPacketPayload {
    public static final Type<ColorInfoPacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "color_info_to_server"));

    @Override
    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, ColorInfoPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(ColorInfoPacketServerBound::write, ColorInfoPacketServerBound::new);

    public ColorInfoPacketServerBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readVarIntArray());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeVarIntArray(tagData);
    }

    public static void sendToClient(ServerPlayer entity, EquipmentSlot slotType, int[] tagData) {
        NuminaPackets.sendToPlayer(new ColorInfoPacketClientBound(slotType, tagData), entity);
    }

    public static void handle(ColorInfoPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player  = ctx.player();
                NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, data.slotType), NuminaCapabilities.RENDER)
                        .ifPresent(render -> {
                            render.setColorArray(data.tagData);
                        });
//                player.containerMenu.broadcastChanges();
//                sendToClient((ServerPlayer) player, data.slotType, data.tagData); // this seems faster than letting changes propagate through player.containerMenu mechanics
        });
    }
}
