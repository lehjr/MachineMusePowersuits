package lehjr.numina.client.network;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.packets.clientbound.ToggleRequestPacketClientBound;
import lehjr.numina.common.registration.NuminaCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleRequestPacketClientHandler {
    public static void handlePacket(ToggleRequestPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
            ResourceLocation registryName = message.registryName();
            boolean toggleval = message.toggleval();
            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                IModularItem iModularItem = NuminaCapabilities.getModularItemOrModeChangingCapability(player.getInventory().getItem(i));
                if (iModularItem != null) {
                    iModularItem.toggleModule(registryName, toggleval);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
