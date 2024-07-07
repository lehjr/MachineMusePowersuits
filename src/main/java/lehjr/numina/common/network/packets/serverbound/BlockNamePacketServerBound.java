package lehjr.numina.common.network.packets.serverbound;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.network.NuminaPackets;
import lehjr.numina.common.network.packets.clientbound.BlockNamePacketClientBound;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record BlockNamePacketServerBound(ResourceLocation regName) implements CustomPacketPayload {
    public static final Type<BlockNamePacketServerBound> ID = new Type<>(new ResourceLocation(NuminaConstants.MOD_ID, "block_name_to_sever"));

    @Override
    @NotNull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, BlockNamePacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(BlockNamePacketServerBound::write, BlockNamePacketServerBound::new);

    public BlockNamePacketServerBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readResourceLocation());
    }

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(regName);
    }

    public static void sendToClient(ServerPlayer entity, ResourceLocation regName) {
        NuminaPackets.sendToPlayer(new BlockNamePacketClientBound(regName), entity);
    }

    public static void handle(BlockNamePacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            if (player instanceof ServerPlayer && data.regName != null) {
                NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.MAINHAND),
                                NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                        .ifPresent(handler -> TagUtils.setModuleResourceLocation(handler.getActiveModule(), NuminaConstants.BLOCK, data.regName));
                sendToClient((ServerPlayer) player, data.regName);
            }
        });
    }
}
