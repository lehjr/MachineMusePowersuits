package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capability.NuminaCapabilities;
import lehjr.numina.common.constants.NuminaConstants;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.numina.common.utils.TagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public class BlockNamePacketClientHandler {
    public static void handlePacket(ResourceLocation regName) {
        if (Minecraft.getInstance().player != null) {
            NuminaCapabilities.getCapability(ItemUtils.getItemFromEntitySlot(Minecraft.getInstance().player, EquipmentSlot.MAINHAND),
                            NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                    .ifPresent(handler -> TagUtils.setModuleResourceLocation(handler.getActiveModule(), NuminaConstants.BLOCK, regName));
        }
    }
}
