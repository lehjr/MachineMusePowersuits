package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.network.packets.clientbound.ToggleRequestPacketClientBound;
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
                player.getInventory().getItem(i).getCapability(ForgeCapabilities.ITEM_HANDLER)
                        .filter(IModularItem.class::isInstance)
                        .map(IModularItem.class::cast)
                        .ifPresent(handler -> handler.toggleModule(registryName, toggleval));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
