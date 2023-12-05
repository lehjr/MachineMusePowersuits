package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.NuminaCapabilities;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.packets.clientbound.ColorInfoPacketClientBound;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ColorInfoPacketClientHandler {
    public static void handlePacket(ColorInfoPacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final Player player = Minecraft.getInstance().player;
            EquipmentSlot slotType = message.getSlotType();
            int[] tagData = message.getTagData();
            ItemUtils.getItemFromEntitySlot(player, slotType)
                    .getCapability(NuminaCapabilities.RENDER)
                    .ifPresent(render -> render.setColorArray(tagData));
        });
        ctx.get().setPacketHandled(true);
    }
}
