package com.github.lehjr.powersuits.network.packets;

import com.github.lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CreativeInstallPacket {
    protected EquipmentSlotType slotType;
    protected ResourceLocation regName;

    public CreativeInstallPacket() {
    }

    public CreativeInstallPacket(EquipmentSlotType slotType, ResourceLocation regName) {
        this.slotType = slotType;
        this.regName = regName;
    }

    public static void write(CreativeInstallPacket msg, PacketBuffer packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static CreativeInstallPacket read(PacketBuffer packetBuffer) {
        return new CreativeInstallPacket(packetBuffer.readEnum(EquipmentSlotType.class), packetBuffer.readResourceLocation());
    }

    public static void handle(CreativeInstallPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();
            EquipmentSlotType slotType = message.slotType;
            ResourceLocation regName = message.regName;
            Item item = ForgeRegistries.ITEMS.getValue(regName);
            ItemStack module = new ItemStack(item, 1);

            player.getItemBySlot(slotType).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
                    .filter(IModularItem.class::isInstance)
                    .map(IModularItem.class::cast)
                    .ifPresent(iModularItem -> {
                        if (!iModularItem.getInstalledModuleNames().contains(regName)) {
                            for (int index = 0; index < iModularItem.getSlots(); index++) {
                                if (iModularItem.insertItem(index, module, false).sameItem(ItemStack.EMPTY)) {
                                    break;
                                }
                            }
                        }
                    });
        });
        ctx.get().setPacketHandled(true);
    }
}