package lehjr.powersuits.network.packets;

import lehjr.numina.util.capabilities.inventory.modularitem.IModularItem;
import net.minecraft.entity.player.ServerPlayer;
import net.minecraft.inventory.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CreativeInstallPacket {
    protected EquipmentSlot slotType;
    protected ResourceLocation regName;

    public CreativeInstallPacket() {
    }

    public CreativeInstallPacket(EquipmentSlot slotType, ResourceLocation regName) {
        this.slotType = slotType;
        this.regName = regName;
    }

    public static void write(CreativeInstallPacket msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(msg.slotType);
        packetBuffer.writeResourceLocation(msg.regName);
    }

    public static CreativeInstallPacket read(FriendlyByteBuf packetBuffer) {
        return new CreativeInstallPacket(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readResourceLocation());
    }

    public static void handle(CreativeInstallPacket message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayer player = ctx.get().getSender();
            EquipmentSlot slotType = message.slotType;
            ResourceLocation regName = message.regName;
            Item item = ForgeRegistries.ITEMS.getValue(regName);
            ItemStack module = new ItemStack(item, 1);

            module.getCapability(CapabilityEnergy.ENERGY).ifPresent(iEnergyStorage -> iEnergyStorage.receiveEnergy(iEnergyStorage.getMaxEnergyStored(), false));

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