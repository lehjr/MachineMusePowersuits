package lehjr.powersuits.common.network.packets.serverbound;

import lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import lehjr.numina.common.registration.NuminaCapabilities;
import lehjr.numina.common.utils.ElectricItemUtils;
import lehjr.numina.common.utils.ItemUtils;
import lehjr.powersuits.common.constants.MPSConstants;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @param boost: boost amount to set on parent stack
 * @param drainAmount: amount of energy to drain from player
 */
public record SetSprintAssistDoubleAndDrainServerBound(double boost, int drainAmount) {
    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeDouble(boost);
        packetBuffer.writeInt(drainAmount);
    }

    public static SetSprintAssistDoubleAndDrainServerBound read(FriendlyByteBuf packetBuffer) {
        return new SetSprintAssistDoubleAndDrainServerBound(packetBuffer.readDouble(),
                packetBuffer.readInt());
    }

    // probably not needed
//    public static void sendToClient(ServerPlayer player, int value) {
//        MPSPackets.CHANNEL_INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),new SetSprintAssistDoubleClientBound(value), player);
//    }

    public static void handle(SetSprintAssistDoubleAndDrainServerBound data, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            Level level = player.level();
            double valueToSet = data.boost();
            int drainAmount = data.drainAmount();
            if(player.connection.clientIsFloating) {
                valueToSet = 0;
            }
            if (player.getAbilities().flying || player.isPassenger() || player.isFallFlying()  || player.isInWaterOrBubble()) {
                valueToSet = 0;
            }

            IModularItem iModularItem = NuminaCapabilities.getModularItem(ItemUtils.getItemFromEntitySlot(player, EquipmentSlot.LEGS));
            if(iModularItem != null) {
                // every 20 ticks
                if ((!(valueToSet == 0) && (level.getGameTime() % 20) == 0)){
                    ElectricItemUtils.drainPlayerEnergy(player, drainAmount, false);
                }
                if (iModularItem.setModuleDouble(MPSConstants.SPRINT_ASSIST_MODULE, MPSConstants.MOVEMENT_SPEED, valueToSet)) {
//                    NuminaLogger.logDebug("changed sprint assist speed value to: " + valueToSet);
                }
            }
        });
    }
}


