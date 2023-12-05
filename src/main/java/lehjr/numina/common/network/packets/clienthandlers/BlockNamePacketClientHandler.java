package lehjr.numina.common.network.packets.clienthandlers;

import lehjr.numina.common.capabilities.inventory.modechanging.IModeChangingItem;
import lehjr.numina.common.constants.TagConstants;
import lehjr.numina.common.item.ItemUtils;
import lehjr.numina.common.network.packets.clientbound.BlockNamePacketClientBound;
import lehjr.numina.common.tags.TagUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BlockNamePacketClientHandler {
    public static void handlePacket(BlockNamePacketClientBound message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final Player player = Minecraft.getInstance().player;
            ResourceLocation regName = message.getRegName();

            if (player != null && regName != null) {
                ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.MAINHAND)
                        .getCapability(ForgeCapabilities.ITEM_HANDLER).filter(IModeChangingItem.class::isInstance)
                        .map(IModeChangingItem.class::cast)
                        .ifPresent(handler-> {
                            TagUtils.setModuleResourceLocation(handler.getActiveModule(), TagConstants.BLOCK, regName);
                        });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
