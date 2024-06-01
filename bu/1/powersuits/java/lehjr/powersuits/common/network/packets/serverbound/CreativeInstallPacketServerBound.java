package lehjr.powersuits.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public record CreativeInstallPacketServerBound(EquipmentSlot slotType, ResourceLocation regName) {

    public static void write(CreativeInstallPacketServerBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static CreativeInstallPacketServerBound read(FriendlyByteBuf packetBuffer) {
        return new CreativeInstallPacketServerBound(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readResourceLocation());
    }

    public static void sendToClient(ServerPlayer entity, EquipmentSlot slotType, ResourceLocation regName) {
        MPSPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> entity),
                new CreativeInstallPacketClientBound(slotType, regName));
    }

    public static class Handler {
        public static void handle(CreativeInstallPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final Player player = ctx.get().getSender();
                EquipmentSlot slotType = message.slotType;
                ResourceLocation regName = message.regName;
                Item item = ForgeRegistries.ITEMS.getValue(regName);
                ItemStack module = new ItemStack(item, 1);

                module.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> iEnergyStorage.receiveEnergy(iEnergyStorage.getMaxEnergyStored(), false));

                ItemUtils.getItemFromEntitySlot(player, slotType).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .ifPresent(iModularItem -> {
                            if (!iModularItem.getInstalledModuleNames().contains(regName)) {
                                for (int index = 0; index < iModularItem.getSlots(); index++) {
                                    if (iModularItem.insertItem(index, module, false).isEmpty()) {
                                        break;
                                    }
                                }
                            }
                        });

                if (player instanceof ServerPlayer) {
                    sendToClient((ServerPlayer) player, slotType, regName);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}