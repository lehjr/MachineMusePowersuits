package lehjr.numina.network.packets;

import lehjr.numina.util.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.util.nbt.MuseNBTUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
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
            final ServerPlayerEntity player = ctx.get().getSender();
            player.getItemBySlot(EquipmentSlotType.MAINHAND).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> {
                        System.out.println("tag before: " + handler.getActiveModule().serializeNBT());


                        MuseNBTUtils.setModuleResourceLocation(handler.getActiveModule(), "block", message.regName);

                        System.out.println("tag after: " + handler.getActiveModule().serializeNBT());
                    });
        });
        ctx.get().setPacketHandled(true);
    }
}

