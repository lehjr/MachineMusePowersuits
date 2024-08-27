package com.lehjr.powersuits.common.network.packets.serverbound;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record CreativeInstallPacketServerBound(EquipmentSlot slotType, ResourceLocation regName) implements CustomPacketPayload {
    public static final Type<CreativeInstallPacketServerBound> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "creative_install_to_server"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<RegistryFriendlyByteBuf, CreativeInstallPacketServerBound> STREAM_CODEC =
            StreamCodec.ofMember(CreativeInstallPacketServerBound::write, CreativeInstallPacketServerBound::new);

    public void write(RegistryFriendlyByteBuf packetBuffer) {
        packetBuffer.writeEnum(slotType);
        packetBuffer.writeResourceLocation(regName);
    }

    public CreativeInstallPacketServerBound(RegistryFriendlyByteBuf packetBuffer) {
        this(packetBuffer.readEnum(EquipmentSlot.class), packetBuffer.readResourceLocation());
    }

//    public static void sendToClient(ServerPlayer entity, EquipmentSlot slotType, ResourceLocation regName) {
//        MPSPackets.sendToPlayer(new CreativeInstallPacketClientBound(slotType, regName), entity);
//    }

    public static void handle(CreativeInstallPacketServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            final Player player = ctx.player();
            EquipmentSlot slotType = data.slotType;
            ResourceLocation regName = data.regName;
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