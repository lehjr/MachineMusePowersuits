package lehjr.powersuits.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CreativeInstallPacketClientHandler {
    public static void handlePacket(CreativeInstallPacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            EquipmentSlot slotType = data.slotType();
            ResourceLocation regName = data.regName();

            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            Item item = BuiltInRegistries.ITEM.get(regName);
            ItemStack module = new ItemStack(item, 1);
            IEnergyStorage iEnergyStorage = module.getCapability(Capabilities.EnergyStorage.ITEM);
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
    }
}
