package lehjr.numina.network.packets;

import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.inventory.EquipmentSlot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

public class BlockNamePacket {
    protected ResourceLocation regName;

    public BlockNamePacket() {
    }

    public BlockNamePacket(ResourceLocation regName) {
        this.regName = regName;
    }

    public static void write(BlockNamePacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static BlockNamePacket read(PacketBuffer packetBuffer) {
        return new BlockNamePacket(packetBuffer.readResourceLocation());
    }

    // Only set up to get for the AoE2 module, for now
    public static void handle(BlockNamePacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayer player = ctx.get().getSender();
            player.getItemBySlot(EquipmentSlot.MAINHAND).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> {
                        MuseNBTUtils.setModuleResourceLocation(handler.getActiveModule(), "block", message.regName);
                    });
        });
        ctx.get().setPacketHandled(true);
    }
}

