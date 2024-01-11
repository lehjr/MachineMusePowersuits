package lehjr.powersuits.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.item.ItemUtils;
import lehjr.powersuits.common.network.packets.clientbound.CreativeInstallPacketClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CreativeInstallPacketClientHandler {
    public static void handlePacket(CreativeInstallPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
            EquipmentSlot slotType = message.slotType();
            ResourceLocation regName = message.regName();

            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            Item item = ForgeRegistries.ITEMS.getValue(regName);
            ItemStack module = new ItemStack(item, 1);

            module.getCapability(ForgeCapabilities.ENERGY).ifPresent(iEnergyStorage -> iEnergyStorage.receiveEnergy(iEnergyStorage.getMaxEnergyStored(), false));

            ItemUtils.getItemFromEntitySlot(player, slotType).getCapability(ForgeCapabilities.ITEM_HANDLER)
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

