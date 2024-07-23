package lehjr.powersuits.common.network.packets.serverbound;

import lehjr.numina.common.base.NuminaLogger;
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
public record ContainerGuiOpenPacket(EquipmentSlot slotType, boolean preserve, double mouseX, double mouseY) implements CustomPacketPayload {
    public static final Type<ContainerGuiOpenPacket> ID = new Type<>(new ResourceLocation(MPSConstants.MOD_ID, "container_gui_open_to_server"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, ContainerGuiOpenPacket> STREAM_CODEC =
            StreamCodec.ofMember(ContainerGuiOpenPacket::write, ContainerGuiOpenPacket::new);

    public ContainerGuiOpenPacket(RegistryFriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class),
                packetBuffer.readBoolean(),
                packetBuffer.readDouble(),
                packetBuffer.readDouble());
    }

    public void write(RegistryFriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeBoolean(preserve);
        packetBuffer.writeDouble(mouseX);
        packetBuffer.writeDouble(mouseY);
        NuminaLogger.logDebug("WRITING: reserve: " + preserve + ", mouseX: " + mouseX + ", mouseY: " + mouseY);
    }

    // FIXME ... cleanup failed attempt at setting mouse coordinates
    public static void handle(ContainerGuiOpenPacket data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            SimpleMenuProvider container;
            NuminaLogger.logDebug("HANDLING: reserve: " + data.preserve + ", mouseX: " + data.mouseX + ", mouseY: " + data.mouseY);

            if(data.preserve()) {
                NuminaLogger.logDebug("preserving mouse");
                container = new SimpleMenuProvider((id, inventory, player) ->
                        new InstallSalvageMenu(id, inventory,
                                data.slotType,
                                data.preserve,
                                data.mouseX,
                                data.mouseY),
                        Component.translatable(MPSConstants.GUI_INSTALL_SALVAGE));
            } else {
                NuminaLogger.logDebug("not preserving mouse");
                container = new SimpleMenuProvider((id, inventory, player) ->
                        new InstallSalvageMenu(id, inventory, data.slotType),
                        Component.translatable(MPSConstants.GUI_INSTALL_SALVAGE));
            }

            ctx.player().openMenu(container, buf -> {
                buf.writeEnum(data.slotType);
                buf.writeBoolean(data.preserve);
                buf.writeDouble(data.mouseX);
                buf.writeDouble(data.mouseY);
            });
        });
    }
}