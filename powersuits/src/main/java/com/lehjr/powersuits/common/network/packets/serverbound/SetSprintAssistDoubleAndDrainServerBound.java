package com.lehjr.powersuits.common.network.packets.serverbound;

import com.lehjr.numina.common.capabilities.inventory.modularitem.IModularItem;
import com.lehjr.numina.common.registration.NuminaCapabilities;
import com.lehjr.numina.common.utils.ElectricItemUtils;
import com.lehjr.numina.common.utils.ItemUtils;
import com.lehjr.powersuits.common.constants.MPSConstants;
import com.lehjr.powersuits.common.network.MPSPackets;
import com.lehjr.powersuits.common.network.packets.clientbound.SetSprintAssistDoubleClientBound;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nonnull;

/**
 * @param boost: boost amount to set on parent stack
 * @param drainAmount: amount of energy to drain from player
 */
public record SetSprintAssistDoubleAndDrainServerBound(double boost, int drainAmount) implements CustomPacketPayload {
    public static final Type<SetSprintAssistDoubleAndDrainServerBound> ID = new Type<>(
        ResourceLocation.fromNamespaceAndPath(MPSConstants.MOD_ID, "sprint_assist_value_request_to_server"));

    @Nonnull
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }

    public static final StreamCodec<FriendlyByteBuf, SetSprintAssistDoubleAndDrainServerBound> STREAM_CODEC =
        StreamCodec.ofMember(SetSprintAssistDoubleAndDrainServerBound::write, SetSprintAssistDoubleAndDrainServerBound::new);

    public void write(FriendlyByteBuf packetBuffer) {
        packetBuffer.writeDouble(boost);
        packetBuffer.writeInt(drainAmount);
    }

    public SetSprintAssistDoubleAndDrainServerBound(FriendlyByteBuf packetBuffer) {
        this(packetBuffer.readDouble(),
            packetBuffer.readInt());
    }

    // probably not needed
    public static void sendToClient(ServerPlayer player, int value) {
                MPSPackets.sendToPlayer(new SetSprintAssistDoubleClientBound(value), player);
    }

    public static void handle(SetSprintAssistDoubleAndDrainServerBound data, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            Level level = player.level();
            double valueToSet = data.boost();
            int drainAmount = data.drainAmount();
            if(((ServerPlayer) player).connection.clientIsFloating) {
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


