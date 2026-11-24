package lehjr.powersuits.common.network.packets.clientbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public record CreativeInstallPacketClientBound (EquipmentSlot slotType, ResourceLocation regName) {
    public static void write(CreativeInstallPacketClientBound msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static CreativeInstallPacketClientBound read(FriendlyByteBuf packetBuffer) {
        return new CreativeInstallPacketClientBound(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readResourceLocation());
    }

    public static void handle(CreativeInstallPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final Player player = ctx.get().getSender();
            if (player == null) {
                return;
            }

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
            if(iModularItem != null) {
                int index = iModularItem.findInstalledModule(module);
                if(index < 0) {
                    for (index = 0; index < iModularItem.getSlots(); index++) {
                        if (iModularItem.insertItem(index, module, false).isEmpty()) {
                            break;
                        }
                    }
                } else {
                    iModularItem.setStackInSlot(index, module);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}