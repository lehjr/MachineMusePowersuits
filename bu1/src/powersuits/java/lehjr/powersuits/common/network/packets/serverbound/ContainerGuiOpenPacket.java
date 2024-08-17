package lehjr.powersuits.common.network.packets.serverbound;

import com.lehjr.numina.common.base.NuminaLogger;
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
public record ContainerGuiOpenPacket(EquipmentSlot slotType) implements CustomPacketPayload {
    public static final Type<ContainerGuiOpenPacket> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "container_gui_open_to_server"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, ContainerGuiOpenPacket> STREAM_CODEC =
            StreamCodec.ofMember(ContainerGuiOpenPacket::write, ContainerGuiOpenPacket::new);

    public ContainerGuiOpenPacket(RegistryFriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class));
    }

    public void write(RegistryFriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
    }

    public static void handle(ContainerGuiOpenPacket data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            SimpleMenuProvider container;

                NuminaLogger.logDebug("not preserving mouse");
                container = new SimpleMenuProvider((id, inventory, player) ->
                        new InstallSalvageMenu(id, inventory, data.slotType),
                        Component.translatable(MPSConstants.GUI_INSTALL_SALVAGE));

            ctx.player().openMenu(container, buf -> buf.writeEnum(data.slotType));
        });
    }
}