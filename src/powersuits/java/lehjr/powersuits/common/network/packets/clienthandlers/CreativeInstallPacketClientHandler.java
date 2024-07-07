package lehjr.powersuits.common.network.packets.clienthandlers;

import lehjr.numina.common.capability.NuminaCapabilities;
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
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CreativeInstallPacketClientHandler {
    public static void handlePacket(CreativeInstallPacketClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(()-> {
            EquipmentSlot slotType = data.slotType();
            ResourceLocation regName = data.regName();

            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            Item item = BuiltInRegistries.ITEM.get(regName);
            ItemStack module = new ItemStack(item, 1);

            NuminaCapabilities.getCapability(module, Capabilities.EnergyStorage.ITEM).ifPresent(iEnergyStorage -> iEnergyStorage.receiveEnergy(iEnergyStorage.getMaxEnergyStored(), false));

            NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, slotType), NuminaCapabilities.Inventory.MODULAR_ITEM)
                    .ifPresent(iModularItem -> {
                        if (!iModularItem.getInstalledModuleNames().contains(regName)) {
                            for (int index = 0; index < iModularItem.getSlots(); index++) {
                                if (ItemStack.isSameItem(iModularItem.insertItem(index, module, false), ItemStack.EMPTY)) {
                                    break;
                                }
                            }
                        }
                    });
        });
    }
}

