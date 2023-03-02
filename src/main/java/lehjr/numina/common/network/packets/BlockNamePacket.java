package lehjr.numina.common.network.packets;

import lehjr.numina.common.base.NuminaLogger;
import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.capabilities.render.chameleon.Chameleon;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlockNamePacket {
    protected ResourceLocation regName;

    public BlockNamePacket() {
    }

    public BlockNamePacket(ResourceLocation regName) {
        this.regName = regName;
    }

    public static void write(BlockNamePacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static BlockNamePacket read(FriendlyByteBuf packetBuffer) {
        return new BlockNamePacket(packetBuffer.readResourceLocation());
    }

    // Only set up to get for the AoE2 module, for now
    public static void handle(BlockNamePacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayer player = ctx.get().getSender();
            player.getItemBySlot(EquipmentSlot.MAINHAND).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).filter(IModeChangingItem.class::isInstance)
                    .map(IModeChangingItem.class::cast)
                    .ifPresent(handler-> {
                        NuminaLogger.logError("module before: " + handler.getActiveModule().serializeNBT());

                        TagUtils.setModuleResourceLocation(handler.getActiveModule(), TagConstants.BLOCK, message.regName);

                        NuminaLogger.logError("module before: " + handler.getActiveModule().serializeNBT());
                    });
            player.getInventory().setChanged();
        });
        ctx.get().setPacketHandled(true);
    }
}
