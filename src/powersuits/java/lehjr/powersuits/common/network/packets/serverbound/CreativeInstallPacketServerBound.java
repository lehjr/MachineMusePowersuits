package lehjr.powersuits.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.network.MPSPackets;
import lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.NetworkEvent;
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
        MPSPackets.sendToPlayer(new CreativeInstallPacketClientBound(slotType, regName), entity);
    }

    public static class Handler {
        public static void handle(CreativeInstallPacketServerBound message, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                final ServerPlayer player = ctx.get().getSender();
                EquipmentSlot slotType = message.slotType;
                ResourceLocation regName = message.regName;
                Item item = ForgeRegistries.ITEMS.getValue(regName);
                if(item == null ){
                    return;
                }

                ItemStack module = new ItemStack(item, 1);

                IEnergyStorage iEnergyStorage = module.getCapability(ForgeCapabilities.ENERGY).resolve().orElse(null);
                if(iEnergyStorage != null) {
                    while (iEnergyStorage.getEnergyStored() < iEnergyStorage.getMaxEnergyStored()) {
                        iEnergyStorage.receiveEnergy(iEnergyStorage.getMaxEnergyStored(), false);
                    }
                    iEnergyStorage.receiveEnergy(iEnergyStorage.getMaxEnergyStored(), false);
                }

                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(ItemUtils.getItemFromEntitySlot(player, slotType));
                if (iModularItem != null) {
                    int index = iModularItem.findInstalledModule(module);

                    // Todo: upgrade modules

                    // if not installed
                    if (index < 0) {
                        for (index = 0; index < iModularItem.getSlots(); index++) {
                            if (iModularItem.isModuleValidForPlacement(index, module) && iModularItem.insertItem(index, module, false).isEmpty()) {
                                break;
                            }
                        }
                    } else {
                        ItemStack tmp = iModularItem.getStackInSlot(index);
                        iModularItem.setStackInSlot(index, ItemStack.EMPTY);
                        if (iModularItem.isModuleValidForPlacement(index, module)) {
                            iModularItem.setStackInSlot(index, module);
                        } else {
                            iModularItem.setStackInSlot(index, tmp);
                        }
                    }
                }
                if (player instanceof ServerPlayer) {
                    sendToClient(player, slotType, regName);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}