package lehjr.numina.client.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.registration.NuminaCapabilities;
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
                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(player.getInventory().getItem(i));
                if(iModularItem != null) {
                    iModularItem.toggleModule(registryName, toggleval);
                }
            }
    }
}
