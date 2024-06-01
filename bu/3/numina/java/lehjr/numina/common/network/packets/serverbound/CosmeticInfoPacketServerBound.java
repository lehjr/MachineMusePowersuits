package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

/**
 * Used when changing cosmetic settings.
 * @param slotType
 * @param tagName
 * @param tagData
 */
public record CosmeticInfoPacketServerBound(EquipmentSlot slotType, String tagName, CompoundTag tagData) implements CustomPacketPayload {
    public static final Type<CosmeticInfoPacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, " CosmeticInfoServer"));

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, CosmeticInfoPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(CosmeticInfoPacketServerBound::write, CosmeticInfoPacketServerBound::new);

    public CosmeticInfoPacketServerBound(FriendlyByteBuf packetBuffer) {
        this(
                packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readUtf(500),
                packetBuffer.readNbt());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeUtf(tagName);
        packetBuffer.writeNbt(tagData);
    }

    public static void sendToClient(ServerPlayer entity, EquipmentSlot slotType, String tagName, CompoundTag tagData) {
        NuminaPackets.sendToPlayer(new CosmeticInfoPacketServerBound(slotType, tagName, tagData), entity);
    }

    public static void handle(CosmeticInfoPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, data.slotType),
                    NuminaCapabilities.RENDER).ifPresent(render -> render.setRenderTag(data.tagData, data.tagName));
//                    player.containerMenu.broadcastChanges();
            sendToClient((ServerPlayer) player, data.slotType, data.tagName, data.tagData);
        });
    }
}