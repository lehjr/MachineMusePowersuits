package lehjr.powersuits.common.network.packets.serverbound;

import lehjr.powersuits.common.constants.MPSConstants;
import lehjr.powersuits.common.container.InstallSalvageMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * A packet for sending a containerGui open request from the client side.
 */
public record ContainerGuiOpenPacketServerBound(EquipmentSlot slotType, boolean shouldpreserve, double mouseX, double mouseY) implements CustomPacketPayload {
    public static final Type<ContainerGuiOpenPacketServerBound> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "container_gui_open_to_server"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, ContainerGuiOpenPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(ContainerGuiOpenPacketServerBound::write, ContainerGuiOpenPacketServerBound::new);

    public ContainerGuiOpenPacketServerBound(RegistryFriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readBoolean(), packetBuffer.readDouble(),  packetBuffer.readDouble());
    }

    public void write(RegistryFriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeBoolean(shouldpreserve);
        packetBuffer.writeDouble(mouseX);
        packetBuffer.writeDouble(mouseY);
    }

    public static void handle(ContainerGuiOpenPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ctx.player().openMenu(new SimpleMenuProvider((id, inventory, player) ->
                new InstallSalvageMenu(id, inventory, data.slotType),
                Component.translatable(MPSConstants.GUI_INSTALL_SALVAGE)), buf -> {
                    buf.writeEnum(data.slotType);
                    buf.writeBoolean(data.shouldpreserve);
                    buf.writeDouble(data.mouseX);
                    buf.writeDouble(data.mouseY);
                });
        });
    }
}
