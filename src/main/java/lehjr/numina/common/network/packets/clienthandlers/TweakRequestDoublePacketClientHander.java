package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class TweakRequestDoublePacketClientHander {
    public static void handlePacket(EquipmentSlot type, ResourceLocation moduleRegName, String tweakName, double tweakValue) {
            final Player player = Minecraft.getInstance().player;
            if (moduleRegName != null && tweakName != null) {
                NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, type), NuminaCapabilities.Inventory.MODULAR_ITEM)
                        .ifPresent(iItemHandler -> iItemHandler.setModuleDouble(moduleRegName, tweakName, tweakValue));


                NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(player, type), NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                        .ifPresent(iItemHandler -> iItemHandler.setModuleDouble(moduleRegName, tweakName, tweakValue));
            }
    }
}
