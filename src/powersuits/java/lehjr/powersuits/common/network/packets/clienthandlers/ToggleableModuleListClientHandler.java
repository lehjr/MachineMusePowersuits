package lehjr.powersuits.common.network.packets.clienthandlers;

import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.common.network.packets.clientbound.ToggleableModuleListClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ToggleableModuleListClientHandler {
    public static void handlePacket(ToggleableModuleListClientBound data, IPayloadContext ctx) {
        ctx.enqueueWork(()-> {
            NonNullList<ResourceLocation> moduleNames = data.moduleNames();

            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            for(ResourceLocation location: moduleNames) {
                KeymappingKeyHandler.registerKeybinding(location, false);
            }
        });
    }
}
