package lehjr.powersuits.common.network.packets.clienthandlers;

import lehjr.powersuits.client.control.KeyMappingReaderWriter;
import lehjr.powersuits.client.control.KeymappingKeyHandler;
import lehjr.powersuits.client.overlay.MPSOverlay;
import lehjr.powersuits.common.network.packets.clientbound.ToggleableModuleListClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleableModuleListClientHandler {
    public static void handlePacket(ToggleableModuleListClientBound data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(()-> {
            NonNullList<ResourceLocation> moduleNames = data.moduleNames();

            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            for(ResourceLocation location: moduleNames) {
                KeymappingKeyHandler.registerKeybinding(location, false);
            }
            KeyMappingReaderWriter.INSTANCE.writeOutKeybindSetings();
            MPSOverlay.makeKBDisplayList(); // fixme: eliminate the need for this
        });
    }
}
