package lehjr.numina.client.network;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.packets.clientbound.TweakRequestDoublePacketClientBound;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TweakRequestDoublePacketClientHander {
    public static void handlePacket(TweakRequestDoublePacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final Player player = Minecraft.getInstance().player;
            ResourceLocation moduleRegName = message.moduleRegName();
            String tweakName = message.tweakName();
            double tweakValue = message.tweakValue();
            if (moduleRegName != null && tweakName != null) {
                EquipmentSlot type = message.type();
                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(ItemUtils.getItemFromEntitySlot(player, type));
                if(iModularItem != null) {
                    iModularItem.setModuleDouble(moduleRegName, tweakName, tweakValue);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
