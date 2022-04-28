package com.lehjr.numina.common.network.packets;

import net.minecraft.nbt.StringTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkEvent;
import com.lehjr.numina.api.capabilities.inventory.modechanging.IModeChangingItem;
import com.lehjr.numina.api.constants.TagConstants;

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
                        handler.getActiveModule().addTagElement(TagConstants.BLOCK, StringTag.valueOf(message.regName.toString()));
                    });
        });
        ctx.get().setPacketHandled(true);
    }
}

