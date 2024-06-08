package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.capabilities.inventory.IModularItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ToggleRequestPacketClientHandler {
    public static void handlePacket(ResourceLocation registryName, boolean toggleval) {
            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                NuminaCapabilities.getCapability(player.getInventory().getItem(i), NuminaCapabilities.Inventory.MODULAR_ITEM)
                        .map(IModularItem.class::cast)
                        .ifPresent(handler -> handler.toggleModule(registryName, toggleval));

                NuminaCapabilities.getCapability(player.getInventory().getItem(i), NuminaCapabilities.Inventory.MODE_CHANGING_MODULAR_ITEM)
                        .map(IModularItem.class::cast)
                        .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
            }
    }
}
